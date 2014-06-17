package com.rules;

/*
 * 规则的简单数据对象
 */
public class RuleObject{
	private String source="";	//新闻来源
	private String url=""; 			//链接地址，新闻链接从此页面中解析获取
	private String urlFilter="";	//过滤新闻链接的正则表达式
	private String newsType="";		//新闻分类
	private String webName="";		//新闻分类
	
	
	public String getWebName() {
		return webName;
	}
	public void setWebName(String webName) {
		this.webName = webName;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrlFilter() {
		return urlFilter;
	}
	public void setUrlFilter(String urlFilter) {
		this.urlFilter = urlFilter;
	}
	public String getNewsType() {
		return newsType;
	}
	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}
}