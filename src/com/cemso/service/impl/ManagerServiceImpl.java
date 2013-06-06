package com.cemso.service.impl;

import java.util.List;

import com.cemso.dao.ManagerDao;
import com.cemso.dto.ActionlogDTO;
import com.cemso.service.ManagerService;

public class ManagerServiceImpl implements ManagerService {

	private ManagerDao managerDao;
	
	public ManagerDao getManagerDao() {
		return managerDao;
	}

	public void setManagerDao(ManagerDao managerDao) {
		this.managerDao = managerDao;
	}

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
		return managerDao.queryActionlogs(starttime, endtime, user);
	}

}
