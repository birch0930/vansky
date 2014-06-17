package com.news.release;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.news.model.NewsBean;
import com.news.service.NewsService;

public class NewsRelease extends HttpServlet {

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
		doPost(request,response);
		
		
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

		NewsService newservice = new NewsService();
		String id = request.getParameter("id");
		
		if(id ==null || id.equals("")){
			request.setAttribute("status", "Error");
			return;
		}	
		try{
		newservice.newsRelease(Integer.parseInt(id));
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		request.setAttribute("action", "release");
		request.setAttribute("status", "发布成功");
		this.getServletConfig().getServletContext()
		.getRequestDispatcher("/release.jsp").forward(request, response);
	}

}
