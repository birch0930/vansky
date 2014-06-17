<!doctype html>
<%@ page language="java"  contentType="text/html;charset=UTF-8" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
<meta charset="utf-8">
    <base href="<%=basePath%>">
    
    <title>新闻信息系统</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" type="text/css" href="css/css.css">
	<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.5.0/pure-min.css">
	<script src="http://code.jquery.com/jquery-1.11.1.js"></script>
 	<script src="js/main.js"></script>
 	<script type="text/javascript" src="ckeditor/ckeditor.js" charset="utf-8"></script>
 	
 	
</head>
  	
  <body>
 
  
  <div class="container">
  <img src="img/logo.gif" class="logo"/>
  <header>新闻信息系统
  </header>
    <div class="left">
       <ul class="nav">
        <li><a href="index.jsp">新闻采集</a></li>
        <li><a href="javascript:void(0);" onclick="pagechange(name)" name="release">新闻发布</a></li>
        
      </ul>
    </div>
    <div class="right">
      <div class="result">
    
      <form  class="pure-form pure-form-stacked">
      <fieldset>
      <legend>新闻采集</legend>
		
      <label for="all" class="label"><input onchange='chooseAll()' type='checkbox' id="all">All</label>
      <ul>
        <li><label for="wenxuecity"><input type='checkbox' value="wenxuecity" id="wenxuecity">文学城</label></li>
        <li><label for="singtao"><input type='checkbox'  value="singtao" id="singtao">星岛日报</label></li>
        <li><label for="mingpao"><input type='checkbox' id="mingpao">明报</label></li>
        <li><label for="bcbay"><input type='checkbox' id="bcbay">温哥华港湾</label></li>
        <li><label for="gcpnews"><input type='checkbox' id="gcpnews">环球华网</label></li>
        <li><label for="ifeng"><input type='checkbox' id="ifeng">凤凰网</label></li>
        <li><label for="zaobao"><input type='checkbox' id="zaobao">联合早报</label></li>
        <li><label for="worldjournal"><input type='checkbox' id="worldjournal">世界新闻网</label></li>
        <li><label for="reuters"><input type='checkbox' id="reuters">路透</label></li>
        <li><label for="lahoo"><input type='checkbox' id="lahoo">乐活网</label></li>
        
      </ul>
      
      	<input type='button' class="button" id="done" value="Done">
      	
      	</fieldset>
      	</form>
      </div>
    </div>
  </div>
       <div class="popup">正在采集.... 请稍后</div>
     
  <footer></footer>
  </body>
</html>
