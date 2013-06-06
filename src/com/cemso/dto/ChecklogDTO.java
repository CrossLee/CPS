/*
 * Created on 2012-10-6
 *
 * Checklog.java
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
 * 2012-10-6       CrossLee                    1.0         New
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
public class ChecklogDTO {

	private Integer indexid;
	private String info;
	private String logtime;
	private String type;
	
	/**
	 * 
	 */
	public ChecklogDTO() {
		super();
	}
	/**
	 * @param indexid
	 * @param info
	 * @param logtime
	 */
	public ChecklogDTO(Integer indexid, String info, String logtime, String type) {
		super();
		this.indexid = indexid;
		this.info = info;
		this.logtime = logtime;
		this.type = type;
	}
	public Integer getIndexid() {
		return indexid;
	}
	public void setIndexid(Integer indexid) {
		this.indexid = indexid;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getLogtime() {
		return logtime;
	}
	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
