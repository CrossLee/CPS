package com.cemso.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cemso.dto.ActionlogDTO;
import com.cemso.service.ManagerService;
import com.opensymphony.xwork2.ActionSupport;

public class ManagerAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2762097631325136365L;

	private Log log = LogFactory.getLog(ManagerAction.class);
	
	private String starttime;
	private String endtime;
	private String user;
	
	private ManagerService managerService;

	private List<ActionlogDTO> logs;
	
	public List<ActionlogDTO> getLogs() {
		return logs;
	}
	public void setLogs(List<ActionlogDTO> logs) {
		this.logs = logs;
	}
	public ManagerService getManagerService() {
		return managerService;
	}
	public void setManagerService(ManagerService managerService) {
		this.managerService = managerService;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	public String querylog(){
		if(log.isInfoEnabled()){
			log.info("ManagerAction ¡ª¡ª> querylog() called"); 
		}
		try{
			logs = managerService.queryActionlogs(starttime, endtime, user);
			return SUCCESS;
		} catch (Exception e){
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
				log.error(e.getCause());
			}
			return ERROR;
		}
	}
	
	
	
}
