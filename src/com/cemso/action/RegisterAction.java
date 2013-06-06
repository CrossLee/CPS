/*
 * Created on 2012-10-2
 *
 * RegisterAction.java
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
 * 2012-10-2       CrossLee                    1.0         New
 * ---------------------------------------------------------------------------------
 */
/**
 * 
 */
package com.cemso.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cemso.dto.ChecklogDTO;
import com.cemso.service.RegisterService;
import com.cemso.util.ConstantUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author CrossLee
 *
 */
public class RegisterAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private String result;
	private String type;
	
	private RegisterService registerService;
	
	private Log log = LogFactory.getLog(RegisterAction.class);
	
	public RegisterService getRegisterService() {
		return registerService;
	}
	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String check(){
		
		ChecklogDTO checklog = registerService.queryLastChecklog();
		if(checklog.getType().equals(ConstantUtil.CheckLogs.TYPE_INVALID)){
			boolean flag = registerService.updateToDelete();
			if(log.isDebugEnabled()){
				log.debug("update register deleted state to yes : " + flag);
			}
		}
		result = checklog.getInfo();
		type = checklog.getType();
		return "result";
	}
}
