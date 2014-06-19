<%@ page language="java"  contentType="text/html;charset=UTF-8" import="java.util.*" pageEncoding="utf-8"%>

<%@ page  import="com.news.model.*" %>
<%@ page  import="com.news.service.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	String id = request.getParameter("id");
 	NewsService service = new NewsService();
 	if(id == null || id.equals(""))return;
 	NewsBean news = service.readNewsById(Integer.parseInt((id)));
 	
%>
<base href="<%=basePath%>">


<div class="editor result">

<form action="" method="post" id="f" name="f" class="pure-form pure-form-stacked" target="_blank">
<fieldset>
<legend>新闻编辑</legend>

<input type="hidden" id="id" name="id" value="<%= news.getNewsId() %>">
<label for="title">题目:</label>
<input type="text" id="title" name="title" size="80" value="<%= news.getTitle() %>">
<label for="sourceTime">时间:</label>
<input type="date" id="sourceTime" name="sourceTime" size="80" value="<%= news.getSourceTime().toString() %>">
<div id="saved" class="hideDiv alert">保存成功</div>
<div id="unsaved" class="hideDiv alert">保存失败</div>
<label for="content">内容:</label>
<textarea id="content" name="content"><%= news.getContent() %></textarea>
<script type="text/javascript">
           replaceTextarea();     
	</script>
<input type='button' class="button" id="preview" value="Preview" onclick="previewNews()">

<input type='button' class="button" id="save" value="Save" onclick="saveBack()">
<input type='button' class="button" id="release" value="Release" onclick="releaseNews(<%= news.getNewsId() %>)">
</fieldset>

</form>

</div>
