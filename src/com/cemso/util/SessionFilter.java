/*
 * Created on Mar 1, 2012
 *
 * SessionFilter.java
 *
 * Copyright (C) 2012 by Citicorp Software &amp; Technology Services (Shanghai) Limited.
 * All rights reserved. Citicorp Software &amp; Technology Services (Shanghai) Limited
 * claims copyright in this computer program as an unpublished work. Claim of copyright
 * does not imply waiver of other rights.
 *
 * NOTICE OF PROPRIETARY RIGHTS
 *
 * This program is a confidential trade secret and the property of
 * Citicorp Software &amp; Technology Services (Shanghai) Limited. Use, examination,
 * reproduction, disassembly, decompiling, transfer and/or disclosure to others of
 * all or any part of this software program are strictly prohibited except by express
 * written agreement with Citicorp Software &amp; Technology Services (Shanghai) Limited.
 */
/*
 * ---------------------------------------------------------------------------------
 * Modification History
 * Date               Author                     Version     Description
 * Mar 1, 2012       gl65293                    1.0         New
 * ---------------------------------------------------------------------------------
 */
/**
 *
 */
package com.cemso.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cemso.dao.RegisterDao;
import com.cemso.dao.TreenodeDao;
import com.cemso.dao.impl.RegisterDaoImpl;
import com.cemso.dao.impl.TreenodeDaoImpl;
import com.cemso.dto.RegisterDTO;
import com.cemso.dto.TreeNodeDTO;
import com.cemso.dto.UserDTO;

/**
 * @author gl65293
 * 
 */
public class SessionFilter extends HttpServlet implements Filter {

	private static final long serialVersionUID = 1L;
	private RegisterDao registerDao = new RegisterDaoImpl();
	private TreenodeDao treenodeDao = new TreenodeDaoImpl();
	
	public RegisterDao getRegisterDao() {
		return registerDao;
	}
	public void setRegisterDao(RegisterDao registerDao) {
		this.registerDao = registerDao;
	}
	public TreenodeDao getTreenodeDao() {
		return treenodeDao;
	}
	public void setTreenodeDao(TreenodeDao treenodeDao) {
		this.treenodeDao = treenodeDao;
	}
	
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		
		List<RegisterDTO> registers = registerDao.queryUndeletedRegisters();
		String type = request.getParameter("type");
		if(registers.isEmpty() || XmlOperationTool.isTest){
			if(!XmlOperationTool.isTest){
				if((type != null && type.equals("register")) || (type != null && type.equals("check"))){
					chain.doFilter(request, response);
				}else{
					response.sendRedirect("register.jsp");
				}
			}else{
				TreeNodeDTO tnd = treenodeDao.queryTreeNode(10001);
				if((tnd != null) && (tnd.getAction() != null) && (tnd.getAction().equals("exp"))){
					response.sendRedirect("register.jsp?refresh=yes");
				}else{
					if((type != null && type.equals("register")) || (type != null && type.equals("check"))){
						chain.doFilter(request, response);
					}else{
						UserDTO user = (UserDTO) session.getAttribute("user");
						if (user == null) {
							response.sendRedirect("login.jsp?usernull=yes");
						} else {
							chain.doFilter(request, response);
						}
					}
				}
			}
		}else{
			if((type != null && type.equals("register")) || (type != null && type.equals("check"))){
				chain.doFilter(request, response);
			}else{
				UserDTO user = (UserDTO) session.getAttribute("user");
				if (user == null) {
					response.sendRedirect("login.jsp?usernull=yes");
				} else {
					chain.doFilter(request, response);
				}
			}
		}
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}