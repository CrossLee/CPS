package com.cemso.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cemso.dao.ProgramDao;
import com.cemso.dao.TemplateDao;
import com.cemso.dao.TemplatemodelDao;
import com.cemso.dao.impl.ActionlogDaoImpl;
import com.cemso.dto.ActionlogDTO;
import com.cemso.dto.ProgramDTO;
import com.cemso.dto.TemplateDTO;
import com.cemso.dto.TemplateModelDTO;
import com.cemso.service.TemplateService;
import com.cemso.util.ConstantUtil;
import com.cemso.util.SequenceUtil;

public class TemplateServiceImpl implements TemplateService {

	private TemplateDao templateDao;
	private TemplatemodelDao templatemodelDao;

	private ProgramDao programDao;

	public ProgramDao getProgramDao() {
		return programDao;
	}

	public void setProgramDao(ProgramDao programDao) {
		this.programDao = programDao;
	}

	private static final Log log = LogFactory
			.getLog(com.cemso.service.impl.TemplateServiceImpl.class);

	public TemplateDao getTemplateDao() {
		return templateDao;
	}

	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	public TemplatemodelDao getTemplatemodelDao() {
		return templatemodelDao;
	}

	public void setTemplatemodelDao(TemplatemodelDao templatemodelDao) {
		this.templatemodelDao = templatemodelDao;
	}

	public boolean addTemlate(TemplateDTO template) {
		int indexid = SequenceUtil.getInstance().getNextKeyValue(
				ConstantUtil.SequenceName.TEMPLATE_SEQUENCE);
		template.setIndexid(indexid);
		boolean flag1 = templateDao.addTemplate(template);
		List<TemplateModelDTO> models = template.getModels();

		boolean flag2 = true;
		for (TemplateModelDTO model : models) {
			flag2 = flag2 && templatemodelDao.addTemplateModel(model);
		}
		
		if(flag1 && flag2){
			Integer indexid1 = SequenceUtil.getInstance().getNextKeyValue(
					ConstantUtil.SequenceName.ACTIONLOG_SEQUENCE);
			String time = ActionlogDTO.currentTime();
			String user = ActionlogDTO.username;
			String action = "添加了模板（ID）: "+template.getId()+", 模板名称：" + template.getName();
			String sql = "";
			ActionlogDTO actionlog = new ActionlogDTO(indexid1,time,user,action,sql);
			ActionlogDaoImpl.addActionlog(actionlog);
		}
		
		return flag1 && flag2;
	}

	public boolean modifyTemlate(TemplateDTO template) {
		boolean flag = templateDao.updateTemplate(template);
		if(flag){
			Integer indexid1 = SequenceUtil.getInstance().getNextKeyValue(
					ConstantUtil.SequenceName.ACTIONLOG_SEQUENCE);
			String time = ActionlogDTO.currentTime();
			String user = ActionlogDTO.username;
			String action = "修改了模板（ID）: "+template.getId()+", 模板名称：" + template.getName();
			String sql = "";
			ActionlogDTO actionlog = new ActionlogDTO(indexid1,time,user,action,sql);
			ActionlogDaoImpl.addActionlog(actionlog);
		}
		return flag;
	}

	public boolean deleteTemlate(TemplateDTO template) {
		boolean flag = templateDao.deleteTemplate(template.getId());
		if(flag){
			Integer indexid1 = SequenceUtil.getInstance().getNextKeyValue(
					ConstantUtil.SequenceName.ACTIONLOG_SEQUENCE);
			String time = ActionlogDTO.currentTime();
			String user = ActionlogDTO.username;
			String action = "删除了模板（ID）: "+template.getId()+", 模板名称：" + template.getName();
			String sql = "";
			ActionlogDTO actionlog = new ActionlogDTO(indexid1,time,user,action,sql);
			ActionlogDaoImpl.addActionlog(actionlog);
		}
		return flag;
	}

	public boolean deleteTemlate(String id) {
		boolean flag = templateDao.deleteTemplate(id);
		if(flag){
			Integer indexid1 = SequenceUtil.getInstance().getNextKeyValue(
					ConstantUtil.SequenceName.ACTIONLOG_SEQUENCE);
			String time = ActionlogDTO.currentTime();
			String user = ActionlogDTO.username;
			String action = "删除了模板（ID）: "+id;
			String sql = "";
			ActionlogDTO actionlog = new ActionlogDTO(indexid1,time,user,action,sql);
			ActionlogDaoImpl.addActionlog(actionlog);
		}
		return flag;
	}

	public List<TemplateDTO> getTemlates() {

		boolean flag = updateTemplateStatus();
		// boolean flag = true;
		if (flag) {
			List<TemplateDTO> templateList = templateDao.queryTemplates();
			// get templatemodels
			List<TemplateModelDTO> allmodels = templatemodelDao
					.queryTemplateModels();

			for (TemplateDTO template : templateList) {
				List<TemplateModelDTO> templateModels = new ArrayList<TemplateModelDTO>();
				for (TemplateModelDTO model : allmodels) {
					if (template.getId().equals(model.getTemplateid())) {
						templateModels.add(model);
					}
				}
				template.setModels(templateModels);
				templateModels = null;
				if (log.isDebugEnabled()) {
					log.debug("template \"" + template.getName()
							+ "\"'s models size is :"
							+ template.getModels().size());
				}
			}

			return templateList;
		} else {
			return null;
		}

	}

	// update canBeDelete
	private boolean updateTemplateStatus() {
		if (log.isDebugEnabled()) {
			log.debug("start to update template's canBeDelete status...");
		}
		boolean flag = true;
		List<ProgramDTO> programs = programDao.queryPrograms();
		List<TemplateDTO> templateList = templateDao.queryTemplates();

		int i = 0;
		boolean restart = false;
		int programSize = programs.size();
		out: for (; i < templateList.size();) {
			String templateId = templateList.get(i).getId();
			int j = 0;
			for (; j < programSize; j++) {
				String tempid = programs.get(j).getTemplateId();
				if (tempid.equals(templateId)) {
					templateList.remove(templateList.get(i));
					restart = true;
					break;
				}
			}
			if (j == programSize) {
				restart = false;
			}
			if (restart) {
				i = 0;
				continue out;
			} else {
				i++;
			}
		}
		if (!templateList.isEmpty()) {
			for (TemplateDTO template : templateList) {
				flag &= templateDao.updateTemplate(template.getId(), "true");
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("update template's canBeDelete status: " + flag);
		}
		return flag;
	}
}
