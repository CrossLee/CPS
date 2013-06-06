package com.cemso.service;

import java.util.List;

import com.cemso.dto.TemplateDTO;

public interface TemplateService {

	public boolean addTemlate(TemplateDTO template);
	public boolean modifyTemlate(TemplateDTO template);
	public boolean deleteTemlate(TemplateDTO template);
	public boolean deleteTemlate(String id);
	public List<TemplateDTO> getTemlates();
}
