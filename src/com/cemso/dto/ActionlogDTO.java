package com.cemso.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActionlogDTO {

	public static String username = "";
	
	private Integer indexid;
	private String time;
	private String user;
	private String action;
	private String sqlstring;
	
	public static String currentTime(){
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString();
		return time;
	}
	
	public ActionlogDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ActionlogDTO(Integer indexid, String time, String user,
			String action, String sqlstring) {
		super();
		this.indexid = indexid;
		this.time = time;
		this.user = user;
		this.action = action;
		this.sqlstring = sqlstring;
	}
	public Integer getIndexid() {
		return indexid;
	}
	public void setIndexid(Integer indexid) {
		this.indexid = indexid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getSqlstring() {
		return sqlstring;
	}
	public void setSqlstring(String sqlstring) {
		this.sqlstring = sqlstring;
	}
}
