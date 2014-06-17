package com.news.release;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.news.model.NewsBean;
import com.news.service.NewsService;

public class NewsEdit extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");

		response.setCharacterEncoding("utf-8");
		NewsBean news = new NewsBean();
		NewsService newservice = new NewsService();
		String id = request.getParameterValues("id")[0];
		String title = request.getParameterValues("title")[0];
		request.getAttribute("id");
		String content = request.getParameterValues("content")[0];
		
		news.setContent(content);
		news.setTitle(title);
		newservice.updateNewsById(Integer.parseInt(id), news);

		
		this.getServletConfig().getServletContext()
		.getRequestDispatcher("/edit.jsp?id="+id).forward(request, response);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}
