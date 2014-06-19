package com.news.release;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.news.model.NewsBean;
import com.news.service.NewsService;

public class NewsRevoke extends HttpServlet {

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
		
		response.setContentType("text/html");
		
		NewsService newservice = new NewsService();
		String id = request.getParameter("id");
		
		if(id ==null || id.equals("")){
			request.setAttribute("status", "error");
			return;
		}
		try{
		NewsBean news = newservice.readNewsById(Integer.parseInt(id));
		if(news.getStatus() == 2)
		newservice.newsRevoke( Integer.parseInt(id));
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		request.setAttribute("status", "succuss");
		request.setAttribute("action", "revoke");
		this.getServletConfig().getServletContext()
		.getRequestDispatcher("/release.jsp").forward(request, response);
	}

}
