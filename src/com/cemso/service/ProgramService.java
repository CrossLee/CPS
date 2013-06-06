package com.cemso.service;

import java.util.List;

import com.cemso.dto.ProgramDTO;

public interface ProgramService {

	public boolean addProgram(ProgramDTO program);
	public List<ProgramDTO> getProgramList();
	public boolean updateProgram(ProgramDTO program);
	/**
	 * @param string
	 * @return
	 */
	public boolean deleteProgram(String id);
}
