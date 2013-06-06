package com.cemso.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cemso.dao.ManagerDao;
import com.cemso.dto.ActionlogDTO;
import com.cemso.util.FetchData;

public class ManagerDaoImpl implements ManagerDao {

	private Log log = LogFactory.getLog(ManagerDaoImpl.class);
	
	@Override
	public boolean deleteActionlogs(String starttime, String endtime) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteActionlogs(String user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ActionlogDTO> queryActionlogs(String starttime, String endtime,
			String user) {
		String sql = "";
		if(user != null && user.equals("all")){
			sql = "select * from actionlog where time > '"+starttime+"' and time < '"+endtime+"' order by indexid desc;";
		}else{
			sql = "select * from actionlog where time > '"+starttime+"' and time < '"+endtime+"' and user = '"+user+"'  order by indexid desc;";
		}
		if(log.isDebugEnabled()){
			log.debug("sql : " + sql);
		}
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.ActionlogDTO");
		List<ActionlogDTO> logs = new ArrayList<ActionlogDTO>();
		for (int i = 0; i < list.size(); i++) {
			logs.add((ActionlogDTO) list.get(i));
		}
		return logs;
	}

}
