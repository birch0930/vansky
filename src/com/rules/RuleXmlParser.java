package com.rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
/*
 * 解析规则xml类
 */
public class RuleXmlParser{
	
	public List<RuleObject> parserXml(String fileName) throws DocumentException {   
		List<RuleObject> list = new ArrayList<RuleObject>();
		// SAXReadr对象   
		SAXReader saxr = new SAXReader();   
	
		// 使用getResourceAsStream返回的是InputStream流对象 使用getResoruce返回是Java.net.url对象   
		Document document = saxr.read(RuleXmlParser.class.getClassLoader().getResourceAsStream(fileName));   
		// 获得文档的根元素   
		Element rootElement = document.getRootElement(); 
		// 遍历一级节点   
		for (Iterator i = rootElement.elementIterator(); i.hasNext();) {   
			RuleObject ruleObject = new RuleObject();
			Element tag = (Element) i.next();
			ruleObject.setSource(tag.attributeValue("source"));
			ruleObject.setUrl(tag.element("url").getText());
			ruleObject.setUrlFilter(tag.element("urlFilter").getText());
			ruleObject.setNewsType(tag.element("newsType").getText());
			ruleObject.setWebName(tag.element("web-name").getText());
			list.add(ruleObject);
			
		}   
		return list;
	}
	
	
	public RuleObject getRuleBySource(String source){
		RuleObject rule = null;
		List<RuleObject> list;
		try {
			list = parserXml("rule.xml");
			for (RuleObject r :list) {   
				if(r.getSource().equalsIgnoreCase(source))
					rule = r;	
			}   
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rule;
		
	}
	
}