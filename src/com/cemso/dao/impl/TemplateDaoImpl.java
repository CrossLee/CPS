/*
 * Created on 2012-7-15
 *
 * TemplateDaoImpl.java
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

import com.cemso.dao.TemplateDao;
import com.cemso.dto.TemplateDTO;
import com.cemso.util.FetchData;

/**
 * @author CrossLee
 *
 */
public class TemplateDaoImpl implements TemplateDao {

	private static final Log log = LogFactory.getLog(com.cemso.dao.impl.TemplateDaoImpl.class);
	
	@Override
	public boolean addTemplate(TemplateDTO template) {
		String sql = generateSql(template, FetchData.SQL_INSERT);
		int count = FetchData.updateData(sql);
		return (count == 1);
	}

	@Override
	public boolean deleteTemplate(int indexid) {
		String sql = "delete from template where indexid = " + indexid+";";
		int count = FetchData.updateData(sql);
		return(count == 1);
	}

	@Override
	public boolean deleteTemplate(String id) {
		String sql = "delete from template where id = '" + id+"';";
		int count = FetchData.updateData(sql);
		return(count == 1);
	}

	@Override
	public TemplateDTO queryResource(int indexid) {
		TemplateDTO template = null;
		String sql = "select * from template where indexid = " + indexid +";";
		if(log.isDebugEnabled()){
			log.debug("sql : " +sql);
		}
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.TemplateDTO");
		if(!list.isEmpty()){
			template = (TemplateDTO) list.get(0);
		}
		return template;
	}

	@Override
	public List<TemplateDTO> queryTemplates() {
		String sql = "select * from template order by indexid desc;";
		if(log.isDebugEnabled()){
			log.debug("sql : " +sql);
		}
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.TemplateDTO");
		List<TemplateDTO> templatelist = new ArrayList<TemplateDTO>();
		for (Object obj : list) {
			templatelist.add((TemplateDTO)obj);
		}
		
		return templatelist;
	}

	@Override
	public boolean updateTemplate(TemplateDTO template) {
		String sql = generateSql(template, FetchData.SQL_UPDATE);
		int count = FetchData.updateData(sql);
		return (count == 1);
	}
	
	@Override
	public boolean updateTemplate(String templateId, String flag) {
		String sql = "update template set canBeDelete = '"+flag+"' where id = '"+templateId+"';";
		int count = FetchData.updateData(sql);
		return (count == 1);
	}
	
	private String generateSql(TemplateDTO template, String type){
		String sql = null;
		int indexid = template.getIndexid();
		String id = template.getId();
		String name = template.getName();
		String remark = template.getRemark();
		String size = template.getSize();
		String createTime = template.getCreateTime();
		String createBy = template.getCreateBy();
		String canBeDelete = String.valueOf(template.isCanBeDelete());

		if (type.equals("INSERT")) {
			sql = "insert into template(indexid,id,name,remark,size,createTime,createBy,canBeDelete) values("
					+ indexid
					+ ",'"
					+ id
					+ "','"
					+ name
					+ "','"
					+ remark 
					+ "','"
					+ size
					+ "','" 
					+ createTime 
					+ "','"
					+ createBy
					+ "','"
					+ canBeDelete
					+ "');";
		}
		if (type.equals("UPDATE")) {
			sql = "update template set id='"+id+"',name='"+name+"',remark='"+remark+"',size='"+size+"',createTime='"+createTime+"',createBy='"+createBy+"',canBeDelete='"+canBeDelete+"' where indexid = "+indexid+";";
		}
		if (type.equals("DELETE")) {
			sql = "delete from template where indexid = " + indexid + ";";
		}
		if(log.isDebugEnabled()){
			log.debug("TemplateDaoImpl.generateSql() call -> sql : " + sql);
		}
		return sql;
	}
}
