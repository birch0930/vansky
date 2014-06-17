package com.news.parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.net.URL;

import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.news.model.NewsBean;
import com.news.service.NewsService;
import com.rules.RuleXmlParser;
import com.selector.Selector;
import com.selector.SelectorXmlParser;

public class GetContent extends HttpServlet {
	private String source;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

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

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");

		PrintWriter out = response.getWriter();
		NewsService newsService = new NewsService();
		Enumeration<String> name = request.getParameterNames();
		String[] sourceArr = null;
		while (name.hasMoreElements()) {

			this.setSource(name.nextElement());
			sourceArr = request.getParameterValues(source);
			if (sourceArr == null) {

				out.println(" 抓取错误 ！");
				return;
			}
	
			for (String sourceUrl : sourceArr) {
			
				try{
				NewsBean news = getNewsByJsoup(sourceUrl);
				if (news != null)
				
					newsService.saveNews(news);
				}catch(HttpStatusException e){
					System.out.println("Page not found");
				}
				
			}
			
		}
		this.getServletConfig().getServletContext()
				.getRequestDispatcher("/index.jsp").forward(request, response);

	}

	/**
	 * 使用jsoup来对文档分析 获取目标内容所在的目标层 这个目标层可以是div，table，tr等等
	 */
	public NewsBean getNewsByJsoup(final String url) throws HttpStatusException {
		NewsBean news = new NewsBean();
		String imgNamePrefix = ""; // 新闻采集时间-系统时间
		String destinationFile = "E:\\apache-tomcat-7.0.53\\webapps\\vansky\\pics\\"
				+ source + "\\";
		String article = "";
		Document doc;
		RuleXmlParser ruleXmlParser = new RuleXmlParser();
		SelectorXmlParser selectorXmlParser = new SelectorXmlParser();
		Selector selector = selectorXmlParser.getSelectorBySource(source);
		news.setSourceUrl(url);

		try {
			
			doc = Jsoup.connect(url).timeout(3000).get();
			if (!doc.hasText())
				return null;
			news.setTitle(doc.select(selector.getTitleSelector()).text());
			String sourceTime = doc.select(selector.getSourceTimeSelector())
					.text();
			
			news.setSourceTime(sourceTime);

			Elements contentElements = doc
					.select(selector.getContentSelector());
			article = contentElements.toString();

			// 设置照片名字前缀
			imgNamePrefix = news.getSourceTime().replaceAll("\\D", "");

			Elements imgElements = null;

			if (selector.getImgSelector() != "") {
				imgElements = contentElements.select(selector.getImgSelector());
				int i = 1;
				for (Element e : imgElements) {
					String src =e.attr("src");
					String destinationFileName ="";
					String suffix = src.substring(src.lastIndexOf("."));
					destinationFileName = destinationFile + imgNamePrefix + i + suffix;
					news.getImgList().add(destinationFileName);
					if (e.attr("src").indexOf("http://") == -1)
						src = ruleXmlParser.getRuleBySource(source).getUrl() + e.attr("src");													
					saveImage(src, destinationFileName);
					article = article.replace(e.attr("src"),
							"http://localhost:8080/vansky/pics/" + source + "/"
									+ imgNamePrefix + i + suffix);
					
					i++;
				}
			}
			news.setContent(article);
			news.setSourceWebsite(source);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return news;
	}

	/**
	 * 
	 * @param imageUrl
	 * @param destinationFile
	 * @throws IOException
	 */
	public void saveImage(String imageUrl, String destinationFile)
			throws IOException {
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		File file = new File(destinationFile);
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}

}
