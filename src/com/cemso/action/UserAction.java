/*
 * Created on Mar 1, 2012
 *
 * UserAction.java
 *
 * Copyright (C) 2012 by Citicorp Software & Technology Services (Shanghai) Limited.
 * All rights reserved. Citicorp Software & Technology Services (Shanghai) Limited 
 * claims copyright in this computer program as an unpublished work. Claim of copyright 
 * does not imply waiver of other rights.
 *
 * NOTICE OF PROPRIETARY RIGHTS
 *
 * This program is a confidential trade secret and the property of 
 * Citicorp Software & Technology Services (Shanghai) Limited. Use, examination, 
 * reproduction, disassembly, decompiling, transfer and/or disclosure to others of 
 * all or any part of this software program are strictly prohibited except by express 
 * written agreement with Citicorp Software & Technology Services (Shanghai) Limited.
 */
/*
 * ---------------------------------------------------------------------------------
 * Modification History
 * Date               Author                     Version     Description
 * Mar 1, 2012       gl65293                    1.0         New
 * ---------------------------------------------------------------------------------
 */
/**
 * 
 */
package com.cemso.action;

import java.io.IOException;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.jdom.JDOMException;

import com.cemso.dto.UserDTO;
import com.cemso.service.UserService;
import com.cemso.util.DesUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author gl65293
 * 
 */
public class UserAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private List<UserDTO> userList;
	private UserService userService;
	private String errorCode = "DEFAULT_CODE";
	private final Log log = LogFactory
			.getLog(com.cemso.action.LoginAction.class);

	private UserDTO userDTO;

	private String passwdresult;
	public String getPasswdresult() {
		return passwdresult;
	}
	public void setPasswdresult(String passwdresult) {
		this.passwdresult = passwdresult;
	}

	// change password
	public String passwd(){
		try {
			if (log.isDebugEnabled()) {
				log.debug("start to passwd...");
			}
			passwdresult = "false";
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.remove("user");
			
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);

			String uid = URLDecoder.decode(request.getParameter("uid"),"UTF-8");
			String oldpwd = request.getParameter("oldpwd");
			String newpwd1 = request.getParameter("newpwd1");

			if(userService.checkuser(uid,oldpwd)){
				if(userService.passwd(uid,newpwd1)){
					passwdresult = "true";
				}
			}
			
			
			if (log.isDebugEnabled()) {
				log.debug("passwd successfully");
			}

		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
		}
		return "passwdresult";
	}
	
	public String getUserlist() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("start to get userlist...");
			}
			userList = userService.getDecryptedUserlist();
			if (log.isDebugEnabled()) {
				log.debug("get DecryptedUserlist successfully.");
			}
			return SUCCESS;
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// add user
	public String addUser() {
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);

			String id = URLDecoder.decode(request.getParameter("userId"),"UTF-8");
			String userPwd = request.getParameter("userPwd");

			if(!userService.nameNotExist(id)){
				errorCode = "ERROR_ADD_USER";
				return ERROR;
			}
			
			// encrypt
			String encriedPwd = "";
			try {
				DesUtils des = new DesUtils("cemso");// ◊‘∂®“Â√‹‘ø
				encriedPwd = des.encrypt(userPwd);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String userType = request.getParameter("userType");
			String privileges = request.getParameter("privileges");

			UserDTO user = new UserDTO();
			user.setId(id);

			user.setUserPwd(encriedPwd);
			user.setUserType(userType);
			user.setPrivileges(privileges);
			user.setLastLogin(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date()));

			if (log.isDebugEnabled()) {
				log.debug("start to add user...");
			}

			boolean flag = userService.addUser(user);
			if (flag) {
				if (log.isDebugEnabled()) {
					log.debug("add user successfully.");
				}
				return SUCCESS;
			} else {
				if (log.isErrorEnabled()) {
					log.error("add user failed!");
				}
				return ERROR;
			}
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// delete user
	public String delUser() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("start to delete user...");
			}

			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);

			// user id
			String id = URLDecoder.decode(request.getParameter("id"),"UTF-8");

			boolean flag = userService.delUser(id);

			if (flag) {
				if (log.isDebugEnabled()) {
					log.debug("delete user successfully.");
				}
				return SUCCESS;
			} else {
				if (log.isErrorEnabled()) {
					log.error("delete user failed!");
				}
				return ERROR;
			}
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// delete selected users
	public String delSelectedUser() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("start to delete selected user ...");
			}
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);
			String ids = URLDecoder.decode(request.getParameter("ids"),"UTF-8");
			String[] id = ids.split(",");
			int size = id.length;
			boolean flag = true;
			for (int i = 0; i < size; i++) {
				flag = flag && userService.delUser(id[i]);
			}
			if (log.isDebugEnabled()) {
				log.debug("delete selected user successfully? : " + flag);
			}
			return flag ? SUCCESS : ERROR;
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	public String editUser() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("start to edit user ...");
			}
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);

			String id = URLDecoder.decode(request.getParameter("userId"),"UTF-8");
			String userPwd = request.getParameter("userPwd");
			String userType = request.getParameter("userType");
			String privileges = request.getParameter("privileges");

			UserDTO user = new UserDTO();
			user.setId(id);

			user.setUserPwd(userPwd);
			user.setUserType(userType);
			user.setPrivileges(privileges);

			try {
				userList = userService.getUserlist();
			} catch (JDOMException e) {
				if (log.isErrorEnabled()) {
					log.error(e.getMessage());
				}
			} catch (IOException e) {
				if (log.isErrorEnabled()) {
					log.error(e.getMessage());
				}
			}
			for (UserDTO userDTO : userList) {
				if (userDTO.getId().equals(id)) {
					user.setLastLogin(userDTO.getLastLogin());
					user.setIndexid(userDTO.getIndexid());
				}
			}

			boolean flag = userService.editUser(user);
			if (flag) {
				if (log.isDebugEnabled()) {
					log.debug("edit user successfully");
				}
				return SUCCESS;
			} else {
				if (log.isDebugEnabled()) {
					log.debug("edit user failed!");
				}
				return ERROR;
			}
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// edit get user
	public String editGetUser() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("start to get info of the user you want to edit ...");
			}
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);
			String id = URLDecoder.decode(request.getParameter("id"),"UTF-8");
//				userList = userService.getUserlist();
			userList = userService.getDecryptedUserlist();
			for (UserDTO user : userList) {
				if (user.getId().equals(id)) {
					userDTO = user;
				}
			}
			if (log.isDebugEnabled()) {
				log
						.debug("get info of the user you want to edit successfully ...");
			}
			return "userObject";
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// log out
	public String logout() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("start to logout...");
			}
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.remove("user");
			if (log.isDebugEnabled()) {
				log.debug("logout successfully");
			}

			return SUCCESS;
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	public List<UserDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
}