package com.cemso.dto;

import java.util.List;

public class ProgramDTO {

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
	private String length;
	private String createtime;
	private String createby;
	private String templateId;
	private List<OnebyoneDTO> onebyoneList;

	// for playlist
	private String indexNum;

	private boolean canBeDelete;
	private String templateWidth;
	private String templateHeight;
	
	private String radioValue;
	
	private String startDate;
	private String endDate;
	private String startHour;
	private String startMinute;
	private String startSecond;
	private	String endHour;
	private	String endMinute;
	private	String endSecond;

	private	String xingqi1;
	private	String xingqi2;
	private	String xingqi3;
	private	String xingqi4;
	private	String xingqi5;
	private	String xingqi6;
	private	String xingqi7;

	public String getRadioValue() {
		return radioValue;
	}

	public void setRadioValue(String radioValue) {
		this.radioValue = radioValue;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	public String getStartMinute() {
		return startMinute;
	}

	public void setStartMinute(String startMinute) {
		this.startMinute = startMinute;
	}

	public String getStartSecond() {
		return startSecond;
	}

	public void setStartSecond(String startSecond) {
		this.startSecond = startSecond;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	public String getEndMinute() {
		return endMinute;
	}

	public void setEndMinute(String endMinute) {
		this.endMinute = endMinute;
	}

	public String getEndSecond() {
		return endSecond;
	}

	public void setEndSecond(String endSecond) {
		this.endSecond = endSecond;
	}

	public String getXingqi1() {
		return xingqi1;
	}

	public void setXingqi1(String xingqi1) {
		this.xingqi1 = xingqi1;
	}

	public String getXingqi2() {
		return xingqi2;
	}

	public void setXingqi2(String xingqi2) {
		this.xingqi2 = xingqi2;
	}

	public String getXingqi3() {
		return xingqi3;
	}

	public void setXingqi3(String xingqi3) {
		this.xingqi3 = xingqi3;
	}

	public String getXingqi4() {
		return xingqi4;
	}

	public void setXingqi4(String xingqi4) {
		this.xingqi4 = xingqi4;
	}

	public String getXingqi5() {
		return xingqi5;
	}

	public void setXingqi5(String xingqi5) {
		this.xingqi5 = xingqi5;
	}

	public String getXingqi6() {
		return xingqi6;
	}

	public void setXingqi6(String xingqi6) {
		this.xingqi6 = xingqi6;
	}

	public String getXingqi7() {
		return xingqi7;
	}

	public void setXingqi7(String xingqi7) {
		this.xingqi7 = xingqi7;
	}

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

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
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

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public List<OnebyoneDTO> getOnebyoneList() {
		return onebyoneList;
	}

	public void setOnebyoneList(List<OnebyoneDTO> onebyoneList) {
		this.onebyoneList = onebyoneList;
	}

	public String getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(String indexNum) {
		this.indexNum = indexNum;
	}

	public boolean isCanBeDelete() {
		return canBeDelete;
	}

	public void setCanBeDelete(boolean canBeDelete) {
		this.canBeDelete = canBeDelete;
	}

	public String getTemplateWidth() {
		return templateWidth;
	}

	public void setTemplateWidth(String templateWidth) {
		this.templateWidth = templateWidth;
	}

	public String getTemplateHeight() {
		return templateHeight;
	}

	public void setTemplateHeight(String templateHeight) {
		this.templateHeight = templateHeight;
	}

	public void setCanBeDelete(String canBeDelete) {
		this.canBeDelete = Boolean.valueOf(canBeDelete);
	}
}
