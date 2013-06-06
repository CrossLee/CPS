/*
 * Created on 2012-8-2
 *
 * CheckDeviceStateExcuter.java
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
 * 2012-8-2       CrossLee                    1.0         New
 * ---------------------------------------------------------------------------------
 */
/**
 * 
 */
package com.cemso.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cemso.dao.DeviceDao;
import com.cemso.dao.RegisterDao;
import com.cemso.dao.TreenodeDao;
import com.cemso.dao.impl.DeviceDaoImpl;
import com.cemso.dao.impl.RegisterDaoImpl;
import com.cemso.dao.impl.TreenodeDaoImpl;
import com.cemso.dto.DeviceDTO;
import com.cemso.dto.RegisterDTO;
import com.cemso.dto.SystemInfoDTO;
import com.cemso.dto.TreeNodeDTO;
import com.cemso.jna.JoymindCommDLL.JoymindCommDLLLib;

/**
 * @author CrossLee
 * 
 */
public class CheckDeviceStateExcuter {

	private static final Log log = LogFactory.getLog(CheckDeviceStateExcuter.class);
	
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	private static DeviceDao deviceDao = new DeviceDaoImpl();
	private static RegisterDao registerDao = new RegisterDaoImpl();
	private static List<DeviceDTO> devices = new ArrayList<DeviceDTO>();
	private static RegisterDTO register = new RegisterDTO();
	private static TreenodeDao treenodeDao = new TreenodeDaoImpl();
	
	// invoke DLL method to get the details of device
	static JoymindCommDLLLib instance = JoymindCommDLLLib.INSTANCE;
	// check states
	
	public static Thread checkerThread = null;
	public static ScheduledFuture<?> checkerHandle1 = null;
	
	public static void checkStart() {
		if(log.isDebugEnabled()){
			log.debug("CheckDeviceStateExcuter.checkStart()...");
		}
		
		CheckRunner checker = new CheckDeviceStateExcuter().new CheckRunner();
		Thread t = new Thread(checker, "checker");
		checkerThread = t;
		
		final ScheduledFuture<?> checkerHandle = scheduler.scheduleAtFixedRate(t, 1, 2, TimeUnit.MINUTES);
		checkerHandle1 = checkerHandle;
		if(log.isInfoEnabled()){
			log.info("auto refresh is started...");
			try {
				File f = new File("C:/DLLfunctionsTest.txt");
				FileWriter fw = new FileWriter(f, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.newLine();
				bw.append(new java.util.Date().toString()+": auto refresh is started...");
				bw.newLine();
				bw.flush();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void checkWait(){
		if(log.isInfoEnabled()){
			log.info("auto refresh is waiting...");
			try {
				File f = new File("C:/DLLfunctionsTest.txt");
				FileWriter fw = new FileWriter(f, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.newLine();
				bw.append(new java.util.Date().toString()+": auto refresh is waiting...");
				bw.newLine();
				bw.flush();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		checkerHandle1.cancel(true);
	}
	
	/*
	public static void checkGoon(){
		CheckRunner checker = new CheckDeviceStateExcuter().new CheckRunner();
		Thread t = new Thread(checker, "checker");
		scheduler.scheduleAtFixedRate(t, 1, 2, TimeUnit.SECONDS);
	}
	*/

	class CheckRunner implements Runnable {
		@SuppressWarnings("static-access")
		@Override
		public void run() {
			///*
			java.util.Date time = new java.util.Date();
			System.out.println("checking...." + time.toString());
			//*/
			devices = deviceDao.queryDevices();
			register = registerDao.queryUndeletedOneRegister();
			
			// no test model
			if(!XmlOperationTool.isTest){
				if(register == null){
					if(log.isWarnEnabled()){
						log.warn("系统没有通过License注册的设备！");
					}
				}else{
					String expireDateStr = register.getExpdate();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						java.util.Date expireDate = sdf.parse(expireDateStr);
						Date now = new Date(System.currentTimeMillis());
						if(now.after(expireDate)){
							log.warn("License过期了！");
							registerDao.updateToDelete();
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else{
				TreeNodeDTO tnd = treenodeDao.queryTreeNode(10001);
				if(tnd != null){
					if(Long.parseLong(tnd.getText()) < System.currentTimeMillis()){
						tnd.setAction("exp");
						treenodeDao.updateTreeNode(tnd);
					}
				}
			}
			
			/*
			for(DeviceDTO device : devices){
				String ip = device.getIp();
				String id = "auto_refresh_" + ip;
				String iniPath = XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER + id
						+ ".ini";
				instance.ResetDLL();
				int flag = instance.GetSysInfo(ip, iniPath);
				if(log.isInfoEnabled()){
					log.info("auto refresh device instance.GetSysInfo(ip, iniPath) for ip: " + ip + ", reuslt is: " + flag);
				}
				try {
					Thread.currentThread().sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				// adjust system time
				boolean refreshboolean = false;
				int adjustResult = 0;
				for(int i = 0; i < 10; i++){
					instance.ResetDLL();
					adjustResult = instance.AdjustTime(ip);
					if(adjustResult == 1){
						refreshboolean = true;
						break;
					}
					try {
						Thread.currentThread().sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				if(log.isInfoEnabled()){
					log.info("adjust time for id: " + ip + ", result is :" + adjustResult);
				}
				
				if(flag == 1){
					// get ini file
					SystemInfoDTO info = null;
					try {
						info = FileOperationTool.parseIni(iniPath);

						device.setMacAdd(info.getMacAddress());
						device.setIp(info.getIp());
						device.setGateway(info.getGateway());
						device.setOnlineState("在线");
						device.setBootTime(info.getBootTime());
						device.setDeviceVersion(info.getVersion());
						device.setAvailableSpace(info.getFreedisk());

						device.setpNo(info.getpNo());
						device.setWidth(info.getWidth());
						device.setHeight(info.getHeight());
						device.setStorage(info.getStorage());
						device.setTime(info.getTime());
						device.setPrgTotal(info.getPrgTotal());
						device.setPrgIndex(info.getPrgIndex());
						device.setStatusNo(info.getStatus());

						DeviceDTO ddto = deviceDao.queryDevice(ip);
						device.setCurrentPlaylist(ddto.getCurrentPlaylist());

						
						if (info.getStorage().equals("1")) {
							device.setStorageName("FLASH存储");
						}
						if (info.getStorage().equals("2")) {
							device.setStorageName("RAM存储");
						}
						if (info.getStorage().equals("3")) {
							device.setStorageName("SD卡存储");
						}

						device.setCurrentTemplate("");
						device.setLastCommunicateTime("");
						device.setCurrentTransferFileName("");
						device.setCurrentTransferSpeed("");
						device.setCurrentPercentage("");
						device.setVolume("");
						device.setAutoBootTime("");
						device.setAutoShutdownTime("");
						device.setPlayingVideo("");
						device.setProgramUpdateTime("");
						device.setProgramUpdateState("");
					} catch (IOException e1) {
						if (log.isErrorEnabled()) {
							log.error(e1.getMessage());
						}
						e1.printStackTrace();
					}

					boolean addFlag = deviceDao.updateDevice(device);
					if (addFlag) {
						if (log.isInfoEnabled()) {
							log.info("auto update device "+device.getName()+" successfully");
						}
					} else {
						if (log.isErrorEnabled()) {
							log.error("auto update device failed !!!");
						}
					}
				}else{
					if(refreshboolean){
						deviceDao.updateDevice(ip, "在线");
						if (log.isInfoEnabled()) {
							log.info("auto update device "+device.getName()+" statue online");
						}
					}else{
						deviceDao.updateDevice(ip, "离线");
						if (log.isInfoEnabled()) {
							log.info("auto update device "+device.getName()+" statue offline");
						}
					}
				}
			}
			*/
		}
	}
}

