package com.cemso.dao;

import java.util.List;

import com.cemso.dto.ActionlogDTO;

public interface ManagerDao {

	public List<ActionlogDTO> queryActionlogs(String starttime, String endtime, String user);
	public boolean deleteActionlogs(String starttime, String endtime);
	public boolean deleteActionlogs(String user);
}
