/*
 * Created on Feb 28, 2012
 *
 * InitServlet.java
 *
 * Copyright (C) 2012 by Citicorp Software & Technology Services (Shanghai) Limited.
 * All rights reserved. Citicorp Software & Technology Services (Shanghai) Limited 
 * claims copyright in this computer program as an unpublished work. Claim of copyright 
 * does not imply waiver of other rights.
 *
 * NOTICE OF PROPRIETARY RIGHTS
 *
 * This program is a confidential trade secret and the property of 
 * Citicorp Software & Technology Services (Shanghai) Limited. Use, examination, 
 * reproduction, disassembly, decompiling, transfer and/or disclosure to others of 
 * all or any part of this software program are strictly prohibited except by express 
 * written agreement with Citicorp Software & Technology Services (Shanghai) Limited.
 */
/*
 * ---------------------------------------------------------------------------------
 * Modification History
 * Date               Author                     Version     Description
 * Feb 28, 2012       gl65293                    1.0         New
 * ---------------------------------------------------------------------------------
 */
/**
 * 
 */
package com.cemso.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import com.cemso.dao.TreenodeDao;
import com.cemso.dao.UserDao;
import com.cemso.dao.impl.TreenodeDaoImpl;
import com.cemso.dao.impl.UserDaoImpl;
import com.cemso.dto.TreeNodeDTO;
import com.cemso.dto.UserDTO;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author gl65293
 */
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory
			.getLog(com.cemso.util.InitServlet.class);
	private UserDao userDao = new UserDaoImpl();
	private TreenodeDao treenodeDao = new TreenodeDaoImpl();
	
	private static boolean isWindows = true;
	private static boolean isTest = false;

	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public TreenodeDao getTreenodeDao() {
		return treenodeDao;
	}
	public void setTreenodeDao(TreenodeDao treenodeDao) {
		this.treenodeDao = treenodeDao;
	}

	public void init() {
		if (log.isInfoEnabled()) {
			log.info("init servlet called...");
		}
		
		if(isTest){
			int size = treenodeDao.queryTreeNodes().size();
			if(size == 0){
				TreeNodeDTO treeNode = new TreeNodeDTO();
				long current = System.currentTimeMillis();
				int months = 6; 
				long expms = current + 30l*months*24*3600*1000;
				treeNode.setIndexid(10001);
				treeNode.setId("10001");
				treeNode.setText(String.valueOf(expms));
				
				treenodeDao.addTreeNode(treeNode);
			}
		}

		// check os type
		isWindows = isWindows();
		XmlOperationTool.isWindos = isWindows;
		ActionContext context = ActionContext.getContext();
		Map<String,Object> session = null;
		if(context != null){
			session = context.getSession();
		}
		if(session != null){
			session.put("widonws", isWindows);
			session.put("isTest", isTest);
		}
		XmlOperationTool.isWindos = isWindows;
		XmlOperationTool.isTest = isTest;
		
		// set log4j.properties listener
		String log4jConfig = null;
		if(isWindows){
			log4jConfig = (Thread.currentThread().getContextClassLoader()
					.getResource("") + "log4j.properties").substring(6);
		}else{
			log4jConfig = (Thread.currentThread().getContextClassLoader()
					.getResource("") + "log4j.properties").substring(5);
		}
		PropertyConfigurator.configureAndWatch(log4jConfig, 60 * 1000);
		boolean flag = false;
		if(isWindows){
			flag = createConfigFolders() && createResourceFolder(); 
		}else{
			flag = createLinuxConfigFolders() && createLinuxResourceFolder();
		}
		 
		if (userDao.nameNotExist("admin1")) {
			flag &= createDefaultUser();
		}
		if (log.isInfoEnabled()) {
			log.info("Device Status checker starting...");
		}
		CheckDeviceStateExcuter.checkStart();
	}

	private boolean isWindows() {
		boolean flag = false;
		String os = System.getProperties().getProperty("os.name");
		if (log.isInfoEnabled()) {
			log.info("OS is : " + os);
		}
		if (os.toUpperCase().indexOf("WINDOWS") != -1) {
			flag = true;
		}
		return flag;
	}

	// create playlist CONFIG WEB_CONTENT folders
	private boolean createConfigFolders() {
		if (log.isInfoEnabled()) {
			log.info("InitServlet.createConfigFolders() called...");
		}
		try {
			String pathConfig = (Thread.currentThread().getContextClassLoader()
					.getResource("") + "pathconfig.properties").substring(6);
			File pf = new File(pathConfig);
			// 生成文件输入流
			FileInputStream inpf = new FileInputStream(pf);

			// 生成properties对象
			Properties p = new Properties();
			p.load(inpf);

			XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER = p
					.getProperty("PLAYLIST_TEMPFILE_FOLDER");
			XmlOperationTool.CONFIG_FOLDER = p.getProperty("CONFIG_FOLDER");
			XmlOperationTool.WEB_CONTENT = getServletConfig()
					.getServletContext().getRealPath("/");

			// 创建资源文件夹
			if (!(new File(XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER).exists())) {
				if (log.isInfoEnabled()) {
					log.info(XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER
							+ " folder does not exist...");
					log.info("create "
							+ XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER
							+ " folder is starting...");
				}
				new File(XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER).mkdirs();
			} else {
				if (log.isInfoEnabled()) {
					log.info(XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER
							+ " folder has been exist...");
				}
			}
			// create configuration folder
			if (!(new File(XmlOperationTool.CONFIG_FOLDER).exists())) {
				if (log.isInfoEnabled()) {
					log.info(XmlOperationTool.CONFIG_FOLDER
							+ " config folder does not exist...");
					log.info("create " + XmlOperationTool.CONFIG_FOLDER
							+ " config folder is starting...");
				}
				new File(XmlOperationTool.CONFIG_FOLDER).mkdirs();
			} else {
				if (log.isInfoEnabled()) {
					log.info(XmlOperationTool.CONFIG_FOLDER
							+ " config folder has been exist...");
				}
			}

			if (new File(XmlOperationTool.CONFIG_FOLDER + "tree.xml").exists()) {
				if (log.isInfoEnabled()) {
					log.info(XmlOperationTool.CONFIG_FOLDER
							+ "tree.xml has been exist...");
				}
				File f = new File(XmlOperationTool.WEB_CONTENT + "tree.xml");
				if (f.exists()) {
					if (log.isInfoEnabled()) {
						log.info(XmlOperationTool.WEB_CONTENT
								+ "tree.xml has been exist...");
						log.info("start to delete :"
										+ XmlOperationTool.WEB_CONTENT
										+ "tree.xml ...");
					}
					f.deleteOnExit();
				}
				FileOperationTool.ChannelCopy(new File(
						XmlOperationTool.CONFIG_FOLDER + "tree.xml"), new File(
						XmlOperationTool.WEB_CONTENT + "tree.xml"));
			} else {
				FileOperationTool.ChannelCopy(new File(
						XmlOperationTool.WEB_CONTENT + "tree.xml"), new File(
						XmlOperationTool.CONFIG_FOLDER + "tree.xml"));
			}

			return true;
		} catch (FileNotFoundException e) {
			if (log.isErrorEnabled()) {
				log.error("create config folders files failed...");
				log.error(e.getMessage());
			}
			return false;
		} catch (IOException e) {
			if (log.isErrorEnabled()) {
				log.error("create config folders files failed...");
				e.printStackTrace();
				log.error(e.getMessage());
			}
			return false;
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("create config folders files failed...");
				log.error(e.getMessage());
			}
			return false;
		}
	}

	// create playlist CONFIG WEB_CONTENT folders
	private boolean createLinuxConfigFolders() {
		if (log.isInfoEnabled()) {
			log.info("InitServlet.createLinuxConfigFolders() called...");
		}
		try {
			String pathConfig = (Thread.currentThread().getContextClassLoader()
					.getResource("") + "pathconfig.properties").substring(5);
			System.out.println("pathConfig:"+pathConfig);
			File pf = new File(pathConfig);
			// 生成文件输入流
			FileInputStream inpf = new FileInputStream(pf);

			// 生成properties对象
			Properties p = new Properties();
			p.load(inpf);

			XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER = p
					.getProperty("LINUX_PLAYLIST_TEMPFILE_FOLDER");
			XmlOperationTool.CONFIG_FOLDER = p.getProperty("LINUX_CONFIG_FOLDER");
			XmlOperationTool.WEB_CONTENT = getServletConfig()
					.getServletContext().getRealPath("/");

			// 创建资源文件夹
			if (!(new File(XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER).exists())) {
				if (log.isInfoEnabled()) {
					log.info(XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER
							+ " folder does not exist...");
					log.info("create "
							+ XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER
							+ " folder is starting...");
				}
				new File(XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER).mkdirs();
			} else {
				if (log.isInfoEnabled()) {
					log.info(XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER
							+ " folder has been exist...");
				}
			}
			// create configuration folder
			if (!(new File(XmlOperationTool.CONFIG_FOLDER).exists())) {
				if (log.isInfoEnabled()) {
					log.info(XmlOperationTool.CONFIG_FOLDER
							+ " config folder does not exist...");
					log.info("create " + XmlOperationTool.CONFIG_FOLDER
							+ " config folder is starting...");
				}
				new File(XmlOperationTool.CONFIG_FOLDER).mkdirs();
			} else {
				if (log.isInfoEnabled()) {
					log.info(XmlOperationTool.CONFIG_FOLDER
							+ " config folder has been exist...");
				}
			}

			if (new File(XmlOperationTool.CONFIG_FOLDER + "tree.xml").exists()) {
				if (log.isInfoEnabled()) {
					log.info(XmlOperationTool.CONFIG_FOLDER
							+ "tree.xml exist...");
				}
				File f = new File(XmlOperationTool.WEB_CONTENT + "tree.xml");
				if (f.exists()) {
					if (log.isInfoEnabled()) {
						log.info(XmlOperationTool.WEB_CONTENT
								+ "tree.xml has been exist...");
						log.info("start to delete :"
										+ XmlOperationTool.WEB_CONTENT
										+ "tree.xml ...");
					}
					f.deleteOnExit();
				}
				FileOperationTool.ChannelCopy(new File(
						XmlOperationTool.CONFIG_FOLDER + "tree.xml"), new File(
						XmlOperationTool.WEB_CONTENT + "tree.xml"));
			} else {
				FileOperationTool.ChannelCopy(new File(
						XmlOperationTool.WEB_CONTENT + "tree.xml"), new File(
						XmlOperationTool.CONFIG_FOLDER + "tree.xml"));
			}

			return true;
		} catch (FileNotFoundException e) {
			if (log.isErrorEnabled()) {
				log.error("create config folders files failed...");
				log.error(e.getMessage());
			}
			return false;
		} catch (IOException e) {
			if (log.isErrorEnabled()) {
				log.error("create config folders files failed...");
				e.printStackTrace();
				log.error(e.getMessage());
			}
			return false;
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("create config folders files failed...");
				log.error(e.getMessage());
			}
			return false;
		}
	}

	// create resource folder
	private boolean createResourceFolder() {
		if (log.isInfoEnabled()) {
			log.info("InitServlet.createResourceFolder() called...");
		}
		try {
			String pathConfig = (Thread.currentThread().getContextClassLoader()
					.getResource("") + "pathconfig.properties").substring(6);
			File pf = new File(pathConfig);
			// 生成文件输入流
			FileInputStream inpf = new FileInputStream(pf);

			// 生成properties对象
			Properties p = new Properties();
			p.load(inpf);
			FileOperationTool.DEFAULT_IMG_DES_PATH = p
					.getProperty("DEFAULT_IMG_DES_PATH");
			FileOperationTool.DEFAULT_VIDEO_DES_PATH = p
					.getProperty("DEFAULT_VIDEO_DES_PATH");
			FileOperationTool.DEFAULT_AUDIO_DES_PATH = p
					.getProperty("DEFAULT_AUDIO_DES_PATH");
			FileOperationTool.DEFAULT_TEXT_DES_PATH = p
					.getProperty("DEFAULT_TEXT_DES_PATH");
			FileOperationTool.DEFAULT_XML_DES_PATH = p
					.getProperty("DEFAULT_XML_DES_PATH");
			FileOperationTool.DEFAULT_ZIP_PATH = p
					.getProperty("DEFAULT_ZIP_PATH");
			FileOperationTool.DEFAULT_FLV_PATH = p
					.getProperty("DEFAULT_FLV_PATH");
			// 创建资源文件夹
			if (!(new File(FileOperationTool.DEFAULT_IMG_DES_PATH).exists())) {
				boolean flag = new File(FileOperationTool.DEFAULT_IMG_DES_PATH).mkdir();
				if(log.isInfoEnabled()){
					log.info("Create \"" + FileOperationTool.DEFAULT_IMG_DES_PATH + "\" : " + flag);
				}
			}else{
				if(log.isInfoEnabled()){
					log.info("\"" + FileOperationTool.DEFAULT_IMG_DES_PATH + "\" has been exist");
				}
			}
			if (!(new File(FileOperationTool.DEFAULT_VIDEO_DES_PATH).exists())) {
				boolean flag = new File(FileOperationTool.DEFAULT_VIDEO_DES_PATH).mkdirs();
				if(log.isInfoEnabled()){
					log.info("Create \"" + FileOperationTool.DEFAULT_VIDEO_DES_PATH + "\" : " + flag);
				}
			}else{
				if(log.isInfoEnabled()){
					log.info("\"" + FileOperationTool.DEFAULT_VIDEO_DES_PATH + "\" has been exist");
				}
			}
			if (!(new File(FileOperationTool.DEFAULT_AUDIO_DES_PATH).exists())) {
				boolean flag = new File(FileOperationTool.DEFAULT_AUDIO_DES_PATH).mkdirs();
				if(log.isInfoEnabled()){
					log.info("Create \"" + FileOperationTool.DEFAULT_AUDIO_DES_PATH + "\" : " + flag);
				}
			}else{
				if(log.isInfoEnabled()){
					log.info("\"" + FileOperationTool.DEFAULT_AUDIO_DES_PATH + "\" has been exist");
				}
			}
			if (!(new File(FileOperationTool.DEFAULT_TEXT_DES_PATH).exists())) {
				boolean flag = new File(FileOperationTool.DEFAULT_TEXT_DES_PATH).mkdirs();
				if(log.isInfoEnabled()){
					log.info("Create \"" + FileOperationTool.DEFAULT_TEXT_DES_PATH + "\" : " + flag);
				}
			}else{
				if(log.isInfoEnabled()){
					log.info("\"" + FileOperationTool.DEFAULT_TEXT_DES_PATH + "\" has been exist");
				}
			}
			if (!(new File(FileOperationTool.DEFAULT_FLV_PATH).exists())) {
				boolean flag = new File(FileOperationTool.DEFAULT_FLV_PATH).mkdirs();
				if(log.isInfoEnabled()){
					log.info("Create \"" + FileOperationTool.DEFAULT_FLV_PATH + "\" : " + flag);
				}
				// copy ffmpeg tools
				String src = this.getClass().getClassLoader().getResource("")
						.getPath()
						+ "tools/";
				String file1 = "drv43260.dll";
				String file2 = "ffmpeg.bat";
				String file3 = "ffmpeg.exe";
				String file4 = "ffplay.exe";
				String file5 = "mencoder.exe";
				String file6 = "pncrt.dll";
				String file7 = "pthreadGC2.dll";

				// flow player
				String file8 = "flowplayer-3.2.8.swf";
				String file9 = "flowplayer.controls-3.2.8.swf";

				// uploadify plugin
				String file10 = "uploadify.swf";
				if(log.isInfoEnabled()){
					log.info("Start to copy files from tools folder...");
				}
				FileOperationTool.copy(new File(src + file1), new File(
						FileOperationTool.DEFAULT_FLV_PATH + file1));
				FileOperationTool.copy(new File(src + file2), new File(
						FileOperationTool.DEFAULT_FLV_PATH + file2));
				FileOperationTool.copy(new File(src + file3), new File(
						FileOperationTool.DEFAULT_FLV_PATH + file3));
				FileOperationTool.copy(new File(src + file4), new File(
						FileOperationTool.DEFAULT_FLV_PATH + file4));
				FileOperationTool.copy(new File(src + file5), new File(
						FileOperationTool.DEFAULT_FLV_PATH + file5));
				FileOperationTool.copy(new File(src + file6), new File(
						FileOperationTool.DEFAULT_FLV_PATH + file6));
				FileOperationTool.copy(new File(src + file7), new File(
						FileOperationTool.DEFAULT_FLV_PATH + file7));
				FileOperationTool.copy(new File(src + file8), new File(
						FileOperationTool.DEFAULT_FLV_PATH + file8));
				FileOperationTool.copy(new File(src + file9), new File(
						FileOperationTool.DEFAULT_FLV_PATH + file9));
				FileOperationTool.copy(new File(src + file10), new File(
						FileOperationTool.DEFAULT_FLV_PATH + file10));
			}else{
				if(log.isInfoEnabled()){
					log.info("\"" + FileOperationTool.DEFAULT_FLV_PATH + "\" has been exist");
				}
			}
			if (log.isInfoEnabled()) {
				log.info("create resource folder successfully...");
			}
			return true;
		} catch (FileNotFoundException e) {
			if (log.isErrorEnabled()) {
				log.error("create resource folder failed...");
				log.error(e.getMessage());
			}
			return false;
		} catch (IOException e) {
			if (log.isErrorEnabled()) {
				log.error("create resource folder failed...");
				log.error(e.getMessage());
			}
			return false;
		}
	}

	// create linux resource folder
	private boolean createLinuxResourceFolder() {
		if (log.isInfoEnabled()) {
			log.info("InitServlet.createLinuxResourceFolder() called...");
		}
		try {
			String pathConfig = (Thread.currentThread().getContextClassLoader()
					.getResource("") + "pathconfig.properties").substring(5);
			File pf = new File(pathConfig);
			// 生成文件输入流
			FileInputStream inpf = new FileInputStream(pf);

			// 生成properties对象
			Properties p = new Properties();
			p.load(inpf);
			FileOperationTool.DEFAULT_IMG_DES_PATH = p
					.getProperty("LINUX_DEFAULT_IMG_DES_PATH");
			FileOperationTool.DEFAULT_VIDEO_DES_PATH = p
					.getProperty("LINUX_DEFAULT_VIDEO_DES_PATH");
			FileOperationTool.DEFAULT_AUDIO_DES_PATH = p
					.getProperty("LINUX_DEFAULT_AUDIO_DES_PATH");
			FileOperationTool.DEFAULT_TEXT_DES_PATH = p
					.getProperty("LINUX_DEFAULT_TEXT_DES_PATH");
			FileOperationTool.DEFAULT_XML_DES_PATH = p
					.getProperty("LINUX_DEFAULT_XML_DES_PATH");
			FileOperationTool.DEFAULT_ZIP_PATH = p
					.getProperty("LINUX_DEFAULT_ZIP_PATH");
			FileOperationTool.DEFAULT_FLV_PATH = p
					.getProperty("LINUX_DEFAULT_FLV_PATH");
			// 创建资源文件夹
			if (!(new File(FileOperationTool.DEFAULT_IMG_DES_PATH).exists())) {
				boolean flag = new File(FileOperationTool.DEFAULT_IMG_DES_PATH).mkdirs();
				if(log.isInfoEnabled()){
					log.info("Create \"" + FileOperationTool.DEFAULT_IMG_DES_PATH + "\" : " + flag);
				}
			}
			if (!(new File(FileOperationTool.DEFAULT_VIDEO_DES_PATH).exists())) {
				boolean flag = new File(FileOperationTool.DEFAULT_VIDEO_DES_PATH).mkdirs();
				if(log.isInfoEnabled()){
					log.info("Create \"" + FileOperationTool.DEFAULT_VIDEO_DES_PATH + "\" : " + flag);
				}
			}
			if (!(new File(FileOperationTool.DEFAULT_AUDIO_DES_PATH).exists())) {
				boolean flag = new File(FileOperationTool.DEFAULT_AUDIO_DES_PATH).mkdirs();
				if(log.isInfoEnabled()){
					log.info("Create \"" + FileOperationTool.DEFAULT_AUDIO_DES_PATH + "\" : " + flag);
				}
			}
			if (!(new File(FileOperationTool.DEFAULT_TEXT_DES_PATH).exists())) {
				boolean flag = new File(FileOperationTool.DEFAULT_TEXT_DES_PATH).mkdirs();
				if(log.isInfoEnabled()){
					log.info("Create \"" + FileOperationTool.DEFAULT_TEXT_DES_PATH + "\" : " + flag);
				}
			}
			if (!(new File(FileOperationTool.DEFAULT_FLV_PATH).exists())) {
				boolean flag = new File(FileOperationTool.DEFAULT_FLV_PATH).mkdirs();
				if(log.isInfoEnabled()){
					log.info("Create \"" + FileOperationTool.DEFAULT_FLV_PATH + "\" : " + flag);
				}
				// copy ffmpeg tools
				String src = this.getClass().getClassLoader().getResource("")
						.getPath()
						+ "tools/";
				String file1 = "drv43260.dll";
				String file2 = "ffmpeg.bat";
				String file3 = "ffmpeg.exe";
				String file4 = "ffplay.exe";
				String file5 = "mencoder.exe";
				String file6 = "pncrt.dll";
				String file7 = "pthreadGC2.dll";

				// flow player
				String file8 = "flowplayer-3.2.8.swf";
				String file9 = "flowplayer.controls-3.2.8.swf";

				// uploadify plugin
				String file10 = "uploadify.swf";
				if(log.isInfoEnabled()){
					log.info("Start to copy files from tools folder...");
				}
				FileOperationTool.copy(new File(src + file1), new File(
						FileOperationTool.DEFAULT_FLV_PATH + file1));
				FileOperationTool.copy(new File(src + file2), new File(
						FileOperationTool.DEFAULT_FLV_PATH + file2));
				FileOperationTool.copy(new File(src + file3), new File(
						FileOperationTool.DEFAULT_FLV_PATH + file3));
				FileOperationTool.copy(new File(src + file4), new File(
						FileOperationTool.DEFAULT_FLV_PATH + file4));
				FileOperationTool.copy(new File(src + file5), new File(
						FileOperationTool.DEFAULT_FLV_PATH + file5));
				FileOperationTool.copy(new File(src + file6), new File(
						FileOperationTool.DEFAULT_FLV_PATH + file6));
				FileOperationTool.copy(new File(src + file7), new File(
						FileOperationTool.DEFAULT_FLV_PATH + file7));
				FileOperationTool.copy(new File(src + file8), new File(
						FileOperationTool.DEFAULT_FLV_PATH + file8));
				FileOperationTool.copy(new File(src + file9), new File(
						FileOperationTool.DEFAULT_FLV_PATH + file9));
				FileOperationTool.copy(new File(src + file10), new File(
						FileOperationTool.DEFAULT_FLV_PATH + file10));
			}
			if (log.isInfoEnabled()) {
				log.info("create resource folder successfully...");
			}
			return true;
		} catch (FileNotFoundException e) {
			if (log.isErrorEnabled()) {
				log.error("create resource folder failed...");
				log.error(e.getMessage());
			}
			return false;
		} catch (IOException e) {
			if (log.isErrorEnabled()) {
				log.error("create resource folder failed...");
				log.error(e.getMessage());
			}
			return false;
		}
	}	
	
	private boolean createDefaultUser() {
		if (log.isInfoEnabled()) {
			log.info("InitServlet.createDefaultUser() called...");
		}
		try {
			if (log.isDebugEnabled()) {
				log.debug("createDefaultUser() called...");
			}
			UserDTO user = new UserDTO();

			int indexid = SequenceUtil.getInstance().getNextKeyValue(
					ConstantUtil.SequenceName.USER_SEQUENCE);
			user.setIndexid(indexid);
			user.setId("admin1");
			user.setPrivileges("123456789");
			user.setUserType("admin");
			String pwd = "admin1";
			DesUtils des = new DesUtils("cemso");// 自定义密钥
			String encriedPwd = des.encrypt(pwd);
			user.setUserPwd(encriedPwd);

			return userDao.addUser(user);
		} catch (InvalidKeyException e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
				log.error(e.getStackTrace());
			}
			return false;
		} catch (NoSuchAlgorithmException e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
				log.error(e.getStackTrace());
			}
			return false;
		} catch (NoSuchPaddingException e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
				log.error(e.getStackTrace());
			}
			return false;
		} catch (IllegalBlockSizeException e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
				log.error(e.getStackTrace());
			}
			return false;
		} catch (BadPaddingException e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
				log.error(e.getStackTrace());
			}
			return false;
		}
	}
}
