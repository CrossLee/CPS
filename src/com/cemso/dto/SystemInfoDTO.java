/*
 * Created on 2012-6-30
 *
 * SystemInfoDTO.java
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
 * 2012-6-30       CrossLee                    1.0         New
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
public class SystemInfoDTO {

	private String pNo;
	private String version;
	private String width;
	private String height;
	private String storage;
	private String ip;
	private String gateway;
	private String subnetMask;
	private String macAddress;
	private String status;
	private String time;
	private String freedisk;
	private String prgTotal;
	private String prgIndex;
	private String bootTime;
	 /**
	 * [CTRL_INFO]   	 
	
		PNO 		=（屏号） 根据控制器硬件拨码值
		VERSION 	=（系统软件版本号）
		WIDTH		=（屏宽）
		HEIGHT		=（屏高）
		STORAGE		=（存储模式）
						1：FLASH存储
						2：RAM存储
						3：SD卡存储
		IP			=（IP地址）
		GATEWAY		=（网关）
		SUBNETMASK	=（子网掩码）
		MACADDRESS	=（MAC地址）
						格式：xx-xx-xx-xx-xx-xx
		STATUS		=（设备运行状态）
						0 : 关屏状态
						1 : 开屏状态
		TIME		=（设备当前时间）
						Eg: 2010/7/23 19:16:15
		FREEDISK	=（剩余存储空间大小,单位BYTE） 
		PRGTOTAL	=（设备节目总数）
		PRGINDEX	=（当前播放节目序号）
		BOOT_TIME	=（设备开机时间）
						Eg: 2010/7/23 19:16:15
	  **/
	public String getpNo() {
		return pNo;
	}
	public void setpNo(String pNo) {
		this.pNo = pNo;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getStorage() {
//		return String.valueOf(Integer.parseInt(storage)/1024);
		return storage;
	}
	public void setStorage(String storage) {
		this.storage = storage;
		//this.storage = String.valueOf(Integer.parseInt(storage)/1024);
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getSubnetMask() {
		return subnetMask;
	}
	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getFreedisk() {
		return freedisk;
	}
	public void setFreedisk(String freedisk) {
		this.freedisk = freedisk;
	}
	public String getPrgTotal() {
		return prgTotal;
	}
	public void setPrgTotal(String prgTotal) {
		this.prgTotal = prgTotal;
	}
	public String getPrgIndex() {
		return prgIndex;
	}
	public void setPrgIndex(String prgIndex) {
		this.prgIndex = prgIndex;
	}
	public String getBootTime() {
		return bootTime;
	}
	public void setBootTime(String bootTime) {
		this.bootTime = bootTime;
	}
	
}
