/*
 * Created on 2012-9-28
 *
 * Register.java
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
package com.cemso.dto;

/**
 * @author CrossLee
 *
 */
public class RegisterDTO {

	private Integer indexid;
	private String startdate;
	private String expdate;
	private String key;
	private String device;
	private String deleted;
	
	
	/**
	 * 
	 */
	public RegisterDTO() {
		super();
	}
	
	/**
	 * @param indexid
	 * @param startdate
	 * @param expdate
	 * @param key
	 * @param device
	 */
	public RegisterDTO(Integer indexid, String startdate, String expdate,
			String key, String device, String deleted) {
		super();
		this.indexid = indexid;
		this.startdate = startdate;
		this.expdate = expdate;
		this.key = key;
		this.device = device;
		this.deleted = deleted;
	}
	
	public Integer getIndexid() {
		return indexid;
	}
	public void setIndexid(Integer indexid) {
		this.indexid = indexid;
	}

	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getExpdate() {
		return expdate;
	}
	public void setExpdate(String expdate) {
		this.expdate = expdate;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
}
