package com.cemso.action;

import java.net.URLDecoder;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cemso.dto.ActionlogDTO;
import com.cemso.dto.UserDTO;
import com.cemso.service.UserService;
import com.googlecode.jsonplugin.annotations.JSON;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private String userName;
	private String userPwd;
	private String userType;
	private String loginState;
	private UserService userService;
	private final Log log = LogFactory.getLog(com.cemso.action.LoginAction.class);

	public String login() {
		try {
		    userName = URLDecoder.decode(userName,"UTF-8");
		    userPwd = URLDecoder.decode(userPwd,"UTF-8");
			
			if(log.isDebugEnabled()){
		        log.debug("LoginAction -> login()");
		        log.debug("user name is : " + userName + ", userpwd is : " + userPwd);
		    }
		    
            super.execute();
            UserDTO userDTO = userService.login(userName, userPwd);
            if(userDTO == null){
                if(log.isDebugEnabled()){
                    log.debug("userDTO is null");
                }
                loginState = "failed";
            }else{
                Map<String,Object> session = ActionContext.getContext().getSession();
                session.put("user", userDTO);
                ActionlogDTO.username = userDTO.getId();
                loginState = "successful";
            }
            return "userObj";
        } catch (Exception e) {
            if(log.isErrorEnabled()){
                log.error(e.getMessage());
            }
            return ERROR;
        }
	}
	
	@JSON(serialize = false)
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLoginState() {
		return loginState;
	}

	public void setLoginState(String loginState) {
		this.loginState = loginState;
	}
	
	
}
