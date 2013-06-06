/*
 * Created on 2012-10-1
 *
 * RegisterService.java
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
 * 2012-10-1       CrossLee                    1.0         New
 * ---------------------------------------------------------------------------------
 */
/**
 * 
 */
package com.cemso.service;

import java.util.List;

import com.cemso.dto.ChecklogDTO;
import com.cemso.dto.RegisterDTO;

/**
 * @author CrossLee
 *
 */
public interface RegisterService {

	public boolean add(List<RegisterDTO> list);
	public boolean updateToDelete();
	public boolean isNoDeletedExist();
	public boolean checkRegister(String mac);
	
	public List<RegisterDTO> queryRegisters();
	public boolean addChecklog(String info, String type);
	
	public ChecklogDTO queryLastChecklog();
}
