/*
 * Created on 2012-7-15
 *
 * DeviceDao.java
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

import com.cemso.dto.DeviceDTO;

/**
 * @author CrossLee
 *
 */
public interface DeviceDao {

	public List<DeviceDTO> queryDevices();
	public DeviceDTO queryDevice(int indexid);
	boolean deleteDevice(int indexid);
	boolean deleteDevice(String deviceid);
	boolean deleteAllDevices();
	boolean addDevice(DeviceDTO device);
	boolean updateDevice(DeviceDTO device);
	/**
	 * @param ip
	 * @param string
	 * @return
	 */
	public boolean updateDevice(String ip, String string);
	/**
	 * @param ip
	 * @return
	 */
	public DeviceDTO queryDevice(String ip);
}
