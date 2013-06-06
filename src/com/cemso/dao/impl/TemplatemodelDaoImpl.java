/*
 * Created on 2012-7-15
 *
 * TemplatemodelDaoImpl.java
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
 * 2012-7-15       CrossLee                    1.0         New
 * ---------------------------------------------------------------------------------
 */
/**
 * 
 */
package com.cemso.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cemso.dao.TemplatemodelDao;
import com.cemso.dto.TemplateModelDTO;
import com.cemso.util.FetchData;

/**
 * @author CrossLee
 *
 */
public class TemplatemodelDaoImpl implements TemplatemodelDao {

	private static final Log log = LogFactory.getLog(com.cemso.dao.impl.TemplatemodelDaoImpl.class);
	
	@Override
	public boolean addTemplateModel(TemplateModelDTO templatemodelDTO) {
		String sql = generateSql(templatemodelDTO, FetchData.SQL_INSERT);
		int count = FetchData.updateData(sql);
		return (count == 1);
	}

	@Override
	public boolean deleteTemplateModel(int indexid) {
		String sql = "delete from templatemodel where indexid = " + indexid + ";";
		int count = FetchData.updateData(sql);
		return (count == 1);
	}

	@Override
	public TemplateModelDTO queryTemplateModel(int indexid) {
		TemplateModelDTO templatemodel = null;
		String sql = "select * from templatemodel where indexid = " + indexid +";";
		if(log.isDebugEnabled()){
			log.debug("sql : " + sql);
		}
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.TemplateModelDTO");
		if(!list.isEmpty()){
			templatemodel = (TemplateModelDTO)list.get(0);
		}
		return templatemodel;
	}
	
	@Override
	public TemplateModelDTO queryTemplateModel(String id) {
		TemplateModelDTO templatemodel = null;
		String sql = "select * from templatemodel where id = '" + id +"';";
		if(log.isDebugEnabled()){
			log.debug("sql : " + sql);
		}
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.TemplateModelDTO");
		if(!list.isEmpty()){
			templatemodel = (TemplateModelDTO)list.get(0);
		}
		return templatemodel;
	}

	@Override
	public List<TemplateModelDTO> queryTemplateModels() {
		String sql = "select * from templatemodel order by indexid desc;";
		if(log.isDebugEnabled()){
			log.debug("sql : " + sql);
		}
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.TemplateModelDTO");
		List<TemplateModelDTO> templatemodelList = new ArrayList<TemplateModelDTO>();
		for(Object o : list){
			templatemodelList.add((TemplateModelDTO)o);
		}
		return templatemodelList;
	}

	@Override
	public boolean updateTemplateModel(TemplateModelDTO template) {
		String sql = generateSql(template, FetchData.SQL_UPDATE);
		int count = FetchData.updateData(sql);
		return (count == 1);
	}

	private static String generateSql(TemplateModelDTO templatemodelDTO, String methodType){
		String sql = null;
		int indexid = templatemodelDTO.getIndexid();
		String templateid = templatemodelDTO.getTemplateid();
		String id = templatemodelDTO.getId();
		String name = templatemodelDTO.getName();
		String type = templatemodelDTO.getType();
		String dimension = templatemodelDTO.getDimension();
		String gradation = templatemodelDTO.getGradation();
		String direction = templatemodelDTO.getDirection();
		
		String fontColor = templatemodelDTO.getFontColor();
		String fontName = templatemodelDTO.getFontName();
		String fontSize = templatemodelDTO.getFontSize();
		String fontBolder = templatemodelDTO.getFontBolder();
		String fontItalic = templatemodelDTO.getFontItalic();
		String fontUnder = templatemodelDTO.getFontUnder();
		String fontNextLine = templatemodelDTO.getFontNextLine();
		String fontHAlign = templatemodelDTO.getFontHAlign();
		String fontVAlign = templatemodelDTO.getFontVAlign();
		String speed = templatemodelDTO.getSpeed();
		String delay = templatemodelDTO.getDelay();
		
		// for clock
		String mode = templatemodelDTO.getMode();
		String format = templatemodelDTO.getFormat();

		if (methodType.equals("INSERT")) {
			sql = "insert into templatemodel(indexid,templateid,id,name,type,dimension,gradation,direction,fontColor,fontName,fontSize,fontBolder,fontItalic,fontUnder,fontNextLine,fontHAlign,fontVAlign,speed,delay,mode,format) values("
					+ indexid
					+ ",'"
					+ templateid
					+ "','"
					+ id
					+ "','"
					+ name
					+ "','"
					+ type 
					+ "','"
					+ dimension
					+ "','" 
					+ gradation 
					+ "','"
					+ direction 
					+ "','"
					+ fontColor 
					+ "','"
					+ fontName 
					+ "','"
					+ fontSize 
					+ "','"
					+ fontBolder 
					+ "','"
					+ fontItalic 
					+ "','"
					+ fontUnder 
					+ "','"
					+ fontNextLine 
					+ "','"
					+ fontHAlign 
					+ "','"
					+ fontVAlign 
					+ "','"
					+ speed 
					+ "','"
					+ delay
					+ "','"
					+ mode
					+ "','"
					+ format
					+ "');";
		}
		if (methodType.equals("UPDATE")) {
			sql = "update templatemodel set id='"+id+"',name='"+name+"',type='"+type+"',dimension='"+dimension+"',gradation='"+gradation+"',direction='"+direction+"' wherer indexid = "+indexid+";";
		}
		if (methodType.equals("DELETE")) {
			sql = "delete from templatemodel where indexid = " + indexid + ";";
		}
		if(log.isDebugEnabled()){
			log.debug("TemplatemodelDaoImpl.generateSql() call -> sql : " + sql);
		}
		return sql;
	}
}
