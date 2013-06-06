package com.cemso.service;

import java.util.List;
import java.util.Map;

import com.cemso.dto.DeviceDTO;

public interface DeviceService {

	public List<DeviceDTO> getDevices();
	public Map<String,List<DeviceDTO>> getStatedDevices();
	public boolean addDevice(DeviceDTO device);
	public boolean deleteDevice(String deviceId);
	public boolean deleteAllDevices();
	public boolean edit(DeviceDTO device);
	public List<DeviceDTO> searchedDevices(String keyword);
	public boolean pushPlaylist(String playlistId,String ip) throws Exception;
	/**
	 * @param ip
	 * @param string
	 */
	public boolean editOnlineState(String ip, String string);
	/**
	 * @param ip
	 * @return
	 */
	public DeviceDTO queryDevice(String ip);
	/**
	 * @param imageName
	 * @param ip
	 * @return
	 */
	public boolean pullDefaultPic(String imageName, String ip); 
}
