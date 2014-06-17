package com.news.release;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.news.service.NewsService;

public class NewsDel extends HttpServlet {

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
		newservice.deleteNewsById( Integer.parseInt(id));
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		request.setAttribute("status", "删除成功");
		request.setAttribute("action", "delete");
		this.getServletConfig().getServletContext()
		.getRequestDispatcher("/release.jsp").forward(request, response);
	}

}
