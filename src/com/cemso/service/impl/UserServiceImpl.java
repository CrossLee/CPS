package com.cemso.service.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cemso.dao.UserDao;
import com.cemso.dao.impl.ActionlogDaoImpl;
import com.cemso.dto.ActionlogDTO;
import com.cemso.dto.UserDTO;
import com.cemso.service.UserService;
import com.cemso.util.ConstantUtil;
import com.cemso.util.DesUtils;
import com.cemso.util.SequenceUtil;

public class UserServiceImpl implements UserService {

	private static final Log log = LogFactory
			.getLog(com.cemso.service.impl.UserServiceImpl.class);
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public boolean checkuser(String username, String password){
		String encripedPwd = null;
		try {
			DesUtils du = new DesUtils("cemso");
			encripedPwd = du.encrypt(password);
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
		UserDTO user = userDao.queryUser(username, encripedPwd);
		return (user == null) ? false : true;
	}
	
	@Override
	public UserDTO login(String id, String userPwd) {
		UserDTO userDTO = null;
		List<UserDTO> userList = userDao.queryUsers();
		DesUtils des = null;
		try {
			des = new DesUtils("cemso");
		} catch (InvalidKeyException e2) {
			e2.printStackTrace();
		} catch (NoSuchAlgorithmException e2) {
			e2.printStackTrace();
		} catch (NoSuchPaddingException e2) {
			e2.printStackTrace();
		}
		for (UserDTO user : userList) {
			String pwd = null;
			try {
				pwd = des.decrypt(user.getUserPwd());
			} catch (IllegalBlockSizeException e1) {
				e1.printStackTrace();
			} catch (BadPaddingException e1) {
				e1.printStackTrace();
			}
			if (id.equals(user.getId()) && userPwd.equals(pwd)) {
				userDTO = user;
				UserDTO userForUpdate = new UserDTO();
				userForUpdate.setIndexid(user.getIndexid());
				userForUpdate.setId(user.getId());
				userForUpdate.setPrivileges(user.getPrivileges());
				userForUpdate.setUserPwd(user.getUserPwd());
				userForUpdate.setUserType(user.getUserType());
				userForUpdate.setLastLogin(new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").format(new Date()));
//				boolean flag = editUser(userForUpdate);
				boolean flag = userDao.updateUser(userForUpdate);
				if (flag) {
					if (log.isDebugEnabled()) {
						log.debug("update last login date successfully");
					}
					break;
				} else {
					try {
						if (log.isErrorEnabled()) {
							log.error("update last login date failed !!!");
						}
						throw new Exception("update last login date failed !!!");
					} catch (Exception e) {
						if (log.isErrorEnabled()) {
							log.error("update last login date failed !!!");
							log.error(e.getMessage());
						}
					}
				}
			}
		}
		
		if(userDTO != null){
			Integer indexid1 = SequenceUtil.getInstance().getNextKeyValue(
					ConstantUtil.SequenceName.ACTIONLOG_SEQUENCE);
			String time = ActionlogDTO.currentTime();
			String user = userDTO.getId();
			String action = "登录了系统";
			String sql = "";
			ActionlogDTO actionlog = new ActionlogDTO(indexid1,time,user,action,sql);
			ActionlogDaoImpl.addActionlog(actionlog);
		}
		
		return userDTO;
	}

	@Override
	public List<UserDTO> getUserlist(){
		List<UserDTO> userList = userDao.queryUsers();
		return userList;
	}
	
	@Override
	public List<UserDTO> getDecryptedUserlist(){
		List<UserDTO> userList = userDao.queryUsers();
		try {
			for(UserDTO user : userList){
				String pwd = user.getUserPwd();
				DesUtils du = new DesUtils("cemso");
				String decripedPwd = du.decrypt(pwd);
				user.setUserPwd(decripedPwd);
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return userList;
	}

	@Override
	public boolean addUser(UserDTO user) {
		int indexid = SequenceUtil.getInstance().getNextKeyValue(ConstantUtil.SequenceName.USER_SEQUENCE);
		user.setIndexid(indexid);
		boolean flag = userDao.addUser(user);
		if(flag){
			Integer indexid1 = SequenceUtil.getInstance().getNextKeyValue(
					ConstantUtil.SequenceName.ACTIONLOG_SEQUENCE);
			String time = ActionlogDTO.currentTime();
			String user1 = ActionlogDTO.username;
			String action = "添加了用户（ID）："+user.getId()+", 用户类型："+ user.getUserType()+", 用户权限："+user.getFullPrivileges();
			String sql = "";
			ActionlogDTO actionlog = new ActionlogDTO(indexid1,time,user1,action,sql);
			ActionlogDaoImpl.addActionlog(actionlog);
		}
		return flag;
	}

	// delete user
	@Override
	public boolean delUser(String id) {
		return userDao.deleteUser(id);
	}

	// change password
	public boolean passwd(String username, String password){
		boolean flag = false;
		try {
			DesUtils du = new DesUtils("cemso");
			String encripedPwd = du.encrypt(password);
			flag = userDao.passwd(username,encripedPwd);
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
		
		return flag;
	}
	
	@Override
	public boolean editUser(UserDTO user) {
		try {
			String pwd = user.getUserPwd();
			DesUtils du = new DesUtils("cemso");
			String encripedPwd = du.encrypt(pwd);
			user.setUserPwd(encripedPwd);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return userDao.updateUser(user);
	}
	
	@Override
	public boolean nameNotExist(String userName) {
		return userDao.nameNotExist(userName);
	}
}