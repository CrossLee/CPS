package com.cemso.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.cemso.dto.TemplateDTO;
import com.cemso.dto.TemplateModelDTO;
import com.cemso.dto.UserDTO;
import com.cemso.service.TemplateService;
import com.cemso.util.ConstantUtil;
import com.cemso.util.SequenceUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class TemplateAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private TemplateService templateService;
	private List<TemplateDTO> templateList;

	private static final Log log = LogFactory.getLog(TemplateAction.class);

	public String addTemplate() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("addTemplate() called ... ");
			}
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);

			String model = null;
			String templateName = null;
			String tempalteRemark = null;
			try {
				model = URLDecoder.decode(request.getParameter("model"),"utf-8");
				templateName = URLDecoder.decode(request.getParameter("templateName"),"UTF-8");
				tempalteRemark = URLDecoder.decode(request.getParameter("tempalteRemark"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				if(log.isErrorEnabled()){
					log.error(e.getMessage());
				}
				e.printStackTrace();
			}
			String templateSize = request.getParameter("templateSize");

			TemplateDTO template = parseJsonObj(templateName, tempalteRemark,
					templateSize, model);

			return templateService.addTemlate(template) ? SUCCESS : ERROR;
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	public String getTemplates() {
		try {
			templateList = templateService.getTemlates();
			return "templatepage";
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// delete template
	public String delTemplateAction() {
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);

			// template id
			String id = request.getParameter("id");
			return templateService.deleteTemlate(id) ? SUCCESS : ERROR;
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// delete selected template
	public String delSelectedTemplate() {
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);

			// template ids
			String ids = request.getParameter("ids");
			String[] id = ids.split(",");
			int size = id.length;
			boolean flag = true;
			for (int i = 0; i < size; i++) {
				flag = flag && templateService.deleteTemlate(id[i]);
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

	public String templateDesign() {
		return "templateDesignPage";
	}

	private TemplateDTO parseJsonObj(String temmlateNameValue,
			String tempalteRemark, String templateSize, String modelValue) {
		try {
			TemplateDTO template = new TemplateDTO();
			List<TemplateModelDTO> modelList = new ArrayList<TemplateModelDTO>();

			// modelValue:video1_1_0_0_279_150,image2_2_426_0_596_127,audio3_3_0_254_254_394,
			String[] modelArray = modelValue.split(",");
			int modelSize = modelArray.length;
			Date date = Calendar.getInstance().getTime();
			for (int i = 0; i < modelSize; i++) {
				int indexid = SequenceUtil.getInstance().getNextKeyValue(ConstantUtil.SequenceName.TEMPLATEMODEL_SEQUENCE);
				TemplateModelDTO model = new TemplateModelDTO();
				String modelString = modelArray[i];
				String[] modelDetails = modelString.split("_");

				model.setIndexid(indexid);
				model.setTemplateid("template_" + date.getTime());
				model.setName(modelDetails[0]);

				if (modelDetails[0].startsWith("video")) {
					model.setType("video");
				}
				if (modelDetails[0].startsWith("image")) {
					model.setType("image");
				}
				if (modelDetails[0].startsWith("text")) {
					model.setType("text");
					String fontParams = modelDetails[6];
					String[] params = fontParams.split("\\|");
					
					// params
					String fontColor = params[0];
					String fontName = params[1];
					String fontSize = params[2];
					String fontBolder = params[3];
					String fontItalic = params[4];
					String fontUnder = params[5];
					String fontNextLine = params[6];
					String fontHAlign = params[7];
					String fontVAlign = params[8];
					String direction = params[9];
					String speed = params[10];
					String delay = params[11];
					
					model.setFontColor(fontColor);
					model.setFontName(fontName);
					model.setFontSize(fontSize);
					model.setFontBolder(fontBolder);
					model.setFontItalic(fontItalic);
					model.setFontUnder(fontUnder);
					model.setDirection(direction);
					model.setFontNextLine(fontNextLine);
					model.setFontHAlign(fontHAlign);
					model.setFontVAlign(fontVAlign);
					model.setSpeed(speed);
					model.setDelay(delay);
					
				}
				if (modelDetails[0].startsWith("audio")) {
					model.setType("audio");
				}
				if (modelDetails[0].startsWith("weather")) {
					model.setType("weather");
				}
				if (modelDetails[0].startsWith("clock")) {
					model.setType("clock");
					
					String fontParams = modelDetails[6];
					String[] params = fontParams.split("\\|");
					
					// params
					String fontColor = params[0];
					String fontName = params[1];
					String fontSize = params[2];
					String fontBolder = params[3];
					String fontItalic = params[4];
					String fontUnder = params[5];
					String mode = params[6];
					String format = params[7];
					
					model.setFontColor(fontColor);
					model.setFontName(fontName);
					model.setFontSize(fontSize);
					model.setFontBolder(fontBolder);
					model.setFontItalic(fontItalic);
					model.setFontUnder(fontUnder);
					model.setMode(mode);
					model.setFormat(format);
				}

				model.setId(String.valueOf(date.getTime()) + "_" + model.getName());
				model.setGradation(modelDetails[1]);
				model.setDimension(modelDetails[2] + "," + modelDetails[3] + ","
						+ modelDetails[4] + "," + modelDetails[5]);
				modelList.add(model);
			}

			// Date date = Calendar.getInstance().getTime();
			template.setName(temmlateNameValue);

			Map<String, Object> session = ActionContext.getContext().getSession();
			template.setCreateBy(((UserDTO) session.get("user")).getId());
			template.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(date));
			template.setId("template_" + date.getTime());
			template.setRemark(tempalteRemark);
			template.setSize(templateSize);
			template.setModels(modelList);
			template.setCanBeDelete(true);

			return template;
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return null;
		}
	}

	public TemplateService getTemplateService() {
		return templateService;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public List<TemplateDTO> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(List<TemplateDTO> templateList) {
		this.templateList = templateList;
	}
}
