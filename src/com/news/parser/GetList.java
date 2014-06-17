package com.news.parser;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.rules.RuleObject;
import com.rules.RuleXmlParser;
import com.selector.Selector;
import com.selector.SelectorXmlParser;

public class GetList extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SelectorXmlParser selectorXmlParser = new SelectorXmlParser();
		RuleXmlParser ruleXmlParser = new RuleXmlParser();
		StringBuffer html = new StringBuffer();
		RuleObject rule =null;
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String previousLink = "";
		String previousTitle = "";
		String[] sourceLink = request.getParameterValues("sourceLink[]");
		if(sourceLink == null) {
			
			out.println(" 未正确得到链接 ！");
			return;
		}
		html.append(" <form method='post' action='/vansky/servlet/GetContent'>");
		
		html.append("<label for='all' class='label'><input type='checkbox' onchange='chooseAll()' id='all'>All</label>" +
				"<input type='submit' onclick=' showPopup()' class='button' value='submit'/><ul>");
		int j = 0;
		for (String source : sourceLink) {
			
			rule = ruleXmlParser.getRuleBySource(source);
			
			Selector selector = selectorXmlParser.getSelectorBySource(rule
					.getSource());
			try {

				Document doc = null;
			//	while (doc == null)
					doc = Jsoup.connect(rule.getUrlFilter()).timeout(3000)
							.get();
				
				//System.out.println(doc);
				// System.out.println("doc"+doc);
				// 和jquery语法类似 找到class
				Elements links = doc.select(selector.getLink());
				

				for (Element link : links) {
					// 取得链接
					String linkHref = "";
					if (link.attr("href").indexOf("http://") != -1) {
						linkHref = link.attr("href");
					} else {
						linkHref = rule.getUrl() + link.attr("href");
					}
					if (linkHref.equals(previousLink))
						continue;
					previousLink = linkHref;

					// 取得title内容
					String linkText = "";
					if (link.attr("title") == "")
						linkText = link.text();
					else
						linkText = link.attr("title");
					if (linkText.length() <= 6)
						continue;
					if (linkText.equals(previousTitle))
						continue;
					previousTitle = linkText;

					// 拼接字符串
					html.append("<li><input type='checkbox' id=" + j
							+ " name='" + rule.getSource() + "' value="
							+ linkHref + ">" + "<label for=" + j + ">"
							+ linkText + "</label><a href='"+linkHref +"' target='_blank'>访问</a><span style='float:right'>"
							+ rule.getWebName() + "</span></li>");
					j++;
				}
			} catch (Exception e) {
				// 异常
			}
		}

		html.append(" </ul><input type='submit' onclick=' showPopup()' class='button' value='submit'/><input type='reset' class='button' value='reset'/></form>");
		out.println(html);
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
