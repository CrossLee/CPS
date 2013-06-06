/*
 * Created on 2012-10-1
 *
 * RegisterServiceImpl.java
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
 * 2012-10-1       CrossLee                    1.0         New
 * ---------------------------------------------------------------------------------
 */
/**
 * 
 */
package com.cemso.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import com.cemso.dao.RegisterDao;
import com.cemso.dto.ChecklogDTO;
import com.cemso.dto.RegisterDTO;
import com.cemso.service.RegisterService;
import com.cemso.util.ConstantUtil;
import com.cemso.util.SequenceUtil;

/**
 * @author CrossLee
 *
 */
public class RegisterServiceImpl implements RegisterService {

	private RegisterDao registerDao;
	
	public RegisterDao getRegisterDao() {
		return registerDao;
	}

	public void setRegisterDao(RegisterDao registerDao) {
		this.registerDao = registerDao;
	}


	@Override
	public boolean add(List<RegisterDTO> list) {
		boolean flag = true;
		for(RegisterDTO rd : list){
			flag &= registerDao.add(rd);
		}
		return flag;
	}

	@Override
	public boolean addChecklog(String info, String type) {
		Integer indexid = SequenceUtil.getInstance().getNextKeyValue(ConstantUtil.SequenceName.CHECKLOG_SEQUENCE);
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String logtime = tempDate.format(new java.util.Date());
		ChecklogDTO checklog = new ChecklogDTO(indexid, info, logtime,type);
		return registerDao.addChecklog(checklog);
	}

	@Override
	public ChecklogDTO queryLastChecklog() {
		return registerDao.queryLastChecklog();
	}

	@Override
	public boolean updateToDelete() {
		List<RegisterDTO> undeletedRegisters = registerDao.queryUndeletedRegisters();
		if((undeletedRegisters!=null) && (!undeletedRegisters.isEmpty())){
			return registerDao.updateToDelete();
		}else{
			return true;
		}
	}

	@Override
	public List<RegisterDTO> queryRegisters() {
		return registerDao.queryUndeletedRegisters();
	}

	@Override
	public boolean isNoDeletedExist() {
		return registerDao.isUndeletedExist();
	}

	@Override
	public boolean checkRegister(String mac) {
		return registerDao.checkRegister(mac);
	}
}
