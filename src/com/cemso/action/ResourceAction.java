package com.cemso.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.cemso.dto.ResourceDTO;
import com.cemso.service.RegisterService;
import com.cemso.service.ResourceService;
import com.cemso.util.FileOperationTool;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ResourceAction extends ActionSupport {

	private static final Log log = LogFactory.getLog(ResourceAction.class);
	private static final long serialVersionUID = 1L;
	private ResourceService resourceService;
	private RegisterService registerService;
	private List<ResourceDTO> resourceList;

	private List<ResourceDTO> videoList;
	private List<ResourceDTO> imageList;
	private List<ResourceDTO> audioList;
	private List<ResourceDTO> textList;

	public String execute() {
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);
			String resourceType = request.getParameter("type");

			imageList = new ArrayList<ResourceDTO>();
			videoList = new ArrayList<ResourceDTO>();
			audioList = new ArrayList<ResourceDTO>();
			textList = new ArrayList<ResourceDTO>();

			resourceList = resourceService.getResourceList();
			if(resourceList == null){
				return "error";
			}
			for (ResourceDTO resource : resourceList) {
				if (resource.getResourceType().equals("video")) {
					videoList.add(resource);
				}
				if (resource.getResourceType().equals("audio")) {
					audioList.add(resource);
				}
				if (resource.getResourceType().equals("image")) {
					imageList.add(resource);
				}
				if (resource.getResourceType().equals("text")) {
					textList.add(resource);
				}
			}

			// text
			if (resourceType.equals("#box-1")) {
				return "textpage";
			}
			// audio
			if (resourceType.equals("#box-2")) {
				return "audiopage";
			}
			// image
			if (resourceType.equals("#box-3")) {
				return "imagepage";
			}
			// video
			if (resourceType.equals("#box-4")) {
				return "videopage";
			}
			// upload
			if (resourceType.equals("#box-5")) {
				return "uploadpage";
			}
			return ERROR;
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// del resource
	public String delResourceAction() {
		try {
			if(log.isDebugEnabled()){
				log.debug("delResourceAction() called ..."); 
			}
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);

			// resource id
			String id = request.getParameter("id");
			// resource name
			String name = "";
			try {
				name = URLDecoder.decode(request.getParameter("name"),"UTF-8");
				if(log.isDebugEnabled()){
					log.debug("The resource name you want to delete is: " + name);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			// IMAGETYPE VIDEOTYPE AUDIOTYPE TEXTTYPE
			String type = request.getParameter("type");
			// delete content in xml file
			boolean flag2 = resourceService.deleteResource(id);
			boolean flag1 = FileOperationTool.del(name, type);
			if(log.isDebugEnabled()){
				log.debug("resourceService.deleteResource(id) : delete resource result is : " + (flag1 && flag2));
				log.debug("FileOperationTool.del(name, type) : delete resource result is : " + (flag1 && flag2));
			}
			if (type.equals("IMAGETYPE")) {
				String previewImagePath = ServletActionContext.getServletContext()
						.getRealPath("/images/preview/")
						+ "/" + name;
				boolean flag = FileOperationTool.del(previewImagePath);
				if (flag) {

				} else {
					return ERROR;
				}
			}
			if(log.isDebugEnabled()){
				log.debug("delete resource result is : " + (flag1 && flag2));
			}
			return flag1 && flag2 ? SUCCESS : ERROR;
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	public String delSelectedResource() {
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);

			// IMAGETYPE VIDEOTYPE AUDIOTYPE TEXTTYPE
			String type = request.getParameter("type");
			// resource ids
			String selectedIDs = request.getParameter("selectedIDs");
			// resource names
			String selectedNames = request.getParameter("selectedNames");
			String[] ids = selectedIDs.split(",");
			String[] names = selectedNames.split("\\|");

			int size = names.length;
			boolean flag = true;
			boolean flag2 = true;
			for (int i = 0; i < size; i++) {
				flag2 = flag2 && resourceService.deleteResource(ids[i]);
				flag = flag && FileOperationTool.del(names[i], type);
			}
			if (flag && flag2) {
				return SUCCESS;
			} else {
				return ERROR;
			}
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// redirect to manage_resource.jsp
	public String displayCategory() {
		return "manageResourcePage";
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public List<ResourceDTO> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<ResourceDTO> resourceList) {
		this.resourceList = resourceList;
	}

	public List<ResourceDTO> getVideoList() {
		return videoList;
	}

	public void setVideoList(List<ResourceDTO> videoList) {
		this.videoList = videoList;
	}

	public List<ResourceDTO> getImageList() {
		return imageList;
	}

	public void setImageList(List<ResourceDTO> imageList) {
		this.imageList = imageList;
	}

	public List<ResourceDTO> getAudioList() {
		return audioList;
	}

	public void setAudioList(List<ResourceDTO> audioList) {
		this.audioList = audioList;
	}

	public List<ResourceDTO> getTextList() {
		return textList;
	}

	public void setTextList(List<ResourceDTO> textList) {
		this.textList = textList;
	}

	public RegisterService getRegisterService() {
		return registerService;
	}

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}
}
