package com.cemso.service;

import java.io.IOException;
import java.util.List;

import org.jdom.JDOMException;

import com.cemso.dto.UserDTO;

public interface UserService {

	public UserDTO login(String userName, String userPwd);
	public boolean checkuser(String userName, String userPwd);
	public List<UserDTO> getUserlist() throws JDOMException, IOException;
	public boolean addUser(UserDTO user);
	public boolean delUser(String id);
	public boolean editUser(UserDTO user);
	public boolean passwd(String username, String password);
	public boolean nameNotExist(String userName);
	public List<UserDTO> getDecryptedUserlist();
}