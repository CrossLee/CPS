/*
 * Created on 2012-7-15
 *
 * ProgramDaoImpl.java
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

import com.cemso.dao.ProgramDao;
import com.cemso.dto.ProgramDTO;
import com.cemso.util.FetchData;

/**
 * @author CrossLee
 *
 */
public class ProgramDaoImpl implements ProgramDao {
	
	private static final Log log = LogFactory.getLog(com.cemso.dao.impl.ProgramDaoImpl.class);

	@Override
	public boolean addProgram(ProgramDTO program) {
		String sql = generateSql(program, FetchData.SQL_INSERT);
		int count = FetchData.updateData(sql);
		return (count == 1);
	}

	@Override
	public boolean deleteProgram(int indexid) {
		String sql = "delete from program where indexid = " + indexid +";";
		int count = FetchData.updateData(sql);
		return (count == 1);
	}
	
	@Override
	public boolean deleteProgram(String id) {
		String sql = "delete from program where id = '" + id +"';";
		int count = FetchData.updateData(sql);
		return (count == 1);
	}

	@Override
	public List<ProgramDTO> queryPrograms() {
		String sql = "select * from program order by indexid desc;";
		if(log.isDebugEnabled()){
			log.debug("sql : " + sql);
		}
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.ProgramDTO");
		List<ProgramDTO> programlist = new ArrayList<ProgramDTO>();
		for(Object o : list){
			programlist.add((ProgramDTO)o);
		}
		return programlist;
	}
	
	@Override
	public ProgramDTO queryProgram(String id) {
		ProgramDTO program = null;
		String sql = "select * from program where id = '" + id +"';";
		if(log.isDebugEnabled()){
			log.debug("sql : " + sql);
		}
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.ProgramDTO");
		if(list != null && !list.isEmpty()){
			program = (ProgramDTO)list.get(0);
		}
		return program;
	}

	@Override
	public boolean updateProgram(ProgramDTO program) {
		String sql = generateSql(program, FetchData.SQL_UPDATE);
		int count = FetchData.updateData(sql);
		return (count == 1);
	}

	@Override
	public boolean updateProgram(String programid, String flag) {
		String sql = "update program set canBeDelete = '"+flag+"' where id = '"+programid+"';";
		int count = FetchData.updateData(sql);
		return (count == 1);
	}
	
	private String generateSql(ProgramDTO program, String type){
		String sql = null;
		int indexid = program.getIndexid();
		String id = program.getId();
		String templateId = program.getTemplateId();
		String name = program.getName();
		String remark = program.getRemark();
		String length = program.getLength();
		String createtime = program.getCreatetime();
		String createby = program.getCreateby();
		String indexNum = program.getIndexNum();
		String canBeDelete = String.valueOf(program.isCanBeDelete());
		String templateWidth = program.getTemplateWidth();
		String templateHeight = program.getTemplateHeight();
		
		String radioValue = program.getRadioValue();
		String startDate = "";
		String endDate = "";
		String startHour = "";
		String startMinute = "";
		String startSecond = "";
		String endHour = "";
		String endMinute = "";
		String endSecond = "";
		
		String xingqi1 = "";
		String xingqi2 = "";
		String xingqi3 = "";
		String xingqi4 = "";
		String xingqi5 = "";
		String xingqi6 = "";
		String xingqi7 = "";
		if(radioValue.equals("1") || radioValue.equals("2")){
			startDate = program.getStartDate();
			endDate = program.getEndDate();
			startHour = program.getStartHour();
			startMinute = program.getStartMinute();
			startSecond = program.getStartSecond();
			endHour = program.getEndHour();
			endMinute = program.getEndMinute();
			endSecond = program.getEndSecond();
			
			xingqi1 = program.getXingqi1();
			xingqi2 = program.getXingqi2();
			xingqi3 = program.getXingqi3();
			xingqi4 = program.getXingqi4();
			xingqi5 = program.getXingqi5();
			xingqi6 = program.getXingqi6();
			xingqi7 = program.getXingqi7();
		}

		if (type.equals("INSERT")) {
			sql = "insert into program(indexid,id,templateId,name,remark,length,createtime,createby,indexNum,canBeDelete,templateWidth,templateHeight,radioValue,startDate,endDate,startHour,startMinute,startSecond,endHour,endMinute,endSecond,xingqi1,xingqi2,xingqi3,xingqi4,xingqi5,xingqi6,xingqi7) values("
				+ indexid
				+ ",'"
				+ id
				+ "','"
				+ templateId
				+ "','"
				+ name 
				+ "','" 
				+ remark
				+ "','"
				+ length 
				+ "','"
				+ createtime
				+ "','"
				+ createby
				+ "','"
				+ indexNum
				+ "','"
				+ canBeDelete
				+ "','"
				+ templateWidth
				+ "','"
				+ templateHeight
				+ "','"
				+ radioValue
				+ "','"
				+ startDate
				+ "','"
				+ endDate
				+ "','"
				+ startHour
				+ "','"
				+ startMinute
				+ "','"
				+ startSecond
				+ "','"
				+ endHour
				+ "','"
				+ endMinute
				+ "','"
				+ endSecond
				+ "','"
				+ xingqi1
				+ "','"
				+ xingqi2
				+ "','"
				+ xingqi3
				+ "','"
				+ xingqi4
				+ "','"
				+ xingqi5
				+ "','"
				+ xingqi6
				+ "','"
				+ xingqi7
				+ "');";
		}
		if (type.equals("UPDATE")) {
			sql = "update program set name='"+name+"',length='"+length+"',createtime='"+createtime+"',createby='"+createby+"',indexNum='"+indexNum+"',canBeDelete='"+canBeDelete+"',templateWidth='"+templateWidth+"',templateHeight='"+templateHeight+"' where id = '"+id+"';";
		}
		if (type.equals("DELETE")) {
			sql = "delete from program where indexid = " + indexid + ";";
		}
		if(log.isDebugEnabled()){
			log.debug("ProgramDaoImpl.generateSql() call -> sql : " + sql);
		}
		return sql;
	}
}
