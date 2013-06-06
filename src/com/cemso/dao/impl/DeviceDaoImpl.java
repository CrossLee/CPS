/*
 * Created on 2012-7-15
 *
 * DeviceDaoImpl.java
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

import com.cemso.dao.DeviceDao;
import com.cemso.dto.DeviceDTO;
import com.cemso.util.FetchData;

/**
 * @author CrossLee
 * 
 */
public class DeviceDaoImpl implements DeviceDao {
	
	private static final Log log = LogFactory.getLog(com.cemso.dao.impl.DeviceDaoImpl.class);

	@Override
	public boolean addDevice(DeviceDTO device) {
		String sql = generateSql(device, FetchData.SQL_INSERT);
		int count = FetchData.updateData(sql);
		return (count > 0) ? true : false;
	}

	@Override
	public boolean deleteDevice(int indexid) {
		String sql = "delete from device where indexid = " + indexid + ";";
		int count = FetchData.updateData(sql);
		return (count > 0) ? true : false;
	}
	
	@Override
	public boolean deleteDevice(String deviceid) {
		String sql = "delete from device where id = '" + deviceid + "';";
		int count = FetchData.updateData(sql);
		return (count > 0) ? true : false;
	}

	@Override
	public DeviceDTO queryDevice(int indexid) {
		DeviceDTO device = null;
		String sql = "select * from device where indexid = " + indexid + ";";
		if(log.isDebugEnabled()){
			log.debug(sql);
		}
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.DeviceDTO");
		if(!list.isEmpty()){
			device = (DeviceDTO) list.get(0);
		}
		return device;
	}

	@Override
	public List<DeviceDTO> queryDevices() {
		String sql = "select * from device order by indexid desc;";
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.DeviceDTO");
		List<DeviceDTO> deviceList = new ArrayList<DeviceDTO>();
		for (int i = 0; i < list.size(); i++) {
			deviceList.add((DeviceDTO) list.get(i));
		}
		return deviceList;
	}

	@Override
	public boolean updateDevice(DeviceDTO device) {
		String sql = generateSql(device, FetchData.SQL_UPDATE);
		int count = FetchData.updateData(sql);
		return (count > 0) ? true : false;
	}
	
	// update device state
	@Override
	public boolean updateDevice(String ip, String statue) {
		String sql = "update device set onlineState = '"+statue+"' where ip = '"+ip+"'";
		if(log.isDebugEnabled()){
			log.debug("sql: "+ sql);
		}
		int count = FetchData.updateData(sql);
		return (count > 0) ? true : false;
	}

	private String generateSql(DeviceDTO device, String type) {
		String sql = null;
		int indexid = 0;
		String id = "";
		String groupId = "";
		String groupText = "";
		if(type.equals("INSERT")){
			indexid = device.getIndexid();
			id = device.getId();
			groupId = device.getGroupId();
			groupText = device.getGroupText();
		}
		String name = device.getName();
		String macAdd = device.getMacAdd();
		String remark = device.getRemark();
		String ip = device.getIp();
		String gateway = device.getGateway();
		String onlineState = device.getOnlineState();
		String currentPlaylist = device.getCurrentPlaylist();
		String currentTemplate = device.getCurrentTemplate();
		String bootTime = device.getBootTime();
		String lastCommunicateTime = device.getLastCommunicateTime();
		String currentTransferFileName = device.getCurrentTransferFileName();
		String currentTransferSpeed = device.getCurrentTransferSpeed();
		String currentPercentage = device.getCurrentPercentage();
		String deviceVersion = device.getDeviceVersion();
		String volume = device.getVolume();
		String autoBootTime = device.getAutoBootTime();
		String autoShutdownTime = device.getAutoShutdownTime();
		String playingVideo = device.getPlayingVideo();
		String programUpdateTime = device.getProgramUpdateTime();
		String programUpdateState = device.getProgramUpdateState();
		String availableSpace = device.getAvailableSpace();
		String pNo = device.getpNo();
		String width = device.getWidth();
		String height = device.getHeight();
		String storage = device.getStorage();
		String time = device.getTime();
		String prgTotal = device.getPrgTotal();
		String prgIndex = device.getPrgIndex();
		String statusNo = device.getStatusNo();
		String storageName = device.getStorageName();

		if (type.equals("INSERT")) {
			sql = "insert into device(indexid,id,name,macAdd,remark,groupId,groupText,ip"
					+ ",gateway,onlineState,currentPlaylist,currentTemplate,bootTime,lastCommunicateTime"
					+ ",currentTransferFileName,currentTransferSpeed,currentPercentage,deviceVersion"
					+ ",volume,autoBootTime,autoShutdownTime,playingVideo,programUpdateTime"
					+ ",programUpdateState,availableSpace,pNo,width,height,storage,time"
					+ ",prgTotal,prgIndex,statusNo,storageName" + ") values("
					+ indexid
					+ ",'"
					+ id
					+ "','"
					+ name
					+ "','"
					+ macAdd
					+ "','"
					+ remark
					+ "','"
					+ groupId
					+ "','"
					+ groupText
					+ "','"
					+ ip
					+ "','"
					+ gateway
					+ "','"
					+ onlineState
					+ "','"
					+ currentPlaylist
					+ "','"
					+ currentTemplate
					+ "','"
					+ bootTime
					+ "','"
					+ lastCommunicateTime
					+ "','"
					+ currentTransferFileName
					+ "','"
					+ currentTransferSpeed
					+ "','"
					+ currentPercentage
					+ "','"
					+ deviceVersion
					+ "','"
					+ volume
					+ "','"
					+ autoBootTime
					+ "','"
					+ autoShutdownTime
					+ "','"
					+ playingVideo
					+ "','"
					+ programUpdateTime
					+ "','"
					+ programUpdateState
					+ "','"
					+ availableSpace
					+ "','"
					+ pNo
					+ "','"
					+ width
					+ "','"
					+ height
					+ "','"
					+ storage
					+ "','"
					+ time
					+ "','"
					+ prgTotal
					+ "','"
					+ prgIndex
					+ "','"
					+ statusNo
					+ "','" + storageName + "');";
		}
		if (type.equals("UPDATE")) {
			sql = "update device set name = '"+name+"',macAdd = '"+macAdd+"',remark = '"+remark+"',ip = '"+ip+"'"
					+ ",gateway = '"+gateway+"',onlineState = '"+onlineState+"',currentPlaylist = '"+currentPlaylist+"',currentTemplate = '"+currentTemplate+"',bootTime = '"+bootTime+"',lastCommunicateTime = '"+lastCommunicateTime+"'"
					+ ",currentTransferFileName = '"+currentTransferFileName+"',currentTransferSpeed = '"+currentTransferSpeed+"',currentPercentage = '"+currentPercentage+"',deviceVersion = '"+deviceVersion+"'"
					+ ",volume = '"+volume+"',autoBootTime = '"+autoBootTime+"',autoShutdownTime = '"+autoShutdownTime+"',playingVideo = '"+playingVideo+"',programUpdateTime = '"+programUpdateTime+"'"
					+ ",programUpdateState = '"+programUpdateState+"',availableSpace = '"+availableSpace+"',pNo = '"+pNo+"',width = '"+width+"',height = '"+height+"',storage = '"+storage+"',time = '"+time+"'"
					+ ",prgTotal = '"+prgTotal+"',prgIndex = '"+prgIndex+"',statusNo = '"+statusNo+"',storageName = '"+storageName+"'" 
					+ "where ip = '"+ip+"';";
		}
		if (type.equals("DELETE")) {
			sql = "delete from device where indexid = " + indexid + ";";
		}
		if (log.isDebugEnabled()) {
			log.debug("DeviceDaoImpl.generateSql() call -> sql : " + sql);
		}
		return sql;
	}

	@Override
	public DeviceDTO queryDevice(String ip) {
		DeviceDTO device = null;
		String sql = "select * from device where ip = '" + ip + "';";
		if(log.isDebugEnabled()){
			log.debug(sql);
		}
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.DeviceDTO");
		if(!list.isEmpty()){
			device = (DeviceDTO) list.get(0);
		}
		return device;
	}

	@Override
	public boolean deleteAllDevices() {
		String sql = "delete from device;";
		if(log.isDebugEnabled()){
			log.debug(sql);
		}
		int count = FetchData.updateDataBySelf(sql);
		return (count > 0) ? true : false;
	}

}
