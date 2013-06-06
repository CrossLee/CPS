package com.cemso.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.cemso.dto.DeviceDTO;
import com.cemso.dto.PlaylistDTO;
import com.cemso.dto.ResourceDTO;
import com.cemso.dto.SystemInfoDTO;
import com.cemso.dto.TreeNodeDTO;
import com.cemso.jna.JoymindCommDLL.JoymindCommDLLLib;
import com.cemso.service.DeviceService;
import com.cemso.service.PlaylistService;
import com.cemso.service.RegisterService;
import com.cemso.service.ResourceService;
import com.cemso.util.FileOperationTool;
import com.cemso.util.XmlOperationTool;
import com.googlecode.jsonplugin.annotations.JSON;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeviceAction extends ActionSupport {

	// multi-thread to push playlist
	class PushPlaylistExecutor {
		private List<String> ips;
		private String playlistId;
		private Lock lock = new ReentrantLock();// 锁
		private Lock lockForBucket = new ReentrantLock();// 锁
		private boolean allResult = true;
		private volatile int bucketSize = 5;

		// increase the bucket size
		private void incSize() {
			lockForBucket.lock();
			if (bucketSize < 5) {
				bucketSize++;
			}
			lockForBucket.unlock();
		}

		// decrease the bucket size
		private void decSize() {
			lockForBucket.lock();
			if (bucketSize == 5) {
				bucketSize--;
			}
			lockForBucket.unlock();
		}

		class Consumer implements Runnable {
			@Override
			public void run() {
				String ip = pull();
				if (ip != null) {
					try {
						boolean flag = deviceService.pushPlaylist(playlistId,
								ip);
						allResult &= flag;
						// after one thread finished pushing playlist to one
						// device, increase the bucket size
						incSize();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		// get one ip from set
		private String pull() {
			lock.lock();
			try {
				if (ips != null && !ips.isEmpty()) {
					String ip = ips.get(0);
					ips.remove(0);
					decSize();
					return ip;
				} else {
					return null;
				}
			} finally {
				lock.unlock();
			}
		}

		public List<String> getIps() {
			return ips;
		}

		public void setIps(List<String> ips) {
			this.ips = ips;
		}

		public String getPlaylistId() {
			return playlistId;
		}

		public void setPlaylistId(String playlistId) {
			this.playlistId = playlistId;
		}

		public boolean isAllResult() {
			return allResult;
		}

		public void setAllResult(boolean allResult) {
			this.allResult = allResult;
		}
	}

	private static final long serialVersionUID = 1L;

	private DeviceService deviceService;
	private PlaylistService playlistService;

	private List<DeviceDTO> deviceList;
	private List<DeviceDTO> groupDevices;
	private List<DeviceDTO> searchedResult;

	private List<DeviceDTO> onlineDevice;
	private List<DeviceDTO> offlineDevice;
	private List<DeviceDTO> exportedDevice;

	private List<DeviceDTO> newOnlineDevices;
	private List<DeviceDTO> newOfflineDevices;

	private List<PlaylistDTO> playlist;

	private List<ResourceDTO> imageList;
	private List<ResourceDTO> resourceList;
	private ResourceService resourceService;

	private InputStream treeStream;

	private boolean addflag;
	private boolean deleteflag;

	private DeviceDTO deviceForOperation;

	// for push selected playlist
	private String pushStatus;
	private String id;
	private String ip;
	private Map<String, String> result;
	
	public Map<String, String> getResult() {
		return result;
	}

	public void setResult(Map<String, String> result) {
		this.result = result;
	}

	// for refresh one device
	private String refreshStatus;

	// for pull default pic
	private String pulledStatus;
	private String imageName;

	// no valid license
	private RegisterService registerService;
	private String errorCode;
	
	private String refreshAllStautesResult;
	
	public String getRefreshAllStautesResult() {
		return refreshAllStautesResult;
	}

	public void setRefreshAllStautesResult(String refreshAllStautesResult) {
		this.refreshAllStautesResult = refreshAllStautesResult;
	}

	public RegisterService getRegisterService() {
		return registerService;
	}

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getRefreshStatus() {
		return refreshStatus;
	}

	public void setRefreshStatus(String refreshStatus) {
		this.refreshStatus = refreshStatus;
	}

	public String refreshAllStautes(){
		try {
			if (log.isDebugEnabled()) {
				log.debug("refreshAllStautes() called...");
			}

			List<DeviceDTO> devices = deviceService.getDevices();
			boolean flag = true;
			for(DeviceDTO device : devices){
				boolean flag1 = refreshSelectedDevice(device);
				flag &= flag1;
			}
			
			if(flag){
				if (log.isDebugEnabled()) {
					log.debug("refreshAll statues result:" + flag);
				}
				refreshAllStautesResult = "true";
			}else{
				refreshAllStautesResult = "false";
			}
			
			return "refreshAllStautesResult";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return "refreshAllStautesResult";
		}
	}
	
	@SuppressWarnings("static-access")
	public boolean refreshSelectedDevice(DeviceDTO device) {
		boolean refreshStatus = false;
		try {
			// invoke DLL method to get the details of device
			JoymindCommDLLLib instance = JoymindCommDLLLib.INSTANCE;
			instance.ResetDLL();
			ip = device.getIp();
			String id = "refreshAll_" + ip;
			String iniPath = XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER + id
					+ ".ini";
			int flag = instance.GetSysInfo(ip, iniPath);
			if(log.isInfoEnabled()){
				log.info("instance.GetSysInfo("+ip+", "+iniPath+"): " + flag);
			}
			try {
				Thread.currentThread().sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// adjust system time
			boolean refreshboolean = false;
			int adjustResult = 0;
			for(int i = 0; i < 10; i++){
				adjustResult = instance.AdjustTime(ip);
				if(log.isInfoEnabled()){
					log.info("instance.AdjustTime("+ip+"):" + adjustResult);
				}
				if(adjustResult == 1){
					refreshboolean = true;
					break;
				}
				try {
					Thread.currentThread().sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if (flag == 1) {
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
					
					DeviceDTO ddto = deviceService.queryDevice(ip);
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
					refreshStatus = false;
					if (log.isErrorEnabled()) {
						log.error(e1.getMessage());
					}
					e1.printStackTrace();
				}

				boolean addFlag = deviceService.edit(device);
				if (addFlag) {
					if (log.isDebugEnabled()) {
						log.debug("update device successfully");
					}
				} else {
					if (log.isErrorEnabled()) {
						log.error("update device failed !!!");
					}
				}
				refreshStatus = true;
			} else {
				if(refreshboolean){
					boolean editflag = deviceService.editOnlineState(ip, "在线");
					if (editflag) {
						refreshStatus = true;
					} else {
						refreshStatus = false;
					}
				}else{
					boolean editflag = deviceService.editOnlineState(ip, "离线");
					if (editflag) {
						refreshStatus = true;
					} else {
						refreshStatus = false;
					}
				}
				
			}

			return refreshStatus;
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return refreshStatus;
		}
	}

	@SuppressWarnings("static-access")
	public String refreshSelectedDevice() {
		try {
			DeviceDTO device = deviceService.queryDevice(ip);
			// invoke DLL method to get the details of device
			JoymindCommDLLLib instance = JoymindCommDLLLib.INSTANCE;
			instance.ResetDLL();
			String id = "refresh_" + ip;
			String iniPath = XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER + id
					+ ".ini";
			int flag = instance.GetSysInfo(ip, iniPath);
			if(log.isInfoEnabled()){
				log.info("instance.GetSysInfo("+ip+", "+iniPath+"): " + flag);
			}
			try {
				Thread.currentThread().sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// adjust system time
			boolean refreshboolean = false;
			int adjustResult = 0;
			for(int i = 0; i < 10; i++){
				adjustResult = instance.AdjustTime(ip);
				if(log.isInfoEnabled()){
					log.info("AdjustTime("+ip+"): " + adjustResult);
				}
				if(adjustResult == 1){
					refreshboolean = true;
					break;
				}
				try {
					Thread.currentThread().sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (flag == 1) {
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

					DeviceDTO ddto = deviceService.queryDevice(ip);
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
					refreshStatus = "failed";
					if (log.isErrorEnabled()) {
						log.error(e1.getMessage());
					}
					e1.printStackTrace();
				}

				boolean addFlag = deviceService.edit(device);
				if (addFlag) {
					if (log.isDebugEnabled()) {
						log.debug("update device successfully");
					}
				} else {
					if (log.isErrorEnabled()) {
						log.error("update device failed !!!");
					}
				}
				refreshStatus = "success";
			} else {
				if(refreshboolean){
					boolean editflag = deviceService.editOnlineState(ip, "在线");
					if (editflag) {
						refreshStatus = "success";
					} else {
						refreshStatus = "failed";
					}
				}else{
					boolean editflag = deviceService.editOnlineState(ip, "离线");
					if (editflag) {
						refreshStatus = "success";
					} else {
						refreshStatus = "failed";
					}
				}
			}

			return "refreshStatus";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return "error";
		}
	}

	private static final Log log = LogFactory
			.getLog(com.cemso.action.DeviceAction.class);

	public String addDevice() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("start to add device ...");
			}
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);
			String deviceName = null;
			String ip = request.getParameter("ip");
			String deviceRemark = null;
			String groupId = request.getParameter("groupId");
			String groupText = null;

			try {
				deviceName = URLDecoder.decode(request
						.getParameter("deviceName"), "UTF-8");
				deviceRemark = URLDecoder.decode(request
						.getParameter("deviceRemark"), "UTF-8");
				groupText = URLDecoder.decode(
						request.getParameter("groupText"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				if (log.isErrorEnabled()) {
					log.error(e.getMessage());
				}
				e.printStackTrace();
			}

			String id = "device_" + System.currentTimeMillis();
			DeviceDTO device = new DeviceDTO();

			// invoke DLL method to get the details of device
			JoymindCommDLLLib instance = JoymindCommDLLLib.INSTANCE;
			instance.ResetDLL();
			String iniPath = XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER + id
					+ ".ini";
			int flag = instance.GetSysInfo(ip, iniPath);
			if(log.isInfoEnabled()){
				log.info("instance.GetSysInfo("+ip+", "+iniPath+"): " + flag);
			}
			if (flag == 1) {
				// get ini file
				SystemInfoDTO info = null;
				try {
					info = FileOperationTool.parseIni(iniPath);

					// check register
					if(!XmlOperationTool.isTest){
						boolean result = registerService.checkRegister(info.getMacAddress());
						if(!result){
							errorCode = "LICENSEERROR";
							return ERROR;
						}
					}
					
					device.setId(id);
					device.setName(deviceName);
					device.setMacAdd(info.getMacAddress());
					device.setRemark(deviceRemark);
					device.setGroupId(groupId);
					device.setGroupText(groupText);
					device.setIp(info.getIp());
					device.setGateway(info.getGateway());
					device.setOnlineState("在线");
					device.setBootTime(info.getBootTime());
					device.setDeviceVersion(info.getVersion());

					// convert BYTE to M
					String freeSpace = info.getFreedisk();
//					String freeSpace1 = "";
//					if (!freeSpace.startsWith("-")) {
//						freeSpace1 = String
//								.valueOf(Long.parseLong(freeSpace) / 1024 );
//					}
					device.setAvailableSpace(freeSpace);

					device.setpNo(info.getpNo());
					device.setWidth(info.getWidth());
					device.setHeight(info.getHeight());
					device.setStorage(info.getStorage());
					device.setTime(info.getTime());
					device.setPrgTotal(info.getPrgTotal());
					device.setPrgIndex(info.getPrgIndex());
					device.setStatusNo(info.getStatus());

					if (info.getStorage().equals("1")) {
						device.setStorageName("FLASH存储");
					}
					if (info.getStorage().equals("2")) {
						device.setStorageName("RAM存储");
					}
					if (info.getStorage().equals("3")) {
						device.setStorageName("SD卡存储");
					}

					device.setCurrentPlaylist("");
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
					return ERROR;
				}

				boolean addFlag = deviceService.addDevice(device);
				if (addFlag) {
					if (log.isDebugEnabled()) {
						log.debug("add device successfully");
						log.debug("start to get device list");
					}
					deviceList = deviceService.getDevices();
					return SUCCESS;
				} else {
					if (log.isErrorEnabled()) {
						log.error("add device failed !!!");
					}
					return ERROR;
				}
			} else {
				if (log.isErrorEnabled()) {
					log.error("the JNA call return 0!");
				}
				return ERROR;
			}
		} catch (NumberFormatException e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// edit device
	public String editDevice() {
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);
			String editDeviceId = request.getParameter("editDeviceId");
			
			String editDeviceName = URLDecoder.decode(request
					.getParameter("editDeviceName"), "UTF-8");;
			String editDeviceRemark = URLDecoder.decode(request
					.getParameter("editDeviceRemark"), "UTF-8");;
			DeviceDTO deviceForUpdate = new DeviceDTO();
			deviceList = deviceService.getDevices();
			for (int i = 0; i < deviceList.size(); i++) {
				DeviceDTO device = deviceList.get(i);
				if (device.getId().equals(editDeviceId)) {
					device.setName(editDeviceName);
					device.setRemark(editDeviceRemark);
					deviceForUpdate = device;
					break;
				}
			}

			boolean flag = deviceService.edit(deviceForUpdate);
			if (flag) {
				return SUCCESS;
			} else {
				return ERROR;
			}
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// delete device
	public String delDevice() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("start to delete device...");
			}

			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);
			// device id
			String id = request.getParameter("id");
			return deviceService.deleteDevice(id) ? SUCCESS : ERROR;
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// delete selected device
	public String delSelectedDevice() {
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);
			// device id
			String ids = request.getParameter("ids");
			String[] id = ids.split(",");
			int size = id.length;
			boolean flag = true;
			for (int i = 0; i < size; i++) {
				flag = flag && deviceService.deleteDevice(id[i]);
			}
			return flag ? SUCCESS : ERROR;
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	@JSON(serialize = false)
	public String getDevice() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("start to get device list...");
			}
			ActionContext context = ActionContext.getContext();
			HttpServletResponse response = (HttpServletResponse) context
					.get(ServletActionContext.HTTP_RESPONSE);

			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			deviceList = deviceService.getDevices();

			if (log.isDebugEnabled()) {
				log.debug("get device list successfully");
			}
			return "devicelist";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	@JSON(serialize = false)
	public String getGroupDeviceList() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("start to get group device list...");
			}
			
			boolean flag = registerService.isNoDeletedExist();
			if(!flag){
				if(log.isWarnEnabled()){
					log.warn("there is no device flag with no deleted in register table!!!");
				}
				errorCode = "没有合法的License，请联系管理员！";
				return ERROR;
			}
			
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);
			String groupId = request.getParameter("groupId");

			deviceList = deviceService.getDevices();

			groupDevices = new ArrayList<DeviceDTO>();
			for (DeviceDTO device : deviceList) {
				if (device.getGroupId().equals(groupId)) {
					groupDevices.add(device);
				}
			}
			if (log.isDebugEnabled()) {
				log.debug("get group device list successfully");
			}
			return SUCCESS;
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	@JSON(serialize = false)
	public String getTreeXml() {
		try {
			try {
				ActionContext context = ActionContext.getContext();
				HttpServletRequest request = (HttpServletRequest) context
						.get(ServletActionContext.HTTP_REQUEST);
				String treeXmlPath = request.getSession().getServletContext()
						.getRealPath("/")
						+ "tree.xml";
				String xmlcontent = XmlOperationTool
						.returnStringOfXml(treeXmlPath);

				treeStream = new ByteArrayInputStream(xmlcontent
						.getBytes("GBK"));

				return SUCCESS;
			} catch (IOException e) {
				e.printStackTrace();
				return ERROR;
			}
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// add node to tree.xml
	public String addNodeToTreeXml() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("start to add node to tree.xml ...");
			}
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);
			String fatherId = request.getParameter("fatherId");
			String childId = request.getParameter("childId");
			String value = null;
			String actionUrl = null;

			try {
				value = URLDecoder.decode(request.getParameter("value"),
						"UTF-8");
				actionUrl = URLDecoder.decode(
						request.getParameter("actionUrl"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				if (log.isDebugEnabled()) {
					log.error(e.getMessage());
				}
				e.printStackTrace();
			}

			String treeXmlPath = request.getSession().getServletContext()
					.getRealPath("/")
					+ "tree.xml";

			TreeNodeDTO node = new TreeNodeDTO();
			node.setParentId(fatherId);
			node.setId(childId);
			node.setText(value);
			node.setAction(actionUrl);
			node.setExpanded("true");
			addflag = XmlOperationTool.addNodeToTreeXml(node, treeXmlPath);

			// backup tree.xml
			try {
				File f1 = new File(XmlOperationTool.CONFIG_FOLDER + "tree.xml");
				if (f1.exists()) {
					f1.deleteOnExit();
					FileOperationTool.ChannelCopy(new File(
							XmlOperationTool.WEB_CONTENT + "tree.xml"),
							new File(XmlOperationTool.CONFIG_FOLDER
									+ "tree.xml"));
				}
			} catch (Exception e) {
				if (log.isErrorEnabled()) {
					log.error(e.getMessage());
				}
				e.printStackTrace();
			}

			if (log.isDebugEnabled()) {
				log.debug("add node to tree.xml state:" + addflag);
			}
			return "deviceObject";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// delete node from tree.xml
	public String delNodeToTreeXml() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("start to delete node from tree.xml ...");
			}
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);
			String nodeId = request.getParameter("nodeId");

			String treeXmlPath = request.getSession().getServletContext()
					.getRealPath("/")
					+ "tree.xml";

			TreeNodeDTO node = new TreeNodeDTO();
			node.setId(nodeId);
			deleteflag = XmlOperationTool.delNodeToTreeXml(node, treeXmlPath);
			if (log.isDebugEnabled()) {
				log.debug("delete node from tree.xml state:" + deleteflag);
			}
			
			if(deleteflag){
				// backup tree.xml
				try {
					File f1 = new File(XmlOperationTool.CONFIG_FOLDER + "tree.xml");
					if (f1.exists()) {
						f1.deleteOnExit();
						FileOperationTool.ChannelCopy(new File(
								XmlOperationTool.WEB_CONTENT + "tree.xml"),
								new File(XmlOperationTool.CONFIG_FOLDER
										+ "tree.xml"));
					}
				} catch (Exception e) {
					if (log.isErrorEnabled()) {
						log.error(e.getMessage());
					}
					e.printStackTrace();
				}
			}
			
			return "deviceObject";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// get device monitor list
	public String getMinitor() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("start to get stated device list ...");
			}

			// for pull default pic
			imageList = new ArrayList<ResourceDTO>();
			resourceList = resourceService.getResourceList();
			for (ResourceDTO resource : resourceList) {
				if (resource.getResourceType().equals("image")
						&& resource.getResourceName().endsWith(".jpg")) {
					imageList.add(resource);
				}
			}

			Map<String, List<DeviceDTO>> statedDeviceMap = deviceService
					.getStatedDevices();
			onlineDevice = statedDeviceMap.get("online");
			offlineDevice = statedDeviceMap.get("offline");

			Map<String, Object> session = ActionContext.getContext()
					.getSession();

			// put the first onlice device list into session
			if (!session.containsKey("onlineDeviceList")) {
				session.put("onlineDeviceList", onlineDevice);
			}
			// get the playlist for pushing to device
			playlist = playlistService.getPlaylist();
			if (log.isDebugEnabled()) {
				log.debug("get stated device list successfully");
			}
			return SUCCESS;
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// refresh monitor list
	@SuppressWarnings("unchecked")
	public String refreshMinitor() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("start to get stated device list ...");
			}
			Map<String, List<DeviceDTO>> statedDeviceMap = deviceService
					.getStatedDevices();
			onlineDevice = statedDeviceMap.get("online");
			offlineDevice = statedDeviceMap.get("offline");

			Map<String, Object> session = ActionContext.getContext()
					.getSession();

			if (session.containsKey("onlineDeviceList")) {
				List<DeviceDTO> lastTimeOnlineDeviceList = (List<DeviceDTO>) session
						.get("onlineDeviceList");
				newOnlineDevices = new ArrayList<DeviceDTO>();
				newOfflineDevices = new ArrayList<DeviceDTO>();

				for (int i = 0; i < lastTimeOnlineDeviceList.size(); i++) {
					DeviceDTO temp = lastTimeOnlineDeviceList.get(i);
					int tempIndex = 0;
					for (int j = 0; j < onlineDevice.size(); j++) {
						if (onlineDevice.get(j).getId().equals(temp.getId())) {
							continue;
						} else {
							tempIndex++;
						}
					}
					if (tempIndex == onlineDevice.size()) {
						newOfflineDevices.add(temp);
					}
				}

				for (int i = 0; i < onlineDevice.size(); i++) {
					DeviceDTO temp = onlineDevice.get(i);
					int tempIndex = 0;
					for (int j = 0; j < lastTimeOnlineDeviceList.size(); j++) {
						if (lastTimeOnlineDeviceList.get(j).getId().equals(
								temp.getId())) {
							continue;
						} else {
							tempIndex++;
						}
					}
					if (tempIndex == lastTimeOnlineDeviceList.size()) {
						newOnlineDevices.add(temp);
					}
				}
				session.remove("onlineDeviceList");
				session.put("onlineDeviceList", onlineDevice);
			} else {
				session.put("onlineDeviceList", onlineDevice);
			}

			if (log.isDebugEnabled()) {
				log.debug("get stated device list successfully");
			}
			return SUCCESS;
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// search device
	public String searchMinitor() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("start to search device list ...");
			}
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);
			String searchWords = request.getParameter("searchWords");

			searchedResult = deviceService.searchedDevices(searchWords);

			if (log.isDebugEnabled()) {
				log.debug("start to search device list successfully");
			}
			return SUCCESS;
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// export to xls
	public String exportDevice() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("start to export to xls ...");
			}
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);
			HttpServletResponse response = (HttpServletResponse) context
					.get(ServletActionContext.HTTP_RESPONSE);
			String deviceType = request.getParameter("deviceType");
			Map<String, List<DeviceDTO>> statedDeviceMap = deviceService
					.getStatedDevices();

			if ("online".equals(deviceType)) {
				exportedDevice = statedDeviceMap.get("online");
			}
			if ("offline".equals(deviceType)) {
				exportedDevice = statedDeviceMap.get("offline");
			}
			if (log.isDebugEnabled()) {
				log.debug("export to xls successfully");
			}

			response.setHeader("Content-disposition",
					"attachment; filename=MyExcel.xls");

			return SUCCESS;
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// select one device and more operation
	public String monitorOperation() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("start to get device details for more operation...");
			}
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);
			String deviceId = request.getParameter("deviceId");
			List<DeviceDTO> devices = deviceService.getDevices();
			for (DeviceDTO device : devices) {
				if (device.getId().equals(deviceId)) {
					deviceForOperation = device;
					break;
				}
			}
			if (log.isDebugEnabled()) {
				log.debug("get device details successfully for more operation");
			}
			return SUCCESS;
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	public String pushPlaylist() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("the selected playlist is: " + id);
				log.debug("the device's ip address is : " + ip);
			}
			boolean flag = true;
			result = new HashMap<String, String>();
			if (ip.contains("_") && ip.length() > 15) {
				ip = ip.substring(1);
				String[] ips = ip.split("_");
				try {
					for (int i = 0; i < ips.length; i++) {
						flag = deviceService.pushPlaylist(id, ips[i]);
						DeviceDTO de = deviceService.queryDevice(ips[i]);
						PlaylistDTO pushedPlaylistDTO = playlistService.queryPlaylistDTOById(id);
						log.debug("push playlist: " + ips[i] + "," + flag);
						if(flag){
							if(pushedPlaylistDTO !=null && de != null){
								de.setCurrentPlaylist(pushedPlaylistDTO.getName()+",【成功】");
							}
						}else{
							if(pushedPlaylistDTO !=null && de != null){
								de.setCurrentPlaylist(pushedPlaylistDTO.getName()+",【失败】");
							}
						}
						boolean ff = deviceService.edit(de);
						if(ff){
							log.info("update last playlist " + pushedPlaylistDTO.getName() + " successfully for ip: " + ips[i]);
						}else{
							log.warn("update last playlist " + pushedPlaylistDTO.getName() + " failed for ip: " + ips[i]);
						}
						result.put(ips[i], String.valueOf(flag));
						flag &= true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				if (ip.contains("_")) {
					ip = ip.replace("_", "");
				}
				try {
					flag = deviceService.pushPlaylist(id, ip);
					DeviceDTO de = deviceService.queryDevice(ip);
					PlaylistDTO pushedPlaylistDTO = playlistService.queryPlaylistDTOById(id);
					if(flag){
						de.setCurrentPlaylist(pushedPlaylistDTO.getName()+",【成功】");
					}else{
						de.setCurrentPlaylist(pushedPlaylistDTO.getName()+",【失败】");
					}
					boolean ff = deviceService.edit(de);
					if(ff){
						log.info("update last playlist " + pushedPlaylistDTO.getName() + " successfully for ip: " + ip);
					}else{
						log.warn("update last playlist " + pushedPlaylistDTO.getName() + " failed for ip: " + ip);
					}
					result.put(ip, String.valueOf(flag));
				} catch (Exception e) {
					if (log.isErrorEnabled()) {
						log.error(e.getMessage());
					}
					e.printStackTrace();
					flag = false;
				}
			}
			if (flag) {
				pushStatus = "true";
			} else {
				pushStatus = "false";
			}
			return "pushStatus";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	/**
	 * Mutil thread push selected playlist
	 */
	public String pushPlaylist1() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("the selected playlist is: " + id);
				log.debug("the device's ip address is : " + ip);
			}
			boolean flag = false;

			if (ip.contains("_") && ip.length() > 15) {
				ip = ip.substring(1);
				String[] ips = ip.split("_");
				List<String> ipList = Collections
						.synchronizedList(new ArrayList<String>());
				for (int i = 0; i < ips.length; i++) {
					ipList.add(ips[i]);
				}
				PushPlaylistExecutor executor = new PushPlaylistExecutor();
				executor.setPlaylistId(id);
				executor.setIps(ipList);

				while (executor.ips != null && !executor.ips.isEmpty()) {
					if (executor.bucketSize <= 5) {
						new Thread(executor.new Consumer()).start();
					}
				}
			} else {
				if (ip.contains("_")) {
					ip = ip.replace("_", "");
				}
				try {
					flag = deviceService.pushPlaylist(id, ip);
				} catch (Exception e) {
					if (log.isErrorEnabled()) {
						log.error(e.getMessage());
					}
					e.printStackTrace();
					flag = false;
				}
			}
			if (flag) {
				pushStatus = "true";
			} else {
				pushStatus = "false";
			}
			return "pushStatus";
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// pull default pic
	public String pullDefaultPic() {

		try {
			if (log.isDebugEnabled()) {
				log.debug("the selected pic name is : " + imageName);
				log.debug("the device's ip address is : " + ip);
			}
			boolean flag = false;
			try {
				flag = deviceService.pullDefaultPic(imageName, ip);
			} catch (Exception e) {
				if (log.isErrorEnabled()) {
					log.error(e.getMessage());
				}
				e.printStackTrace();
				flag = false;
			}
			if (flag) {
				pulledStatus = "true";
			} else {
				pulledStatus = "false";
			}
			return "pulledStatus";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public List<DeviceDTO> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<DeviceDTO> deviceList) {
		this.deviceList = deviceList;
	}

	public List<DeviceDTO> getGroupDevices() {
		return groupDevices;
	}

	public void setGroupDevices(List<DeviceDTO> groupDevices) {
		this.groupDevices = groupDevices;
	}

	public boolean isAddflag() {
		return addflag;
	}

	public void setAddflag(boolean addflag) {
		this.addflag = addflag;
	}

	public boolean isDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(boolean deleteflag) {
		this.deleteflag = deleteflag;
	}

	public InputStream getTreeStream() {
		return treeStream;
	}

	public void setTreeStream(InputStream treeStream) {
		this.treeStream = treeStream;
	}

	public List<DeviceDTO> getOnlineDevice() {
		return onlineDevice;
	}

	public void setOnlineDevice(List<DeviceDTO> onlineDevice) {
		this.onlineDevice = onlineDevice;
	}

	public List<DeviceDTO> getOfflineDevice() {
		return offlineDevice;
	}

	public void setOfflineDevice(List<DeviceDTO> offlineDevice) {
		this.offlineDevice = offlineDevice;
	}

	public List<DeviceDTO> getExportedDevice() {
		return exportedDevice;
	}

	public void setExportedDevice(List<DeviceDTO> exportedDevice) {
		this.exportedDevice = exportedDevice;
	}

	public List<DeviceDTO> getSearchedResult() {
		return searchedResult;
	}

	public void setSearchedResult(List<DeviceDTO> searchedResult) {
		this.searchedResult = searchedResult;
	}

	public List<PlaylistDTO> getPlaylist() {
		return playlist;
	}

	public void setPlaylist(List<PlaylistDTO> playlist) {
		this.playlist = playlist;
	}

	public PlaylistService getPlaylistService() {
		return playlistService;
	}

	public void setPlaylistService(PlaylistService playlistService) {
		this.playlistService = playlistService;
	}

	public DeviceDTO getDeviceForOperation() {
		return deviceForOperation;
	}

	public void setDeviceForOperation(DeviceDTO deviceForOperation) {
		this.deviceForOperation = deviceForOperation;
	}

	public List<DeviceDTO> getNewOnlineDevices() {
		return newOnlineDevices;
	}

	public void setNewOnlineDevices(List<DeviceDTO> newOnlineDevices) {
		this.newOnlineDevices = newOnlineDevices;
	}

	public List<DeviceDTO> getNewOfflineDevices() {
		return newOfflineDevices;
	}

	public void setNewOfflineDevices(List<DeviceDTO> newOfflineDevices) {
		this.newOfflineDevices = newOfflineDevices;
	}

	public String getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public List<ResourceDTO> getImageList() {
		return imageList;
	}

	public void setImageList(List<ResourceDTO> imageList) {
		this.imageList = imageList;
	}

	public List<ResourceDTO> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<ResourceDTO> resourceList) {
		this.resourceList = resourceList;
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public String getPulledStatus() {
		return pulledStatus;
	}

	public void setPulledStatus(String pulledStatus) {
		this.pulledStatus = pulledStatus;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
}
