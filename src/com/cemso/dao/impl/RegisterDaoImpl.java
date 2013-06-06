/*
 * Created on 2012-9-28
 *
 * RegisterDaoImpl.java
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
 * 2012-9-28       CrossLee                    1.0         New
 * ---------------------------------------------------------------------------------
 */
/**
 * 
 */
package com.cemso.dao.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cemso.dao.RegisterDao;
import com.cemso.dto.ChecklogDTO;
import com.cemso.dto.RegisterDTO;
import com.cemso.util.DesUtils;
import com.cemso.util.FetchData;

/**
 * @author CrossLee
 * 
 */
public class RegisterDaoImpl implements RegisterDao {

	private Log log = LogFactory.getLog(RegisterDaoImpl.class);

	@Override
	public boolean add(RegisterDTO regDTO) {
		String sql = generateSql(regDTO, FetchData.SQL_INSERT);
		if (log.isDebugEnabled()) {
			log.debug(sql);
		}
		int count = FetchData.updateData(sql);
		return (count == 1);
	}

	@Override
	public List<RegisterDTO> queryRegisters() {
		String sql = "select * from register order by indexid desc;";
		if (log.isDebugEnabled()) {
			log.debug("sql : " + sql);
		}
		List<Object> list = FetchData.queryObject(sql,
				"com.cemso.dto.RegisterDTO");
		List<RegisterDTO> registers = new ArrayList<RegisterDTO>();
		for (Object o : list) {
			registers.add((RegisterDTO) o);
		}
		return registers;
	}

	private String generateSql(RegisterDTO regDTO, String type) {
		int indexid = regDTO.getIndexid();
		String startdate = regDTO.getStartdate();
		String expdate = regDTO.getExpdate();
		String key = regDTO.getKey();
		String device = regDTO.getDevice();
		String deleted = regDTO.getDeleted();

		String sql = "";
		if (type.equals("INSERT")) {
			sql = "insert into register values (" + indexid + ",'" + startdate
					+ "','" + expdate + "','" + key + "','" + device + "','"
					+ deleted + "');";
		}

		return sql;
	}

	@Override
	public boolean addChecklog(ChecklogDTO checklog) {
		int indexid = checklog.getIndexid();
		String info = checklog.getInfo();
		String logtime = checklog.getLogtime();
		String type = checklog.getType();

		String sql = "insert into checklog values(" + indexid + ",'" + info
				+ "','" + logtime + "','" + type + "');";
		if (log.isDebugEnabled()) {
			log.debug(sql);
		}
		int count = FetchData.updateData(sql);
		return (count == 1);
	}

	@Override
	public ChecklogDTO queryLastChecklog() {
		String sql = "select * from checklog order by indexid DESC limit 1;";
		List<Object> list = FetchData.queryObject(sql,
				"com.cemso.dto.ChecklogDTO");
		if (list == null || list.isEmpty()) {
			return null;
		} else {
			return (ChecklogDTO) list.get(0);
		}
	}

	@Override
	public boolean updateToDelete() {
		String sql = "update register set deleted = 'yes' where deleted = 'no';";
		int count = FetchData.updateData(sql);
		return (count >= 1);
	}

	@Override
	public List<RegisterDTO> queryUndeletedRegisters() {
		String sql = "select * from register where deleted = 'no';";
		if (log.isDebugEnabled()) {
			log.debug("sql : " + sql);
		}
		List<Object> list = FetchData.queryObject(sql,
				"com.cemso.dto.RegisterDTO");
		List<RegisterDTO> registers = new ArrayList<RegisterDTO>();
		for (Object o : list) {
			registers.add((RegisterDTO) o);
		}
		return registers;
	}
	
	@Override
	public RegisterDTO queryUndeletedOneRegister() {
		String sql = "select * from register where deleted = 'no';";
		if (log.isDebugEnabled()) {
			log.debug("sql : " + sql);
		}
		List<Object> list = FetchData.queryObject(sql,
				"com.cemso.dto.RegisterDTO");
		List<RegisterDTO> registers = new ArrayList<RegisterDTO>();
		for (Object o : list) {
			registers.add((RegisterDTO) o);
		}
		if(registers.size() != 0){
			return registers.get(registers.size() - 1);
		}else{
			return null;
		}
	}

	@Override
	public boolean isUndeletedExist() {
		String sql = "select count(1) from register where deleted = 'no';";
		if (log.isDebugEnabled()) {
			log.debug("sql : " + sql);
		}
		boolean flag = FetchData.searchData(sql);
		if (log.isDebugEnabled()) {
			log.debug("FetchData.searchData(" + sql + ") result is : " + flag);
		}
		return flag;
	}

	@Override
	public boolean checkRegister(String mac) {
		String sql = "select * from register where deleted = 'no';";
		if (log.isDebugEnabled()) {
			log.debug("sql : " + sql);
		}
		List<Object> list = FetchData.queryObject(sql,
				"com.cemso.dto.RegisterDTO");
		if(list == null || list.isEmpty()){
			return false;
		}
		
		List<RegisterDTO> registers = new ArrayList<RegisterDTO>();
		for (Object o : list) {
			registers.add((RegisterDTO) o);
		}
		String key = registers.get(0).getKey();
		Date currentDate = new Date();
		String starttime = registers.get(0).getStartdate();
		String exptime = registers.get(0).getExpdate();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	Date sdate = null;
		Date edate = null;
		try {
			sdate = dateFormat.parse(starttime);
			edate = dateFormat.parse(exptime);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

    	if(currentDate.before(sdate) || currentDate.after(edate)){
    		return false;
    	}
		
		try {
			DesUtils des = new DesUtils(key);
			String encryMac = des.encrypt(mac);
			for(int i = 0; i < registers.size(); i++){
				if(encryMac.equals(registers.get(i).getDevice())){
					return true;
				}
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
		return false;
	}

}