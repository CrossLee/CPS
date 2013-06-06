/*
 * Created on 2012-7-15
 *
 * OnebyoneDao.java
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

import com.cemso.dto.OnebyoneDTO;

/**
 * @author CrossLee
 *
 */
public interface OnebyoneDao {

	public List<OnebyoneDTO> queryOnebyones();
	public OnebyoneDTO queryOnebyone(int indexid);
	boolean deleteOnebyone(int indexid);
	boolean addOnebyone(OnebyoneDTO onebyone);
	boolean updateOnebyone(OnebyoneDTO onebyone);
	/**
	 * @param programid
	 * @return
	 */
	public boolean deleteOnebyone(String programid);
	
}
