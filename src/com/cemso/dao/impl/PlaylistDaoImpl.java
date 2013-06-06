/*
 * Created on 2012-7-15
 *
 * PlaylistDaoImpl.java
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
package com.cemso.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cemso.dao.PlaylistDao;
import com.cemso.dto.PlaylistDTO;
import com.cemso.util.FetchData;

/**
 * @author CrossLee
 *
 */
public class PlaylistDaoImpl implements PlaylistDao{

	private static final Log log = LogFactory.getLog(com.cemso.dao.impl.PlaylistDaoImpl.class);
	
	@Override
	public boolean addPlaylist(PlaylistDTO playlist) {
		String sql = generateSql(playlist, FetchData.SQL_INSERT);
		int count = FetchData.updateData(sql);
		return (count == 1);
	}

	@Override
	public boolean deletePlaylist(String id) {
		String sql = "delete from playlist where id = '" + id +"';";
		int count = FetchData.updateData(sql);
		return (count == 1);
	}

	@Override
	public List<PlaylistDTO> queryPlaylists() {
		String sql = "select * from playlist order by indexid desc";
		if(log.isDebugEnabled()){
			log.debug("sql : " + sql);
		}
		List<Object> list = FetchData.queryObject(sql,"com.cemso.dto.PlaylistDTO");
		List<PlaylistDTO> playlistList = new ArrayList<PlaylistDTO>();
		for (int i = 0; i < list.size(); i++) {
			playlistList.add((PlaylistDTO) list.get(i));
		}
		return playlistList;
	}

	@Override
	public boolean updatePlaylist(PlaylistDTO playlist) {
		String sql = generateSql(playlist, FetchData.SQL_UPDATE);
		int count = FetchData.updateData(sql);
		return (count == 1);
	}

	private String generateSql(PlaylistDTO playlist, String type){
		String sql = null;
		int indexid = playlist.getIndexid();
		String id = playlist.getId();
		String programids = playlist.getProgramids();
		String name = playlist.getName();
		String remark = playlist.getRemark();
		String createtime = playlist.getCreatetime();
		String createby = playlist.getCreateby();
		String startnow = String.valueOf(playlist.isStartnow());
		String starttime = playlist.getStarttime();
		String endtime = playlist.getEndtime();

		if (type.equals("INSERT")) {
			sql = "insert into playlist(indexid,programids,id,name,remark,createtime,createby,startnow,starttime,endtime) values("
					+ indexid
					+ ",'"
					+ programids
					+ "','"
					+ id
					+ "','"
					+ name
					+ "','"
					+ remark 
					+ "','" 
					+ createtime 
					+ "','"
					+ createby
					+ "','"
					+ startnow
					+ "','"
					+ starttime
					+ "','"
					+ endtime
					+ "');";
		}
		if (type.equals("UPDATE")) {
			sql = "update playlist set id='"+id+"',name='"+name+"',remark='"+remark+"',createtime='"+createtime+"',createby='"+createby+"',startnow='"+startnow+"',starttime='"+starttime+"',endtime='"+endtime+"' where indexid = "+indexid+";";
		}
		if (type.equals("DELETE")) {
			sql = "delete from playlist where indexid = " + indexid + ";";
		}
		if(log.isDebugEnabled()){
			log.debug("PlaylistDaoImpl.generateSql() call -> sql : " + sql);
		}
		return sql;
	}

	@Override
	public PlaylistDTO queryPlaylist(int indexid) {
		PlaylistDTO playlist = null;
		String sql = "select * from playlist where indexid = " + indexid;
		if(log.isDebugEnabled()){
			log.debug("sql : " + sql);
		}
		List<Object> list = FetchData.queryObject(sql,"com.cemso.dto.PlaylistDTO");
		if(list != null && !list.isEmpty()){
			playlist = (PlaylistDTO)list.get(0);
		}
		return playlist;
	}
	
	@Override
	public PlaylistDTO queryPlaylist(String id) {
		PlaylistDTO playlist = null;
		String sql = "select * from playlist where id = '" + id + "';";
		if(log.isDebugEnabled()){
			log.debug("sql : " + sql);
		}
		List<Object> list = FetchData.queryObject(sql,"com.cemso.dto.PlaylistDTO");
		if(list != null && !list.isEmpty()){
			playlist = (PlaylistDTO)list.get(0);
		}
		return playlist;
	}
}
