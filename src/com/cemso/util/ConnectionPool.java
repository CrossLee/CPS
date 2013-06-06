/*
 * Created on 2012-4-20
 *
 * ConnectionPool.java
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
 * 2012-4-20       Administrator                    1.0         New
 * ---------------------------------------------------------------------------------
 */
package com.cemso.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConnectionPool {

	private static ConnectionPool instance = new ConnectionPool();

	private ConnectionPool() {
	}

	/**
	 * 单例模式,获取单例
	 * 
	 * @return ConnectionPool的单例对象
	 */
	public static ConnectionPool getInstance() {
		return instance;
	}

	/**
	 * @return 得到数据库连接
	 */
	public Connection getConnection() {
		Connection conn = null;
		try {
			ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
			DataSource ds = ((DataSource) cxt.getBean("dataSource"));
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
