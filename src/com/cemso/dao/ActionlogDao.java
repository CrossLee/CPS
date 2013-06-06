package com.cemso.dao;

import com.cemso.dto.ActionlogDTO;

public interface ActionlogDao {

	public boolean addActionlog(ActionlogDTO actionlog);
	public boolean delActionlog(ActionlogDTO actionlog);
	public boolean delActionlog(String indexid);
	public boolean delAllActionlog();
}
