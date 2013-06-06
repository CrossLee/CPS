package com.cemso.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.cemso.dto.OnebyoneDTO;
import com.cemso.dto.ProgramDTO;
import com.cemso.dto.ResourceDTO;
import com.cemso.dto.TemplateDTO;
import com.cemso.dto.TemplateModelDTO;
import com.cemso.dto.UserDTO;
import com.cemso.service.ProgramService;
import com.cemso.service.ResourceService;
import com.cemso.service.TemplateModelService;
import com.cemso.service.TemplateService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ProgramAction extends ActionSupport {

	private final Log log = LogFactory
			.getLog(com.cemso.action.LoginAction.class);

	private static final long serialVersionUID = 1L;

	private ProgramService programService;
	private TemplateService templateService;
	private ResourceService resourceService;
	private TemplateModelService templateModelService;

	private List<ProgramDTO> programList;
	private List<TemplateDTO> templateList;
	private List<ResourceDTO> resourceList;

	private List<ResourceDTO> videoList;
	private List<ResourceDTO> imageList;
	private List<ResourceDTO> audioList;
	private List<ResourceDTO> textList;

	public TemplateModelService getTemplateModelService() {
		return templateModelService;
	}

	public void setTemplateModelService(
			TemplateModelService templateModelService) {
		this.templateModelService = templateModelService;
	}

	// 编辑program返回的对象
	private ProgramDTO programDTO;

	public String getProgram() {
		try {
			programList = programService.getProgramList();
			templateList = templateService.getTemlates();
			resourceList = resourceService.getResourceList();

			imageList = new ArrayList<ResourceDTO>();
			videoList = new ArrayList<ResourceDTO>();
			audioList = new ArrayList<ResourceDTO>();
			textList = new ArrayList<ResourceDTO>();

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
			return "programpage";
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// add program
	public String addProgram() {
		try {
			ProgramDTO program = new ProgramDTO();
			List<OnebyoneDTO> onebyoneList = new ArrayList<OnebyoneDTO>();

			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);
			Map<String, Object> session = context.getSession();

			SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			// set program attribute
			String createtime = formate.format(System.currentTimeMillis());
			String createby = ((UserDTO) session.get("user")).getId();
			String length = request.getParameter("length");
			String programName = null;
			String programRemark = null;
			String relation = null;
			
			// cycle parameters
			String radioValue = request.getParameter("radioValue");
			program.setRadioValue(radioValue);
			if(radioValue != null && radioValue != "0"){
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				String startHour  = request.getParameter("startHour");
				String startMinute  = request.getParameter("startMinute");
				String startSecond  = request.getParameter("startSecond");
				String endHour  = request.getParameter("endHour");
				String endMinute  = request.getParameter("endMinute");
				String endSecond  = request.getParameter("endSecond");

				String xingqi1   = request.getParameter("xingqi1");
				String xingqi2   = request.getParameter("xingqi2");
				String xingqi3   = request.getParameter("xingqi3");
				String xingqi4   = request.getParameter("xingqi4");
				String xingqi5   = request.getParameter("xingqi5");
				String xingqi6   = request.getParameter("xingqi6");
				String xingqi7   = request.getParameter("xingqi7");
				
				// check the radio button value
				if(radioValue.equals("1") || radioValue.equals("2")){
					program.setStartDate(startDate);
					program.setEndDate(endDate);
					program.setStartHour(startHour);
					program.setStartMinute(startMinute);
					program.setStartSecond(startSecond);
					program.setEndHour(endHour);
					program.setEndMinute(endMinute);
					program.setEndSecond(endSecond);
					
					program.setXingqi1(xingqi1);
					program.setXingqi2(xingqi2);
					program.setXingqi3(xingqi3);
					program.setXingqi4(xingqi4);
					program.setXingqi5(xingqi5);
					program.setXingqi6(xingqi6);
					program.setXingqi7(xingqi7);
				}
			}
			
			try {
				programName = URLDecoder.decode(
						request.getParameter("programName"), "UTF-8");
				programRemark = URLDecoder.decode(request
						.getParameter("programRemark"), "UTF-8");
				relation = URLDecoder.decode(request.getParameter("relation"),
						"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				if (log.isErrorEnabled())
					log.error(e.getMessage());
				e.printStackTrace();
			}

			String[] onebyones = relation.split("\\|"); // "|"
			// get template id
			String templateId = "template_" + relation.split("_")[0];

			program.setId("program_" + System.currentTimeMillis());
			program.setName(programName);
			program.setRemark(programRemark);
			program.setCreateby(createby);
			program.setCreatetime(createtime);
			program.setTemplateId(templateId);
			program.setLength(length);
			program.setCanBeDelete(true);
			
			for (int i = 0; i < onebyones.length; i++) {
				String[] onebyone = onebyones[i].split(":");
				OnebyoneDTO onebyoneDTO = new OnebyoneDTO();

				onebyoneDTO.setProgramid(program.getId());

				onebyoneDTO.setDimension(onebyone[1]);
				onebyoneDTO.setGradation(onebyone[2]);
				onebyoneDTO.setResource(onebyone[3]);
				onebyoneDTO.setResourceName(onebyone[4]);
				// set font params
				if (onebyoneDTO.getResource().contains("text")) {
					TemplateModelDTO model = templateModelService
							.queryTemplateModel(onebyoneDTO.getResource());
					String direction = model.getDirection();
					onebyoneDTO.setDirection(direction);
					
					// get font parameters
					String fontColor = model.getFontColor();
					String fontName = model.getFontName();
					String fontSize = model.getFontSize();
					String fontBolder = model.getFontBolder();
					String fontItalic = model.getFontItalic();
					String fontUnder = model.getFontUnder();
					String fontNextLine = model.getFontNextLine();
					String fontHAlign = model.getFontHAlign();
					String fontVAlign = model.getFontVAlign();
					String speed = model.getSpeed();
					String delay = model.getDelay();
					
					// set font parameters
					onebyoneDTO.setFontColor(fontColor);
					onebyoneDTO.setFontName(fontName);
					onebyoneDTO.setFontSize(fontSize);
					onebyoneDTO.setFontBolder(fontBolder);
					onebyoneDTO.setFontItalic(fontItalic);
					onebyoneDTO.setFontUnder(fontUnder);
					onebyoneDTO.setFontNextLine(fontNextLine);
					onebyoneDTO.setFontHAlign(fontHAlign);
					onebyoneDTO.setFontVAlign(fontVAlign);
					onebyoneDTO.setSpeed(speed);
					onebyoneDTO.setDelay(delay);
				}
				
				// set clock params
				if (onebyoneDTO.getResource().contains("clock")) {
					TemplateModelDTO model = templateModelService
					.queryTemplateModel(onebyoneDTO.getResource());
					
					// get font parameters
					String fontColor = model.getFontColor();
					String fontName = model.getFontName();
					String fontSize = model.getFontSize();
					String fontBolder = model.getFontBolder();
					String fontItalic = model.getFontItalic();
					String fontUnder = model.getFontUnder();
					String mode = model.getMode();
					String format = model.getFormat();
					
					// set font parameters
					onebyoneDTO.setFontColor(fontColor);
					onebyoneDTO.setFontName(fontName);
					onebyoneDTO.setFontSize(fontSize);
					onebyoneDTO.setFontBolder(fontBolder);
					onebyoneDTO.setFontItalic(fontItalic);
					onebyoneDTO.setFontUnder(fontUnder);
					onebyoneDTO.setMode(mode);
					onebyoneDTO.setFormat(format);
					
				}

				onebyoneList.add(onebyoneDTO);
			}

			program.setOnebyoneList(onebyoneList);
			return programService.addProgram(program) ? SUCCESS : ERROR;
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}

	}

	// delete program
	public String delProgram() {
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);

			// program id
			String id = request.getParameter("id");
			boolean flag = programService.deleteProgram(id);

			return flag ? SUCCESS : ERROR;
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// delete selected program
	public String delSelectedProgram() {
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);

			// program id
			String ids = request.getParameter("ids");
			String[] id = ids.split(",");
			int size = id.length;
			boolean flag = true;
			for (int i = 0; i < size; i++) {
				flag &= programService.deleteProgram(id[i]);
			}

			return flag ? SUCCESS : ERROR;
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// 编辑更新program
	public String editProgram() {
		try {
			ProgramDTO program = new ProgramDTO();
			
			List<OnebyoneDTO> onebyoneList = new ArrayList<OnebyoneDTO>();

			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);
			Map<String, Object> session = context.getSession();

			SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			// set program attribute
			String createtime = formate.format(System.currentTimeMillis());
			String createby = ((UserDTO) session.get("user")).getId();
			String programId = request.getParameter("programId");
			String programName = request.getParameter("programName");
			String programRemark = request.getParameter("programRemark");
			String length = request.getParameter("length");

			String relation = null;
			List<ProgramDTO> programs =  programService.getProgramList();
			for(ProgramDTO programdto : programs){
				if(programdto.getId().equals(programId)){
					program = programdto;
				}
			}
			
			try {
				relation = URLDecoder.decode(request.getParameter("relation"),
						"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				if (log.isErrorEnabled())
					log.error(e.getMessage());
				e.printStackTrace();
			}

			String[] onebyones = relation.split("\\|"); // "|"

			// get template id
			String templateId = "template_" + relation.split("_")[0];

			program.setId(programId);
			program.setName(programName);
			program.setRemark(programRemark);
			program.setCreateby(createby);
			program.setCreatetime(createtime);
			program.setTemplateId(templateId);
			program.setLength(length);

			for (int i = 0; i < onebyones.length; i++) {
				String[] onebyone = onebyones[i].split(":");
				OnebyoneDTO onebyoneDTO = new OnebyoneDTO();
				onebyoneDTO.setDimension(onebyone[1]);
				onebyoneDTO.setGradation(onebyone[2]);
				onebyoneDTO.setResource(onebyone[3]);
				onebyoneDTO.setResourceName(onebyone[4]);
				onebyoneList.add(onebyoneDTO);
			}

			program.setOnebyoneList(onebyoneList);

			return programService.updateProgram(program) ? SUCCESS : ERROR;
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// 获取待编辑的program对象
	public String editGetProgram() {
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);
			String id = request.getParameter("id");
			programList = programService.getProgramList();
			for (ProgramDTO program : programList) {
				if (program.getId().equals(id)) {
					programDTO = program;
				}
			}

			return "programObject";
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// 获取待预览的program对象
	public String previewGetProgram() {
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);
			String id = request.getParameter("id");
			programList = programService.getProgramList();
			for (ProgramDTO program : programList) {
				if (program.getId().equals(id)) {
					programDTO = program;
				}
			}
			// get template size
			List<TemplateDTO> tempTemplateList = templateService.getTemlates();
			for (TemplateDTO template : tempTemplateList) {
				if (template.getId().equals(programDTO.getTemplateId())) {
					String size = template.getSize();
					if (log.isDebugEnabled()) {
						log.debug("the size of this program for preview is : "
								+ size);
					}
					if ("4_3".equals(size)) {
						programDTO.setTemplateWidth("800");
						programDTO.setTemplateHeight("600");
					}
					if ("3_4".equals(size)) {
						programDTO.setTemplateWidth("600");
						programDTO.setTemplateHeight("800");
					}
					if ("5_4".equals(size)) {
						programDTO.setTemplateWidth("800");
						programDTO.setTemplateHeight("640");
					}
					if ("1280_1024".equals(size)) {
						programDTO.setTemplateWidth("800");
						programDTO.setTemplateHeight("640");
					}
					if ("4_5".equals(size)) {
						programDTO.setTemplateWidth("640");
						programDTO.setTemplateHeight("800");
					}
					if ("16_9".equals(size)) {
						programDTO.setTemplateWidth("800");
						programDTO.setTemplateHeight("450");
					}
					if ("9_16".equals(size)) {
						programDTO.setTemplateWidth("450");
						programDTO.setTemplateHeight("800");
					}
				}
			}

			return "programObject";
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	public ProgramService getProgramService() {
		return programService;
	}

	public void setProgramService(ProgramService programService) {
		this.programService = programService;
	}

	public TemplateService getTemplateService() {
		return templateService;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public List<TemplateDTO> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(List<TemplateDTO> templateList) {
		this.templateList = templateList;
	}

	public List<ResourceDTO> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<ResourceDTO> resourceList) {
		this.resourceList = resourceList;
	}

	public List<ProgramDTO> getProgramList() {
		return programList;
	}

	public void setProgramList(List<ProgramDTO> programList) {
		this.programList = programList;
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

	public ProgramDTO getProgramDTO() {
		return programDTO;
	}

	public void setProgramDTO(ProgramDTO programDTO) {
		this.programDTO = programDTO;
	}

}
