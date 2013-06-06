/*
 * Created on Feb 17, 2012
 *
 * PlaylistService.java
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
package com.cemso.service;

import java.util.List;

import com.cemso.dto.PlaylistDTO;

/**
 * @author gl65293
 *
 */
public interface PlaylistService {

    public boolean addPlaylist(PlaylistDTO playlist);
    public PlaylistDTO queryPlaylistDTOById(String id);
    public boolean deletePlaylist(String id);
    public PlaylistDTO queryPlaylist(int indexid);
    public List<PlaylistDTO> getPlaylist();
}
