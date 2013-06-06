package com.cemso.dto;

public class OnebyoneDTO {

	private Integer indexid;
	
	public Integer getIndexid() {
		return indexid;
	}
	public void setIndexid(Integer indexid) {
		this.indexid = indexid;
	}
	private String programid;
	private String dimension;
	private String resource;
	private String resourceName;
	private String gradation;
	private String direction;
	
	// for get and set text file's content
	private String textContent;

	// font params
	private String fontColor;
	private String fontName;
	private String fontSize;
	private String fontBolder;
	private String fontItalic;
	private String fontUnder;
	private String fontNextLine;
	private String fontHAlign;
	private String fontVAlign;
	private String speed;
	private String delay;
	
	// for clock
	private String mode;
	private String format;
	
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getFontColor() {
		return fontColor;
	}
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	public String getFontSize() {
		return fontSize;
	}
	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}
	public String getFontBolder() {
		return fontBolder;
	}
	public void setFontBolder(String fontBolder) {
		this.fontBolder = fontBolder;
	}
	public String getFontItalic() {
		return fontItalic;
	}
	public void setFontItalic(String fontItalic) {
		this.fontItalic = fontItalic;
	}
	public String getFontUnder() {
		return fontUnder;
	}
	public void setFontUnder(String fontUnder) {
		this.fontUnder = fontUnder;
	}
	public String getFontNextLine() {
		return fontNextLine;
	}
	public void setFontNextLine(String fontNextLine) {
		this.fontNextLine = fontNextLine;
	}
	public String getFontHAlign() {
		return fontHAlign;
	}
	public void setFontHAlign(String fontHAlign) {
		this.fontHAlign = fontHAlign;
	}
	public String getFontVAlign() {
		return fontVAlign;
	}
	public void setFontVAlign(String fontVAlign) {
		this.fontVAlign = fontVAlign;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getDelay() {
		return delay;
	}
	public void setDelay(String delay) {
		this.delay = delay;
	}
	public String getResourceType(){
		int underlineIndex = resource.indexOf("_");
		String type = resource.substring(underlineIndex);
		return type;
	}
	
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getGradation() {
		return gradation;
	}
	public void setGradation(String gradation) {
		this.gradation = gradation;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getTextContent() {
		return textContent;
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	public String getProgramid() {
		return programid;
	}
	public void setProgramid(String programid) {
		this.programid = programid;
	}
}
