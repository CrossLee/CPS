/*
 * Created on Feb 17, 2012
 *
 * PlaylistDTO.java
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
package com.cemso.dto;

import java.util.List;

/**
 * @author gl65293
 *
 */
public class PlaylistDTO {

	private Integer indexid;
	private String programids;
	public Integer getIndexid() {
		return indexid;
	}
	public void setIndexid(Integer indexid) {
		this.indexid = indexid;
	}
    public String getProgramids() {
		return programids;
	}
	public void setProgramids(String programids) {
		this.programids = programids;
	}

	private String id;
    private String name;
    private String remark;
    private String createtime;
    private String createby;
    private boolean startnow;
    private String starttime;
    private String endtime;
    private List<ProgramDTO> programList;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getCreatetime() {
        return createtime;
    }
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    public String getCreateby() {
        return createby;
    }
    public void setCreateby(String createby) {
        this.createby = createby;
    }
    public boolean isStartnow() {
        return startnow;
    }
    public String getStartnow() {
    	return String.valueOf(startnow);
    }
    public void setStartnow(boolean startnow) {
        this.startnow = startnow;
    }
    public void setStartnow(String startnow) {
    	this.startnow = Boolean.valueOf(startnow);
    }
    public String getStarttime() {
        return starttime;
    }
    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }
    public String getEndtime() {
        return endtime;
    }
    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
    public List<ProgramDTO> getProgramList() {
        return programList;
    }
    public void setProgramList(List<ProgramDTO> programList) {
        this.programList = programList;
    }
    
}
