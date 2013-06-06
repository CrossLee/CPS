/*
 * Created on 2012-7-15
 *
 * UserDao.java
 *
 * Copyright (C) 2012 by Withiter Software & Technology Services (Shanghai) Limited.
 * All rights reserved. Withiter Software & Technology Services (Shanghai) Limited 
 * claims copyright in this computer program as an unpublished work. Claim of copyright 
 * does not imply waiver of other rights.
 *
 * NOTICE OF PROPRIETARY RIGHTS
 *
 * This program is a confidential trade secret and the property of 
 * Withiter Software & Technology Services (Shanghai) Limited. Use, examination, 
 * reproduction, disassembly, decompiling, transfer and/or disclosure to others of 
 * all or any part of this software program are strictly prohibited except by express 
 * written agreement with Withiter Software & Technology Services (Shanghai) Limited.
 */
/*
 * ---------------------------------------------------------------------------------
 * Modification History
 * Date               Author                     Version     Description
 * 2012-7-15       CrossLee                    1.0         New
 * ---------------------------------------------------------------------------------
 */
/**
 * 
 */
package com.cemso.dao;

import java.util.List;

import com.cemso.dto.UserDTO;

/**
 * @author CrossLee
 *
 */
public interface UserDao {

	public List<UserDTO> queryUsers();
	public UserDTO queryUser(String username,String password);
	boolean deleteUser(int indexid);
	boolean deleteUser(String id);
	boolean addUser(UserDTO user);
	boolean updateUser(UserDTO user);
	boolean passwd(String username, String encriptpassword);
	boolean nameNotExist(String userName);
}
