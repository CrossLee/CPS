/*
 * Created on 2012-7-15
 *
 * UserDaoImpl.java
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
package com.cemso.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cemso.dao.UserDao;
import com.cemso.dto.UserDTO;
import com.cemso.util.FetchData;

/**
 * @author CrossLee
 * 
 */
public class UserDaoImpl implements UserDao {

	private static final Log log = LogFactory
			.getLog(com.cemso.dao.impl.UserDaoImpl.class);

	@Override
	public boolean addUser(UserDTO user) {
		String sql = generateSql(user, FetchData.SQL_INSERT);
		int count = FetchData.updateData(sql);
		return (count == 1);
	}

	@Override
	public boolean deleteUser(int indexid) {
		String sql = "delete from user where indexid = " + indexid + ";";
		int count = FetchData.updateData(sql);
		return (count == 1);
	}

	@Override
	public UserDTO queryUser(String username, String password) {
		UserDTO user = null;

		String sql = "select * from user where id='" + username
				+ "' and userPwd = '" + password + "';";
		if (log.isDebugEnabled()) {
			log.debug("sql : " + sql);
		}
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.UserDTO");
		if (!list.isEmpty()) {
			user = (UserDTO) list.get(0);
		}
		return user;
	}

	@Override
	public List<UserDTO> queryUsers() {
		String sql = "select * from user order by indexid desc;";
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.UserDTO");
		List<UserDTO> userList = new ArrayList<UserDTO>();
		for (int i = 0; i < list.size(); i++) {
			userList.add((UserDTO) list.get(i));
		}
		return userList;
	}

	public boolean passwd(String username, String encriptpassword){
		String sql = "update user set userPwd = '" + encriptpassword + "' where id = '"
		+ username + "';";
		int count = FetchData.updateData(sql);
		return (count > 0) ? true : false;
	}
	
	@Override
	public boolean updateUser(UserDTO user) {
		String sql = generateSql(user, FetchData.SQL_UPDATE);
		int count = FetchData.updateData(sql);
		return (count > 0) ? true : false;
	}

	private String generateSql(UserDTO user, String type) {
		String sql = null;
		int indexid = user.getIndexid();
		String userId = user.getId();
		String userPwd = user.getUserPwd();
		String userType = user.getUserType();
		String privileges = user.getPrivileges();
		String lastLogin = user.getLastLogin();

		if (type.equals("INSERT")) {
			sql = "insert into user(indexid,id,userPwd,userType,privileges,lastLogin) values("
					+ indexid
					+ ",'"
					+ userId
					+ "','"
					+ userPwd
					+ "','"
					+ userType + "','" + privileges + "','" + lastLogin + "');";
		}
		if (type.equals("UPDATE")) {
			sql = "update user set userPwd = '" + userPwd + "' , userType = '"
					+ userType + "', privileges = '" + privileges
					+ "', lastLogin = '" + lastLogin + "' where indexid = '"
					+ indexid + "';";
		}
		if (type.equals("DELETE")) {
			sql = "delete from user where indexid = " + String.valueOf(indexid)
					+ ";";
		}
		return sql;
	}

	@Override
	public boolean deleteUser(String id) {
		String sql = "delete from user where id = '" + id + "';";
		int count = FetchData.updateData(sql);
		return (count == 1);
	}

	@Override
	public boolean nameNotExist(String userName) {
		String sql = "select * from user where id='" + userName + "';";
		if (log.isDebugEnabled()) {
			log.debug("sql : " + sql);
		}
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.UserDTO");
		return list.isEmpty() ? true : false;
	}
}
