package com.cemso.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.cemso.dto.RegisterDTO;
import com.cemso.dto.ResourceDTO;
import com.cemso.dto.UserDTO;
import com.cemso.service.DeviceService;
import com.cemso.service.RegisterService;
import com.cemso.service.ResourceService;
import com.cemso.util.ConstantUtil;
import com.cemso.util.FileOperationTool;
import com.cemso.util.SequenceUtil;
import com.cemso.util.VideoConverter;
import com.cemso.util.XmlOperationTool;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FileUploadAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory
			.getLog(com.cemso.action.FileUploadAction.class);

	private File uploadify;

	private String uploadifyFileName;
	private String fileext;
	private String folder;

	private ResourceDTO resource = new ResourceDTO();
	private ResourceService resourceService;
	
	private RegisterService registerService;
	private DeviceService deviceService;
	
	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public RegisterService getRegisterService() {
		return registerService;
	}

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public String execute() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("start to upload file...");
			}

			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context.get(ServletActionContext.HTTP_REQUEST);
			HttpServletResponse response = ServletActionContext.getResponse();
			Map<String, Object> session = context.getSession();
			response.setCharacterEncoding("GBK"); // 务必，防止返回文件名是乱码
			String type = request.getParameter("type");
			if (type != null && type.equals("register")) {
				log.debug("upload license");
				SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				String datetime = tempDate.format(new java.util.Date());
				String desFilePath = XmlOperationTool.CONFIG_FOLDER + datetime + "_LICENSE.TXT";
				boolean bool = false;
				if (uploadify != null) {
					FileOperationTool.ChannelCopy(uploadify, new File(desFilePath));
					InputStreamReader isr = new InputStreamReader(new FileInputStream(desFilePath), "GBK");
					BufferedReader br = new BufferedReader(isr);
					long current = System.currentTimeMillis();
					String firstLine = br.readLine();
					String data = "";
					int months = 1;
					if(firstLine.startsWith("1")){
						data = firstLine.substring(2);
						months = Integer.parseInt(firstLine.substring(1, 2));
					}
					if(firstLine.startsWith("2")){
						data = firstLine.substring(3);
						months = Integer.parseInt(firstLine.substring(1, 3));
					}
					if(firstLine.startsWith("3")){
						data = firstLine.substring(4);
						months = Integer.parseInt(firstLine.substring(1, 4));
					}
					String startdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
					String expdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current + 30l*months*24*3600*1000));
					String key = "";
					List<RegisterDTO> registers = new ArrayList<RegisterDTO>();
					if(data != null && !data.trim().isEmpty()){
						if(Long.parseLong(data) >= current){
							log.warn("Invalid License file");
							registerService.addChecklog(ConstantUtil.CheckLogs.INVALID,ConstantUtil.CheckLogs.TYPE_INVALID);
							response.getWriter().print("不合法的License！");
							return null;
						}
						key = data;
						data = br.readLine();
					}else{
						log.warn("Invalid License file");
						registerService.addChecklog(ConstantUtil.CheckLogs.INVALID,ConstantUtil.CheckLogs.TYPE_INVALID);
						response.getWriter().print("不合法的License！");
						return null;
					}
					while (data != null && !data.trim().isEmpty()) {
						int indexid = SequenceUtil.getInstance().getNextKeyValue(ConstantUtil.SequenceName.REGISTER_SEQUENCE);
						String device = data.trim();
						RegisterDTO rd = new RegisterDTO(indexid,startdate,expdate,key,device,ConstantUtil.Register.NO);
						registers.add(rd);
						data = br.readLine();
					}
					br.close();
					isr.close();
					//registerService.updateToDelete();
					// delete the old devices
					/*
					boolean f = deviceService.deleteAllDevices();
					if(log.isDebugEnabled()){
						log.debug("Delete all devices result is : " + f);
					}
					*/
					bool = registerService.add(registers);
					if(log.isDebugEnabled()){
						log.debug("Add register devices result is : " + bool);
					}
				}
				
				try {
					if(bool){
						// return response
						response.getWriter().print(uploadifyFileName + " 上传成功");
						registerService.addChecklog(ConstantUtil.CheckLogs.VALID, ConstantUtil.CheckLogs.TYPE_VALID);
						if (log.isDebugEnabled()) {
							log.debug("upload file successfully");
						}
					}else{
						if (log.isErrorEnabled()) {
							log.error("Add License failed !");
						}
					}
				} catch (IOException e) {
					if (log.isErrorEnabled()) {
						log.error("upload file failed !!!");
						log.error(e.getMessage());
					}
				}
				return null;
			}

			String desFilePath = null;
			String currentMillins = Long.toString(System.currentTimeMillis());
			String resourceId = "resource_" + currentMillins;
			if (FileOperationTool.getResourceType(uploadifyFileName).equals(
					FileOperationTool.IMAGETYPE)) {
				resource.setResourceType("image");
				desFilePath = FileOperationTool.DEFAULT_IMG_DES_PATH
						+ currentMillins + "_" + uploadifyFileName;
			}
			if (FileOperationTool.getResourceType(uploadifyFileName).equals(
					FileOperationTool.AUDIOTYPE)) {
				resource.setResourceType("audio");
				desFilePath = FileOperationTool.DEFAULT_AUDIO_DES_PATH
						+ currentMillins + "_" + uploadifyFileName;
			}
			if (FileOperationTool.getResourceType(uploadifyFileName).equals(
					FileOperationTool.TEXTTYPE)) {
				resource.setResourceType("text");
				desFilePath = FileOperationTool.DEFAULT_TEXT_DES_PATH
						+ currentMillins + "_" + uploadifyFileName;
			}
			if (FileOperationTool.getResourceType(uploadifyFileName).equals(
					FileOperationTool.VIDEOTYPE)) {
				resource.setResourceType("video");
				desFilePath = FileOperationTool.DEFAULT_VIDEO_DES_PATH
						+ currentMillins + "_" + uploadifyFileName;
			}

			FileOperationTool.ChannelCopy(uploadify, new File(desFilePath));

			// if resource type is video, convert it to flv format
			if (resource.getResourceType().equals("video") && XmlOperationTool.isWindos) {
				VideoConverter
						.convert(currentMillins + "_" + uploadifyFileName);
			}

			if (uploadify != null) {
				resource.setId(resourceId);
				resource.setResourceName(currentMillins + "_"
						+ uploadifyFileName);
				if (!resource.getResourceType().equals("text")) {
					resource.setParamRemark(uploadifyFileName);
				} else {
					String txtContent = FileOperationTool
							.getContentOfTxt(desFilePath);
					resource.setParamRemark(txtContent);
				}
				resource.setParamCreateby(((UserDTO) session.get("user"))
						.getId());
				resource
						.setParamCreatetime(new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss").format(uploadify
								.lastModified()));
				resource.setCanBeDelete(true);
				if (new File(desFilePath).exists()) {
					resourceService.AddResource(resource);
				}
			}

			try {
				// return response
				response.getWriter().print(uploadifyFileName + " 上传成功");
				if (log.isDebugEnabled()) {
					log.debug("upload file successfully");
				}
			} catch (IOException e) {
				if (log.isErrorEnabled()) {
					log.error("upload file failed !!!");
					log.error(e.getMessage());
				}
			}
			return null;

		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("upload file failed !!!");
				log.error(e.getMessage());
			}
			return ERROR;
		}

	}

	public File getUploadify() {
		return uploadify;
	}

	public void setUploadify(File uploadify) {
		this.uploadify = uploadify;
	}

	public String getUploadifyFileName() {
		return uploadifyFileName;
	}

	public void setUploadifyFileName(String uploadifyFileName) {
		this.uploadifyFileName = uploadifyFileName;
	}

	public String getFileext() {
		return fileext;
	}

	public void setFileext(String fileext) {
		this.fileext = fileext;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}
}
