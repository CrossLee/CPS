package com.cemso.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cemso.dto.ActionlogDTO;
import com.cemso.util.FetchData;

public class ActionlogDaoImpl {

	private static Log log = LogFactory.getLog(ActionlogDaoImpl.class);
	
	private static String generateSql(ActionlogDTO actionlog, String type){
		String sqlforexcute = null;
		int indexid = 0;
		String time = "";
		String user = "";
		String action = "";
		String sqlstring = "";
		if(type.equals("INSERT")){
			indexid = actionlog.getIndexid();
			time = actionlog.getTime();
			user = actionlog.getUser();
			action = actionlog.getAction();
			sqlstring = actionlog.getSqlstring();
			sqlforexcute = "insert into actionlog(indexid,time,user,action,sqlstring) values ("
				+indexid+",'"+time+"','"+user+"','"+action+"','"+sqlstring+"');";
		}
		if (type.equals("DELETE")) {
			indexid = actionlog.getIndexid();
			sqlforexcute = "delete from actionlog where indexid = " + indexid + ";";
		}
		if (log.isDebugEnabled()) {
			log.debug("ActionlogDaoImpl.generateSql() call -> sql : " + sqlforexcute);
		}
		return sqlforexcute;
	}
	
	public static boolean addActionlog(ActionlogDTO actionlog) {
		String sql = generateSql(actionlog, FetchData.SQL_INSERT);
		int count = FetchData.updateData(sql);
		return (count > 0) ? true : false;
	}

	public static boolean delActionlog(ActionlogDTO actionlog) {
		String sql = generateSql(actionlog, FetchData.SQL_DELETE);
		int count = FetchData.updateData(sql);
		return (count > 0) ? true : false;
	}

	public static boolean delActionlog(String indexid) {
		String sql = "delete from actionlog where indexid = "+indexid+";";
		int count = FetchData.updateData(sql);
		return (count > 0) ? true : false;
	}

	public static boolean delAllActionlog() {
		String sql = "delete from actionlog;";
		int count = FetchData.updateData(sql);
		return (count > 0) ? true : false;
	}

}
