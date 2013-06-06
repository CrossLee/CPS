/*
 * Created on 2012-7-15
 *
 * ResourceDaoImpl.java
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

import com.cemso.dao.ResourceDao;
import com.cemso.dto.ResourceDTO;
import com.cemso.util.FetchData;

/**
 * @author CrossLee
 *
 */
public class ResourceDaoImpl implements ResourceDao {

	private static final Log log = LogFactory.getLog(com.cemso.dao.impl.ResourceDaoImpl.class);
	
	@Override
	public boolean addResource(ResourceDTO resource) {
		String sql = generateSql(resource,FetchData.SQL_INSERT);
		int count = FetchData.updateData(sql);
		return (count == 1);
	}

	@Override
	public boolean deleteResource(int indexid) {
		String sql = "delete from resource where indexid = "+indexid+";";
		int count = FetchData.updateData(sql);
		return (count == 1);
	}
	
	@Override
	public boolean deleteResource(String id) {
		String sql = "delete from resource where id = '"+id+"';";
		int count = FetchData.updateData(sql);
		return (count == 1);
	}

	@Override
	public ResourceDTO queryResource(int indexid) {
		ResourceDTO resource = null;
		String sql = "select * from resource where indexid = "+indexid+";";
		if(log.isDebugEnabled()){
			log.debug(sql);
		}
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.ResourceDTO");
		if(!list.isEmpty()){
			resource = (ResourceDTO) list.get(0);
		}
		return resource;
	}

	@Override
	public List<ResourceDTO> queryResources() {
		String sql = "select * from resource order by indexid desc;";
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.ResourceDTO");
		List<ResourceDTO> resourceList = new ArrayList<ResourceDTO>();
		for (int i = 0; i < list.size(); i++) {
			resourceList.add((ResourceDTO) list.get(i));
		}
		return resourceList;
	}

	@Override
	public boolean updateResource(ResourceDTO resource) {
		String sql = generateSql(resource,FetchData.SQL_UPDATE);
		int count = FetchData.updateData(sql);
		return (count == 1);
	}
	
	@Override
	public boolean updateResource(String resourceName,String flag) {
		String sql = "update resource set canBeDelete = '" + flag + "' where resourceName = '"+resourceName+"';";
		int count = FetchData.updateData(sql);
		return (count == 1);
	}
	
	private String generateSql(ResourceDTO resource, String type){
		String sql = null;
		int indexid = resource.getIndexid();
		String id = resource.getId();
		String resourceName = resource.getResourceName();
		String resourceType = resource.getResourceType();
		String paramRemark = resource.getParamRemark();
		String paramCreatetime = resource.getParamCreatetime();
		String paramCreateby = resource.getParamCreateby();
		String canBeDelete = String.valueOf(resource.isCanBeDelete());

		if (type.equals("INSERT")) {
			sql = "insert into resource(indexid,id,resourceName,resourceType,paramRemark,paramCreatetime,paramCreateby,canBeDelete) values("
					+ indexid
					+ ",'"
					+ id
					+ "','"
					+ resourceName
					+ "','"
					+ resourceType 
					+ "','" 
					+ paramRemark 
					+ "','"
					+ paramCreatetime
					+ "','"
					+ paramCreateby
					+ "','"
					+ canBeDelete
					+ "');";
		}
		if (type.equals("UPDATE")) {
			sql = "update resource set paramRemark = '" + paramRemark
					+ "' , canBeDelete = '" + canBeDelete +"';";
		}
		if (type.equals("DELETE")) {
			sql = "delete from resource where indexid = " + indexid + ";";
		}
		if(log.isDebugEnabled()){
			log.debug("ResourceDaoImpl.generateSql() call -> sql : " + sql);
		}
		return sql;
	}

	@Override
	public ResourceDTO queryResource(String resourceName) {
		ResourceDTO resource = null;
		String sql = "select * from resource where resourceName = '"+resourceName+"';";
		if(log.isDebugEnabled()){
			log.debug(sql);
		}
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.ResourceDTO");
		if(!list.isEmpty()){
			resource = (ResourceDTO) list.get(0);
		}
		return resource;
	}

}