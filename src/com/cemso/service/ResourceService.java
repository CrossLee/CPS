package com.cemso.service;

import java.util.List;

import com.cemso.dto.ResourceDTO;

public interface ResourceService {

	public List<ResourceDTO> getResourceList();
	public boolean AddResource(ResourceDTO resource);
	public ResourceDTO queryResource(int indexid);
	public boolean deleteResource(int indexid);
	public boolean deleteResource(String id);
}
