package com.cemso.dto;

public class ResourceDTO {

	private Integer indexid;
	
	public Integer getIndexid() {
		return indexid;
	}
	public void setIndexid(Integer indexid) {
		this.indexid = indexid;
	}
	private String id;
	private String resourceName;
	private String resourceType;
	
	private String paramRemark;
	private String paramCreatetime;
	private String paramCreateby;
	
	private boolean canBeDelete;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getParamRemark() {
		return paramRemark;
	}
	public void setParamRemark(String paramRemark) {
		this.paramRemark = paramRemark;
	}
	public String getParamCreatetime() {
		return paramCreatetime;
	}
	public void setParamCreatetime(String paramCreatetime) {
		this.paramCreatetime = paramCreatetime;
	}
	public String getParamCreateby() {
		return paramCreateby;
	}
	public void setParamCreateby(String paramCreateby) {
		this.paramCreateby = paramCreateby;
	}
	public String getFlvname(){
		return resourceName.substring(0,resourceName.lastIndexOf("."))+".flv";
	}
    public boolean isCanBeDelete() {
        return canBeDelete;
    }
    public void setCanBeDelete(boolean canBeDelete) {
        this.canBeDelete = canBeDelete;
    }
    public void setCanBeDelete(String canBeDelete){
    	this.canBeDelete = Boolean.valueOf(canBeDelete);
    }
}
