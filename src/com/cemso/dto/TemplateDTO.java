package com.cemso.dto;

import java.util.List;

public class TemplateDTO {

	private Integer indexid;

	public Integer getIndexid() {
		return indexid;
	}

	public void setIndexid(Integer indexid) {
		this.indexid = indexid;
	}
	
	private String id;
	private String name;
	private String remark;
	private String size;
	private String createTime;
	private String createBy;
	private List<TemplateModelDTO> models;

	private boolean canBeDelete;

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public List<TemplateModelDTO> getModels() {
		return models;
	}

	public void setModels(List<TemplateModelDTO> models) {
		this.models = models;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public boolean isCanBeDelete() {
		return canBeDelete;
	}

	public void setCanBeDelete(boolean canBeDelete) {
		this.canBeDelete = canBeDelete;
	}

	public void setCanBeDelete(String canBeDelete) {
		this.canBeDelete = Boolean.valueOf(canBeDelete);
	}
}
