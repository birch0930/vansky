<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page  import="com.news.model.*" %>
<%@ page  import="com.news.service.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setCharacterEncoding("utf-8");
String id = request.getParameter("id");
String title = "";
String content = "";

if( id == null || id.equals("") ){
	title = request.getParameter("title");
	content = request.getParameter("content");
}else{
	NewsService service = new NewsService();
	NewsBean news = service.readNewsById(Integer.parseInt((id)));
	title = news.getTitle();
	content = news.getContent();
}

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title><%=title %></title>
    <meta charset="utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="css/css.css">
	

  </head>
  
  <body>
 	<div class="container showpage">
  		<%=title %>
  	<div class="content"><%=content %></div>
  	</div>
  </body>
</html>
