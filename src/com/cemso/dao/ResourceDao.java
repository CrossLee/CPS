/*
 * Created on 2012-7-15
 *
 * ResourceDao.java
 *
 * Copyright (C) 2012 by Withiter Software & Technology Services (Shanghai) Limited.
 * All rights reserved. Withiter Software & Technology Services (Shanghai) Limited 
 * claims copyright in this computer program as an unpublished work. Claim of copyright 
 * does not imply waiver of other rights.
 *
 * NOTICE OF PROPRIETARY RIGHTS
 *
 * This program is a confidential trade secret and the property of 
 * Withiter Software & Technology Services (Shanghai) Limited. Use, examination, 
 * reproduction, disassembly, decompiling, transfer and/or disclosure to others of 
 * all or any part of this software program are strictly prohibited except by express 
 * written agreement with Withiter Software & Technology Services (Shanghai) Limited.
 */
/*
 * ---------------------------------------------------------------------------------
 * Modification History
 * Date               Author                     Version     Description
 * 2012-7-15       CrossLee                    1.0         New
 * ---------------------------------------------------------------------------------
 */
/**
 * 
 */
package com.cemso.dao;

import java.util.List;

import com.cemso.dto.ResourceDTO;

/**
 * @author CrossLee
 *
 */
public interface ResourceDao {

	public List<ResourceDTO> queryResources();
	public ResourceDTO queryResource(int indexid);
	public ResourceDTO queryResource(String resourceName);
	boolean deleteResource(int indexid);
	boolean deleteResource(String id);
	boolean addResource(ResourceDTO resource);
	boolean updateResource(ResourceDTO resource);
	/**
	 * @param resourceName
	 */
	public boolean updateResource(String resourceName,String flag);
	
}
