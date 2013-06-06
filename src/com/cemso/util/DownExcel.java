package com.cemso.util;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.cemso.dto.DeviceDTO;
import com.cemso.service.DeviceService;
import com.cemso.service.impl.DeviceServiceImpl;

public class DownExcel {

	public HSSFWorkbook downExcel(String deviceType) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();

		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("名称");
		cell = row.createCell(1);
		cell.setCellValue("局域网IP");
		cell = row.createCell(2);
		cell.setCellValue("在线状态");
		cell = row.createCell(3);
		cell.setCellValue("启动时间");
		cell = row.createCell(4);
		cell.setCellValue("下次关机时间");
		cell = row.createCell(5);
		cell.setCellValue("当前播放播表");

		DeviceService deviceService = new DeviceServiceImpl();

		Map<String, List<DeviceDTO>> statedDeviceMap = deviceService
				.getStatedDevices();

		if ("online".equals(deviceType)) {
			List<DeviceDTO> exportedDevice = statedDeviceMap.get("online");
			for (int i = 0; i < exportedDevice.size(); i++) {
				HSSFRow rowTemp = sheet.createRow(i + 1);
				DeviceDTO device = exportedDevice.get(i);
				HSSFCell cellTemp = rowTemp.createCell(0);
				cellTemp.setCellValue(device.getName());
				cellTemp = rowTemp.createCell(1);
				cellTemp.setCellValue(device.getIp());
				cellTemp = rowTemp.createCell(2);
				cellTemp.setCellValue(device.getOnlineState());
				cellTemp = rowTemp.createCell(3);
				cellTemp.setCellValue(device.getBootTime());
				cellTemp = rowTemp.createCell(4);
				cellTemp.setCellValue(device.getAutoShutdownTime());
				cellTemp = rowTemp.createCell(5);
				cellTemp.setCellValue(device.getCurrentPlaylist());
			}
		}
		if ("offline".equals(deviceType)) {
			List<DeviceDTO> exportedDevice = statedDeviceMap.get("offline");
			for (int i = 0; i < exportedDevice.size(); i++) {
				HSSFRow rowTemp = sheet.createRow(i + 1);
				DeviceDTO device = exportedDevice.get(i);
				HSSFCell cellTemp = rowTemp.createCell(0);
				cellTemp.setCellValue(device.getName());
				cellTemp = rowTemp.createCell(1);
				cellTemp.setCellValue(device.getIp());
				cellTemp = rowTemp.createCell(2);
				cellTemp.setCellValue(device.getOnlineState());
				cellTemp = rowTemp.createCell(3);
				cellTemp.setCellValue(device.getBootTime());
				cellTemp = rowTemp.createCell(4);
				cellTemp.setCellValue(device.getAutoShutdownTime());
				cellTemp = rowTemp.createCell(5);
				cellTemp.setCellValue(device.getCurrentPlaylist());
			}
		}

		// cell auto size
		HSSFRow rows = sheet.getRow(0);
		int coloumNum = rows.getPhysicalNumberOfCells();
		for (int i = 0; i < coloumNum; i++) {
			sheet.autoSizeColumn(i);
		}

		return wb;
	}
}
