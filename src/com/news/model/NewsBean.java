package com.news.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrator
 */


public class NewsBean implements Serializable{
	private int newsId =0; 		//新闻网站
	private String sourceUrl =""; 		//新闻网站
	private String sourceWebsite="";
	private String title = ""; 			//新闻标题
	private Date sourceTime ;		//新闻来源时间		
	private Date collectTime ;		//新闻采集时间	
	private String Content = "";		//新闻内容
	private int status = 0;		//新闻内容
	private List<String> imgList  ;		
	
	public NewsBean() {
		imgList = new ArrayList<String>();
	}
	public List<String> getImgList() {
		return imgList;
	}
	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	

	public Date getSourceTime() {
		return sourceTime;
	}
	public void setSourceTime(Date sourceTime) {
		this.sourceTime = sourceTime;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	
	public String getSourceWebsite() {
		return sourceWebsite;
	}
	public void setSourceWebsite(String sourceWebsite) {
		this.sourceWebsite = sourceWebsite;
	}
	public int getNewsId() {
		return newsId;
	}
	public void setNewsId(int s) {
		this.newsId = s;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

	
	
  
}
