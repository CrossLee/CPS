/*
 * Created on 2012-7-25
 *
 * TemplateModelServiceImpl.java
 *
 * Copyright (C) 2012 by Withiter Software & Technology Services (Shanghai) Limited.
 * All rights reserved. Withiter Software & Technology Services (Shanghai) Limited 
 * claims copyright in this computer program as an unpublished work. Claim of copyright 
 * does not imply waiver of other rights.
 *
 * NOTICE OF PROPRIETARY RIGHTS
 *
 * This program is a confidential trade secret and the property of 
 * Withiter Software & Technology Services (Shanghai) Limited. Use, examination, 
 * reproduction, disassembly, decompiling, transfer and/or disclosure to others of 
 * all or any part of this software program are strictly prohibited except by express 
 * written agreement with Withiter Software & Technology Services (Shanghai) Limited.
 */
/*
 * ---------------------------------------------------------------------------------
 * Modification History
 * Date               Author                     Version     Description
 * 2012-7-25       CrossLee                    1.0         New
 * ---------------------------------------------------------------------------------
 */
/**
 * 
 */
package com.cemso.service.impl;

import java.util.List;

import com.cemso.dao.TemplatemodelDao;
import com.cemso.dto.TemplateModelDTO;
import com.cemso.service.TemplateModelService;

/**
 * @author CrossLee
 *
 */
public class TemplateModelServiceImpl implements TemplateModelService {
	
	private TemplatemodelDao templatemodelDao;

	public TemplatemodelDao getTemplatemodelDao() {
		return templatemodelDao;
	}

	public void setTemplatemodelDao(TemplatemodelDao templatemodelDao) {
		this.templatemodelDao = templatemodelDao;
	}

	@Override
	public boolean addTemplateModel(TemplateModelDTO templateModel) {
		return false;
	}

	@Override
	public boolean deleteTemplateModel(TemplateModelDTO templateModel) {
		return false;
	}

	@Override
	public boolean deleteTemplateModel(String id) {
		return false;
	}

	@Override
	public boolean modifyTemplateModel(TemplateModelDTO templateModel) {
		return false;
	}

	@Override
	public TemplateModelDTO queryTemplateModel(String id) {
		return templatemodelDao.queryTemplateModel(id);
	}

	@Override
	public List<TemplateModelDTO> queryTemplateModels() {
		return null;
	}
}
