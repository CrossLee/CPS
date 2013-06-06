/*
 * Created on 2012-5-9
 *
 * ConstantUtil.java
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
 * 2012-5-9       CrossLee                    1.0         New
 * ---------------------------------------------------------------------------------
 */
/**
 * 
 */
package com.cemso.util;

/**
 * @author CrossLee
 * 
 */
public interface ConstantUtil {

	interface CheckLogs {
		public final String INVALID = "不合法的License文件，请联系管理员！";
		public final String VALID = "合法的License文件！";
		public final String TYPE_VALID = "valid";
		public final String TYPE_INVALID = "invalid";
	}

	interface Register {
		public final String YES = "yes";
		public final String NO = "no";
	}

	interface Status {
		int STATE_DISPLAY = 1;
		int STATE_HIDDEN = 2;
		int STATE_DELETED = 3;
	}

	interface Info_Status {
		int STATE_SAVE = 1;
		int STATE_PUBLISH = 2;
		int STATE_DELETED = 3;
	}

	/**
	 * 
	 * the sequence name of table
	 * 
	 * @author Cross
	 * 
	 */
	interface SequenceName {
		public final String REGISTER_SEQUENCE = "register";
		public final String DEVICE_SEQUENCE = "device";
		public final String ONEBYONE_SEQUENCE = "onebyone";
		public final String PLAYLIST_SEQUENCE = "playlist";
		public final String PROGRAM_SEQUENCE = "program";
		public final String RESOURCE_SEQUENCE = "resource";
		public final String TEMPLATE_SEQUENCE = "template";
		public final String TEMPLATEMODEL_SEQUENCE = "templatemodel";
		public final String TREENODE_SEQUENCE = "treenode";
		public final String USER_SEQUENCE = "user";
		public final String CHECKLOG_SEQUENCE = "checklog";
		public final String ACTIONLOG_SEQUENCE = "actionlog";
	}

}
