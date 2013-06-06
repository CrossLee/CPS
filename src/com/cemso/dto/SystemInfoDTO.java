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
	
		PNO 		=�����ţ� ���ݿ�����Ӳ������ֵ
		VERSION 	=��ϵͳ����汾�ţ�
		WIDTH		=������
		HEIGHT		=�����ߣ�
		STORAGE		=���洢ģʽ��
						1��FLASH�洢
						2��RAM�洢
						3��SD���洢
		IP			=��IP��ַ��
		GATEWAY		=�����أ�
		SUBNETMASK	=���������룩
		MACADDRESS	=��MAC��ַ��
						��ʽ��xx-xx-xx-xx-xx-xx
		STATUS		=���豸����״̬��
						0 : ����״̬
						1 : ����״̬
		TIME		=���豸��ǰʱ�䣩
						Eg: 2010/7/23 19:16:15
		FREEDISK	=��ʣ��洢�ռ��С,��λBYTE�� 
		PRGTOTAL	=���豸��Ŀ������
		PRGINDEX	=����ǰ���Ž�Ŀ��ţ�
		BOOT_TIME	=���豸����ʱ�䣩
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
