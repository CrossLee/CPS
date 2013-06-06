/*
 * Created on 2012-7-15
 *
 * OnebyoneDaoImpl.java
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

import com.cemso.dao.OnebyoneDao;
import com.cemso.dto.OnebyoneDTO;
import com.cemso.util.FetchData;

/**
 * @author CrossLee
 *
 */
public class OnebyoneDaoImpl implements OnebyoneDao {

	private static final Log log = LogFactory.getLog(com.cemso.dao.impl.OnebyoneDaoImpl.class);
	@Override
	public boolean addOnebyone(OnebyoneDTO onebyone) {
		String sql = generateSql(onebyone, FetchData.SQL_INSERT);
		int count = FetchData.updateData(sql);
		return (count == 1);
	}

	@Override
	public boolean deleteOnebyone(int indexid) {
		// TODO Auto-generated method stub
		String sql = "delete from onebyone where indexid = " + indexid + ";";
		if(log.isDebugEnabled()){
			log.debug("sql : " + sql);
		}
		int count = FetchData.updateData(sql);
		return (count == 1);
	}
	
	@Override
	public boolean deleteOnebyone(String programid) {
		String sql = "delete from onebyone where programid = '" + programid + "';";
		if(log.isDebugEnabled()){
			log.debug("sql : " + sql);
		}
		int count = FetchData.updateData(sql);
		return (count == 1);
	}
	
	@Override
	public List<OnebyoneDTO> queryOnebyones() {
		String sql = "select * from onebyone order by indexid desc;";
		if(log.isDebugEnabled()){
			log.debug("sql : " + sql);
		}
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.OnebyoneDTO");
		List<OnebyoneDTO> onebyoneDTOList = new ArrayList<OnebyoneDTO>();
		for (int i = 0; i < list.size(); i++) {
			onebyoneDTOList.add((OnebyoneDTO) list.get(i));
		}
		return onebyoneDTOList;
	}

	@Override
	public boolean updateOnebyone(OnebyoneDTO onebyone) {
		String sql = generateSql(onebyone, FetchData.SQL_UPDATE);
		int count = FetchData.updateData(sql);
		return (count == 1);
	}
	
	@Override
	public OnebyoneDTO queryOnebyone(int indexid) {
		OnebyoneDTO onebyone = null;
		String sql = "select * from onebyone;";
		if(log.isDebugEnabled()){
			log.debug(sql);
		}
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.OnebyoneDTO");
		if(!list.isEmpty()){
			onebyone = (OnebyoneDTO) list.get(0);
		}
		return onebyone;
	}

	private String generateSql(OnebyoneDTO onebyone, String type){
		String sql = null;
		int indexid = onebyone.getIndexid();
		String programid = onebyone.getProgramid();
		String dimension = onebyone.getDimension();
		String resource = onebyone.getResource();
		String resourceName = onebyone.getResourceName();
		String gradation = onebyone.getGradation();
		String direction = onebyone.getDirection();
		String textContent = onebyone.getTextContent();

		String fontColor = onebyone.getFontColor();
		String fontName = onebyone.getFontName();
		String fontSize = onebyone.getFontSize();
		String fontBolder = onebyone.getFontBolder();
		String fontItalic = onebyone.getFontItalic();
		String fontUnder = onebyone.getFontUnder();
		String fontNextLine = onebyone.getFontNextLine();
		String fontHAlign = onebyone.getFontHAlign();
		String fontVAlign = onebyone.getFontVAlign();
		String speed = onebyone.getSpeed();
		String delay = onebyone.getDelay();
		
		// for clock
		String mode = onebyone.getMode();
		String format = onebyone.getFormat();
		
		if (type.equals("INSERT")) {
			sql = "insert into onebyone(indexid,programid,dimension,resource,resourceName,gradation,direction,textContent,fontColor,fontName,fontSize,fontBolder,fontItalic,fontUnder,fontNextLine,fontHAlign,fontVAlign,speed,delay,mode,format) values("
					+ indexid
					+ ",'"
					+ programid
					+ "','"
					+ dimension
					+ "','"
					+ resource
					+ "','"
					+ resourceName 
					+ "','" 
					+ gradation 
					+ "','"
					+ direction
					+ "','"
					+ textContent
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
		if (type.equals("UPDATE")) {
			sql = "update onebyone set dimension='"+dimension+"',resource='"+resource+"',resourceName='"+resourceName+"',gradation='"+gradation+"',direction='"+direction+"',textContent='"+textContent+"' where programid = "+programid+";";
		}
		if (type.equals("DELETE")) {
			sql = "delete from onebyone where indexid = " + indexid + ";";
		}
		if(log.isDebugEnabled()){
			log.debug("OnebyoneDaoImpl.generateSql() call -> sql : " + sql);
		}
		return sql;
	}
}
