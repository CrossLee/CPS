/*
 * Created on Feb 17, 2012
 *
 * PlaylistServiceImpl.java
 *
 * Copyright (C) 2012 by Citicorp Software & Technology Services (Shanghai) Limited.
 * All rights reserved. Citicorp Software & Technology Services (Shanghai) Limited 
 * claims copyright in this computer program as an unpublished work. Claim of copyright 
 * does not imply waiver of other rights.
 *
 * NOTICE OF PROPRIETARY RIGHTS
 *
 * This program is a confidential trade secret and the property of 
 * Citicorp Software & Technology Services (Shanghai) Limited. Use, examination, 
 * reproduction, disassembly, decompiling, transfer and/or disclosure to others of 
 * all or any part of this software program are strictly prohibited except by express 
 * written agreement with Citicorp Software & Technology Services (Shanghai) Limited.
 */
/*
 * ---------------------------------------------------------------------------------
 * Modification History
 * Date               Author                     Version     Description
 * Feb 17, 2012       gl65293                    1.0         New
 * ---------------------------------------------------------------------------------
 */
/**
 * 
 */
package com.cemso.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.cemso.dao.PlaylistDao;
import com.cemso.dao.ProgramDao;
import com.cemso.dao.impl.ActionlogDaoImpl;
import com.cemso.dto.ActionlogDTO;
import com.cemso.dto.PlaylistDTO;
import com.cemso.dto.ProgramDTO;
import com.cemso.service.PlaylistService;
import com.cemso.util.ConstantUtil;
import com.cemso.util.SequenceUtil;

/**
 * @author gl65293
 *
 */
public class PlaylistServiceImpl implements PlaylistService {
	
	private PlaylistDao playlistDao;
	private ProgramDao programDao;
    public PlaylistDao getPlaylistDao() {
		return playlistDao;
	}
	public void setPlaylistDao(PlaylistDao playlistDao) {
		this.playlistDao = playlistDao;
	}

	public ProgramDao getProgramDao() {
		return programDao;
	}
	public void setProgramDao(ProgramDao programDao) {
		this.programDao = programDao;
	}
	
	public PlaylistDTO queryPlaylistDTOById(String id){
		return playlistDao.queryPlaylist(id);
	}
	
    public boolean addPlaylist(PlaylistDTO playlist) {
    	int indexid = SequenceUtil.getInstance().getNextKeyValue(ConstantUtil.SequenceName.PLAYLIST_SEQUENCE);
    	playlist.setIndexid(indexid);
        boolean flag = playlistDao.addPlaylist(playlist);
        boolean flag2 = true;
        
        String programids = playlist.getProgramids();
        String[] ids = programids.split(",");
        for(String id : ids){
        	flag2 &= programDao.updateProgram(id,"false");
        }
        
        if(flag && flag2){
			Integer indexid1 = SequenceUtil.getInstance().getNextKeyValue(
					ConstantUtil.SequenceName.ACTIONLOG_SEQUENCE);
			String time = ActionlogDTO.currentTime();
			String user = ActionlogDTO.username;
			String action = "添加了播表（ID）: "+playlist.getId()+", 播表名称: "+ playlist.getName();
			String sql = "";
			ActionlogDTO actionlog = new ActionlogDTO(indexid1,time,user,action,sql);
			ActionlogDaoImpl.addActionlog(actionlog);
		}
        
        return flag && flag2;
    }

    public List<PlaylistDTO> getPlaylist() {
    	List<PlaylistDTO> list = playlistDao.queryPlaylists();
    	for(PlaylistDTO playlist : list){
    		String programids = playlist.getProgramids();
    		String[] ids = programids.split(",");
    		List<ProgramDTO> programs = new ArrayList<ProgramDTO>();
    		for(String programid : ids){
    			ProgramDTO program = programDao.queryProgram(programid);
    			programs.add(program);
    		}
    		playlist.setProgramList(programs);
    	}
        return list;
    }

	@Override
	public boolean deletePlaylist(String id) {
		boolean flag = playlistDao.deletePlaylist(id);
		if(flag){
			Integer indexid1 = SequenceUtil.getInstance().getNextKeyValue(
					ConstantUtil.SequenceName.ACTIONLOG_SEQUENCE);
			String time = ActionlogDTO.currentTime();
			String user = ActionlogDTO.username;
			String action = "删除了播表（ID）: "+id;
			String sql = "";
			ActionlogDTO actionlog = new ActionlogDTO(indexid1,time,user,action,sql);
			ActionlogDaoImpl.addActionlog(actionlog);
		}
		return flag;
	}

	@Override
	public PlaylistDTO queryPlaylist(int indexid) {
		return playlistDao.queryPlaylist(indexid);
	}
}
