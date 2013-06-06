package com.cemso.dto;

public class DeviceDTO {

	private Integer indexid;
	
	public Integer getIndexid() {
		return indexid;
	}
	public void setIndexid(Integer indexid) {
		this.indexid = indexid;
	}
	private String id;						// 设备id
	private String name;					// 设备名称
	private String macAdd;					// mac地址
	private String remark;					// 备注
	private String groupId;					// 所属分组id
	private String groupText;				// 所属分组name
	
	private String ip;						// ip地址
	private String gateway;					// 网关
	private String onlineState;				// 是否在线
	private String currentPlaylist;			// 当前播放节目名
	private String currentTemplate;			// 当前播放模板名
	private String bootTime;				// 开机时间
	private String lastCommunicateTime;		// 上次通信时间
	private String currentTransferFileName;	// 当前传输的文件名
	private String currentTransferSpeed;	// 当前下载（推送）速度
	private String currentPercentage;		// 下载（推送）百分比
	private String deviceVersion;			// 设备版本
	private String volume;					// 音量
	private String autoBootTime;			// 定时开机时间
	private String autoShutdownTime;		// 定时关机时间
	private String playingVideo;			// 播放视频
	private String programUpdateTime; 		// 节目更新时间
	private String programUpdateState;		// 节目更新状态
	private String availableSpace;			// 存储设备剩余空间
	
	private String pNo;
	private String width;
	private String height;
	private String storage;
	private String time;
	private String prgTotal;
	private String prgIndex;
	private String statusNo;
	private String storageName;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMacAdd() {
		return macAdd;
	}
	public void setMacAdd(String macAdd) {
		this.macAdd = macAdd;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
	public String getOnlineState() {
		return onlineState;
	}
	public void setOnlineState(String onlineState) {
		this.onlineState = onlineState;
	}
	public String getCurrentPlaylist() {
		return currentPlaylist;
	}
	public void setCurrentPlaylist(String currentPlaylist) {
		this.currentPlaylist = currentPlaylist;
	}
	public String getCurrentTemplate() {
		return currentTemplate;
	}
	public void setCurrentTemplate(String currentTemplate) {
		this.currentTemplate = currentTemplate;
	}
	public String getBootTime() {
		return bootTime;
	}
	public void setBootTime(String bootTime) {
		this.bootTime = bootTime;
	}
	public String getLastCommunicateTime() {
		return lastCommunicateTime;
	}
	public void setLastCommunicateTime(String lastCommunicateTime) {
		this.lastCommunicateTime = lastCommunicateTime;
	}
	public String getCurrentTransferFileName() {
		return currentTransferFileName;
	}
	public void setCurrentTransferFileName(String currentTransferFileName) {
		this.currentTransferFileName = currentTransferFileName;
	}
	public String getCurrentTransferSpeed() {
		return currentTransferSpeed;
	}
	public void setCurrentTransferSpeed(String currentTransferSpeed) {
		this.currentTransferSpeed = currentTransferSpeed;
	}
	public String getCurrentPercentage() {
		return currentPercentage;
	}
	public void setCurrentPercentage(String currentPercentage) {
		this.currentPercentage = currentPercentage;
	}
	public String getDeviceVersion() {
		return deviceVersion;
	}
	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getAutoBootTime() {
		return autoBootTime;
	}
	public void setAutoBootTime(String autoBootTime) {
		this.autoBootTime = autoBootTime;
	}
	public String getAutoShutdownTime() {
		return autoShutdownTime;
	}
	public void setAutoShutdownTime(String autoShutdownTime) {
		this.autoShutdownTime = autoShutdownTime;
	}
	public String getPlayingVideo() {
		return playingVideo;
	}
	public void setPlayingVideo(String playingVideo) {
		this.playingVideo = playingVideo;
	}
	public String getProgramUpdateTime() {
		return programUpdateTime;
	}
	public void setProgramUpdateTime(String programUpdateTime) {
		this.programUpdateTime = programUpdateTime;
	}
	public String getProgramUpdateState() {
		return programUpdateState;
	}
	public void setProgramUpdateState(String programUpdateState) {
		this.programUpdateState = programUpdateState;
	}
	public String getAvailableSpace() {
		return availableSpace;
	}
	public void setAvailableSpace(String availableSpace) {
		this.availableSpace = availableSpace;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroupText() {
		return groupText;
	}
	public void setGroupText(String groupText) {
		this.groupText = groupText;
	}
	public String getpNo() {
		return pNo;
	}
	public void setPNo(String pNo) {
		this.pNo = pNo;
	}
	public String getPNo() {
		return pNo;
	}
	public void setpNo(String pNo) {
		this.pNo = pNo;
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
		return storage;
	}
	public void setStorage(String storage) {
		this.storage = storage;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
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
	public String getStatusNo() {
		return statusNo;
	}
	public void setStatusNo(String statusNo) {
		this.statusNo = statusNo;
	}
	public String getStorageName() {
		return storageName;
	}
	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}
	
}
