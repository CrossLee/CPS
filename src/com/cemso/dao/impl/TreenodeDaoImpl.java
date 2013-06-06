/*
 * Created on 2012-7-15
 *
 * TreenodeDaoImpl.java
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

import com.cemso.dao.TreenodeDao;
import com.cemso.dto.TreeNodeDTO;
import com.cemso.util.FetchData;

/**
 * @author CrossLee
 *
 */
public class TreenodeDaoImpl implements TreenodeDao {

	private static final Log log = LogFactory.getLog(com.cemso.dao.impl.TreenodeDaoImpl.class);
	
	@Override
	public boolean addTreeNode(TreeNodeDTO treeNode) {
		String sql = generateSql(treeNode, FetchData.SQL_INSERT);
		int count = FetchData.updateData(sql);
		return (count == 1);
	}

	@Override
	public boolean deleteTreeNode(int indexid) {
		String sql = "delete from treenode where indexid = " + indexid +";";
		int count = FetchData.updateData(sql);
		return (count == 1);
	}

	@Override
	public TreeNodeDTO queryTreeNode(int indexid) {
		TreeNodeDTO treenode = null;
		String sql = "select * from treenode where indexid = " + indexid +";";
		if(log.isDebugEnabled()){
			log.debug("sql : " + sql);
		}
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.TreeNodeDTO");
		if(!list.isEmpty()){
			treenode = (TreeNodeDTO)list.get(0);
		}
		return treenode;
	}

	@Override
	public List<TreeNodeDTO> queryTreeNodes() {
		String sql = "select * from treenode;";
		if(log.isDebugEnabled()){
			log.debug("sql : " + sql);
		}
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.TreeNodeDTO");
		List<TreeNodeDTO> treenodelist = new ArrayList<TreeNodeDTO>();
		for(Object o : list){
			treenodelist.add((TreeNodeDTO)o);
		}
		return treenodelist;
	}

	@Override
	public boolean updateTreeNode(TreeNodeDTO treeNode) {
		String sql = generateSql(treeNode, FetchData.SQL_UPDATE);
		int count = FetchData.updateData(sql);
		return (count == 1);
	}
	
	private static String generateSql(TreeNodeDTO treenode, String methodType){
		String sql = null;
		int indexid = treenode.getIndexid();
		String parentId = treenode.getParentId();
		String id = treenode.getId();
		String text = treenode.getText();
		String action = treenode.getAction();
		String expanded = treenode.getExpanded();

		if (methodType.equals("INSERT")) {
			sql = "insert into treenode(indexid,parentId,id,text,action,expanded) values("
					+ indexid
					+ ",'"
					+ parentId
					+ "','"
					+ id
					+ "','"
					+ text 
					+ "','"
					+ action
					+ "','" 
					+ expanded 
					+ "');";
		}
		if (methodType.equals("UPDATE")) {
			sql = "update treenode set parentId='"+parentId+"',id='"+id+"',text='"+text+"',action='"+action+"',expanded='"+expanded+"' where indexid = "+indexid+";";
		}
		if (methodType.equals("DELETE")) {
			sql = "delete from treenode where indexid = " + indexid + ";";
		}
		if(log.isDebugEnabled()){
			log.debug("TreenodeDaoImpl.generateSql() call -> sql : " + sql);
		}
		return sql;
	}
}
