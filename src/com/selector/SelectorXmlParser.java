package com.selector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class SelectorXmlParser {
	

	private List<Selector> parserXml(String fileName) throws DocumentException {   
		List<Selector> list = new ArrayList<Selector>();
		// SAXReadr对象   
		SAXReader saxr = new SAXReader();   
	
		// 使用getResourceAsStream返回的是InputStream流对象 使用getResoruce返回是Java.net.url对象   
		Document document = saxr.read(SelectorXmlParser.class.getClassLoader().getResourceAsStream(fileName));   
		// 获得文档的根元素   
		Element rootElement = document.getRootElement(); 
		// 遍历一级节点   
		for (Iterator i = rootElement.elementIterator(); i.hasNext();) {   
			Selector selector = new Selector();
			Element tag = (Element) i.next();
			selector.setSource(tag.attributeValue("source"));			
			selector.setTitleSelector(tag.element("title").getText());
			//selector.setAuthorSelector(tag.element("author").getText());
			selector.setSourceTimeSelector(tag.element("sourceTime").getText());
			selector.setImgSelector(tag.element("img").getText());
			selector.setContentSelector(tag.element("content").getText());	
			selector.setLink(tag.element("link").getText());
			selector.setSourceTimeRegex(tag.element("sourceTimeRegex").getText());
			
			list.add(selector);
			
		}   
		return list;
	}
	
	public Selector getSelectorBySource(String source){
		Selector selector = null;
		List<Selector> list;
		try {
			list = parserXml("selector.xml");
			for (Selector s :list) {   
				if(s.getSource().equalsIgnoreCase(source))
					selector = s;	
			}   
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return selector;
		
	}
}
