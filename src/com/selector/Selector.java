package com.selector;

public class Selector {
	private String source ="";
	private String link ="";
	private String contentSelector ="";
	private String titleSelector="";
	private String sourceTimeSelector="";
	private String authorSelector="";
	private String imgSelector="";
	private String sourceTimeRegex="";
	
	public String getSourceTimeRegex() {
		return sourceTimeRegex;
	}
	public void setSourceTimeRegex(String sourceTimeRegex) {
		this.sourceTimeRegex = sourceTimeRegex;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getImgSelector() {
		return imgSelector;
	}
	public void setImgSelector(String imgSelector) {
		this.imgSelector = imgSelector;
	}
	public String getContentSelector() {
		return contentSelector;
	}
	public void setContentSelector(String contentSelector) {
		this.contentSelector = contentSelector;
	}
	public String getTitleSelector() {
		return titleSelector;
	}
	public void setTitleSelector(String titleSelector) {
		this.titleSelector = titleSelector;
	}
	public String getSourceTimeSelector() {
		return sourceTimeSelector;
	}
	public void setSourceTimeSelector(String sourceTimeSelector) {
		this.sourceTimeSelector = sourceTimeSelector;
	}
	public String getAuthorSelector() {
		return authorSelector;
	}
	public void setAuthorSelector(String authorSelector) {
		this.authorSelector = authorSelector;
	}
	
	
}
