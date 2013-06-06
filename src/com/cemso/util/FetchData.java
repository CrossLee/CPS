/*
 * Created on 2012-4-20
 *
 * FetchData.java
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


import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class FetchData {
	
	public static final String SQL_UPDATE = "UPDATE";
	public static final String SQL_INSERT = "INSERT";
	public static final String SQL_DELETE = "DELETE";
	public static final String SQL_SELECT = "SELECT";
	
  /**
   * @param metaData  ResultSetMetaData对象
   * @param rs ResultSet对象
   * @param i 列数
   * @return 通过判断数据库字段的类型来处理字段的值
   */
	public static String getField(ResultSetMetaData metaData, ResultSet rs, int i) {
		String entry = "";
		try {
			if (metaData.getColumnType(i) == -4) {
				String nr = "";
				byte[] bnr = rs.getBytes(i);
				if (bnr != null) {
					nr = new String(bnr, "UTF-8");
				}
				entry = nr;
			} else {
				entry = rs.getString(i);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (entry == null) {
			entry = "";
		}
		return entry;
	}
  
	
	/**
	 * @param sSQL SQL语句
	 * @return 根据传入的sSQL得到包含结果的vectorA，注意数据库中一行放在一个vectorB中，多个vectorB存入vectorA
	 */
	@SuppressWarnings("unchecked")
	public static Vector getData(String sSQL) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Vector rows = new Vector();
		try {
			conn = ConnectionPool.getInstance().getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sSQL);

			ResultSetMetaData rsMetaData = rs.getMetaData();
			int cnt = rsMetaData.getColumnCount();
			while (rs.next()) {
				Vector newRow = new Vector();
				for (int i = 1; i < cnt + 1; i++) {
					String entry = getField(rsMetaData, rs, i);
					newRow.addElement(entry);
				}
				rows.addElement(newRow);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rows;
	}
  
	/**
	 * @param sSQL 查询语句
	 * @return 根据传入的sSQL得到包含结果的listA，注意数据库中一行放在一个listB中，多个listB存入listA
	 */
	@SuppressWarnings("unchecked")
	public static List getListData(String sSQL) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		List rows = new ArrayList();
		try {
			conn = ConnectionPool.getInstance().getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sSQL);

			ResultSetMetaData rsMetaData = rs.getMetaData();
			int cnt = rsMetaData.getColumnCount();
			while (rs.next()) {
				List newRow = new ArrayList();
				for (int i = 1; i < cnt + 1; i++) {
					String entry = getField(rsMetaData, rs, i);
					newRow.add(entry);
				}
				rows.add(newRow);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rows;
	}
  
	/**
	 * @param sSQL 
	 * @return 只返回单行结果集 list中存的数据库各个字段值
	 */
	@SuppressWarnings("unchecked")
	public static List getSingleListData(String sSQL) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		List rows = new ArrayList();
		try {
			conn = ConnectionPool.getInstance().getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sSQL);

			ResultSetMetaData rsMetaData = rs.getMetaData();
			int cnt = rsMetaData.getColumnCount();
			for (int i = 1; i < cnt + 1; i++) {
					String entry = getField(rsMetaData, rs, i);
					rows.add(entry);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rows;
	} 
	
	
	@SuppressWarnings("unchecked")
	public static Map getSelectMapData(String sSQL) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Map rows = new HashMap();
		try {
			conn = ConnectionPool.getInstance().getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sSQL);
            while(rs.next()){
            	String id=rs.getString(1);
            	String option=rs.getString(2);
            	rows.put(id, option);
            }
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rows;
	} 
	
	/**
	 * @param sSQL SQL语句
	 * @return 根据传入的sSQL得到一个字段的值
	 */
	public static String getSingleData(String sSQL) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String entry = "";
		try {
			conn = ConnectionPool.getInstance().getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sSQL);

			ResultSetMetaData rsMetaData = rs.getMetaData();
			if (rs.next()) {
				entry = getField(rsMetaData, rs, 1);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return entry;
	}
  
  
  /**
   * @param sSQL SQL语句
   * @return 判断数据库中有没有值返回布尔类型
   */
  public static boolean searchData(String sSQL){
	  Connection conn=null;
	  Statement st=null;
	  ResultSet rs=null;
	  boolean entry=false;
	  try{
		  conn=ConnectionPool.getInstance().getConnection();
		  st=conn.createStatement();
		  rs=st.executeQuery(sSQL);
		  
		  if(rs.next()){
			  entry=true;
		  }
	  }catch(SQLException se){
		  se.printStackTrace();
	  }finally{
		  try{
			  if(rs!=null){
				  rs.close();
			  }
			  if(st!=null){
				  st.close();
			  }
			  if(conn!=null){
				  conn.close();
			  }
		  }catch(Exception e){
			  e.printStackTrace();
		  }
	  }
	  return entry;
  } 
  
  
  
  	/**
  	 * @param sSQL SQL更新语句
  	 * @return 更新数据 
  	 */
	public static int updateData(String sSQL) {
		Connection conn = null;
		Statement st = null;
		int bl = 0;
		try {
			conn = ConnectionPool.getInstance().getConnection();
			st = conn.createStatement();
			bl = st.executeUpdate(sSQL);

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bl;
	}
	/**
	 * @param sSQL SQL更新语句 colStr SQL中参数
	 * @return 用PreparedStatement更新数据
	 */
	public static int updateData(String sSQL,String colStr[]) {
		Connection conn = null;
		PreparedStatement prep = null;
		int bl = 0;
		try {
			conn = ConnectionPool.getInstance().getConnection();
			conn.setAutoCommit(false);
			prep = conn.prepareStatement(sSQL);
			for(int i=0;i<colStr.length;i++){
				prep.setString(i+1, colStr[i]);
			}
			bl=prep.executeUpdate();
			conn.commit();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (prep != null) {
					prep.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bl;
	}
	
    /*
	 * @param sSQL 查询语句
	 * @return 根据传入的sSQL得到包含结果的listA，注意数据库中一行放在一个listB中，多个listB存入listA
	 */
	@SuppressWarnings("unchecked")
	public static List getListData(String sSQL,String colStr[]) {
		Connection conn = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		List rows = new ArrayList();
		try {
			conn = ConnectionPool.getInstance().getConnection();
			prep = conn.prepareStatement(sSQL);
			for(int i=0;i<colStr.length;i++){
				prep.setString(i+1, colStr[i]);
			}
			rs = prep.executeQuery();

			ResultSetMetaData rsMetaData = rs.getMetaData();
			int cnt = rsMetaData.getColumnCount();
			while (rs.next()) {
				List newRow = new ArrayList();
				for (int i = 1; i < cnt + 1; i++) {
					String entry = getField(rsMetaData, rs, i);
					newRow.add(entry);
				}
				rows.add(newRow);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (prep != null) {
					prep.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rows;
	}
  
	/**
	 * @param sSQL SQL更新语句
	 * @return  更新数据（非自动提交）
	*/
	public static int updateDataBySelf(String sSQL) {
		Connection conn = null;
		Statement st = null;
		int bl = 0;
		conn = ConnectionPool.getInstance().getConnection();
		try {
			conn.setAutoCommit(false);
			st = conn.createStatement();
			bl = st.executeUpdate(sSQL);
			conn.commit();
		} catch (SQLException se) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			se.printStackTrace();
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bl;
	}
	
	/**
	 * @param sSQL SQL更新语句数组
	 * @return  更新数据（非自动提交）
	*/
	public static int updateDataBySelf(String[] sSQL) {
		if(sSQL == null || sSQL.length == 0){
			return 0;
		}
		Connection conn = null;
		Statement st = null;
		int bl = 0;
		conn = ConnectionPool.getInstance().getConnection();
		try {
			conn.setAutoCommit(false);
			st = conn.createStatement();
			for(int i = 0 ; i < sSQL.length; i++){
				bl += st.executeUpdate(sSQL[i]);
			}
			conn.commit();
		} catch (SQLException se) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			se.printStackTrace();
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bl;
	}

	/**
	 * @param sql
	 * @param objPath
	 * @return 返回list中 ,list中存放的是object
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Object> queryObject(String sql,String objPath) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Object obj=null;
		Class<?> cls=null;
		Class type = null;
		String temp="";
		ArrayList<Object> alist=new ArrayList<Object>();
		try {
			
			conn = ConnectionPool.getInstance().getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int cnt = rsMetaData.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <=cnt; i++) {
					String c1=rsMetaData.getColumnName(i).substring(0, 1).toUpperCase();
					String c2=rsMetaData.getColumnName(i).substring(1);
					if(i==1){
						cls=getObject(objPath);
						try {
							obj = cls.newInstance();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					temp=getField(rsMetaData, rs, i);
					if(temp.equals("null")){
						temp="";
					}
					if(!temp.equals("")){
						if(rsMetaData.getColumnType(i)==12||rsMetaData.getColumnType(i)==-1){
							type=String.class;
							setter(obj,c1+c2, temp,type);
						}else if(rsMetaData.getColumnType(i)==4){
							type=Integer.class;
							setter(obj,c1+c2,new Integer(temp),type);
						}else if(rsMetaData.getColumnType(i)==7){
							type=Float.class;
							setter(obj,c1+c2,new Float(temp),type);
						}else if(rsMetaData.getColumnType(i)==8){
							type=Double.class;
							setter(obj,c1+c2,new Double(temp),type);
						}
					}else{
						continue;
					}
				}
				alist.add(obj);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return alist;
		
	}
	
	
	/**
	 * @param sql
	 * @param objPath
	 * @return 返回list中 ,list中存放的是object
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Object> queryObject(String sql,String objPath,String colStr[]) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement prep = null;
		Object obj=null;
		Class<?> cls=null;
		Class type = null;
		String temp="";
		ArrayList<Object> alist=new ArrayList<Object>();
		try {
			
			conn = ConnectionPool.getInstance().getConnection();
			prep = conn.prepareStatement(sql);
			for(int i=0;i<colStr.length;i++){
				prep.setString(i+1, colStr[i]);
			}
			rs = prep.executeQuery();
			
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int cnt = rsMetaData.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <=cnt; i++) {
					String c1=rsMetaData.getColumnName(i).substring(0, 1).toUpperCase();
					String c2=rsMetaData.getColumnName(i).substring(1);
					if(i==1){
						cls=getObject(objPath);
						try {
							obj = cls.newInstance();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					temp=getField(rsMetaData, rs, i);
					if(temp.equals("null")){
						temp="";
					}
					if(!temp.equals("")){
						if(rsMetaData.getColumnType(i)==12||rsMetaData.getColumnType(i)==-1){
							type=String.class;
							setter(obj,c1+c2, temp,type);
						}else if(rsMetaData.getColumnType(i)==4){
							type=Integer.class;
							setter(obj,c1+c2,new Integer(temp),type);
						}else if(rsMetaData.getColumnType(i)==7){
							type=Float.class;
							setter(obj,c1+c2,new Float(temp),type);
						}else if(rsMetaData.getColumnType(i)==8){
							type=Double.class;
							setter(obj,c1+c2,new Double(temp),type);
						}
					}else{
						continue;
					}
				}
				alist.add(obj);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (prep != null) {
					prep.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return alist;
		
	}
	
	
	/**
	 * @param objectPath
	 * @return 根据参数加载class
	 */
	public static Class<?> getObject(String objectPath) {
		Class<?> demo = null;
		
		try {
			demo = Class.forName(objectPath);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return demo;
	}
	
	/**
	 * @param obj 指定Object
	 * @param att javabean中set方法后缀名
	 * @param value
	 * @param type
	 */
	public static void setter(Object obj, String att, Object value, Class<?> type) {
		try {
			Method method = obj.getClass().getMethod("set" + att, type);
			method.invoke(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
