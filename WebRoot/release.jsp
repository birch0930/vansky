
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page  import="com.news.service.*" %>
<%@ page  import="com.news.model.*" %>
<%@ page  import="java.sql.*" %>
<%! 
	final static int PAGESIZE = 20;
	final static  int PAGE_BAR_SIZE = 5;
	static int from = 1 ;

	%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String[] str = {"草稿","撤回","已发布"}; //"草稿" 0,"撤回" 1,"已发布" 2};
String action = (String)request.getAttribute("action");

int pageBar;
int currentPage;
int to = from + PAGE_BAR_SIZE-1;

/**
String status = request.getParameter("status");
if(action.equals("release")){
	
}else if(action.equals("delete")){
	
}
*/
String strPageNo = request.getParameter("currentPage");
if(strPageNo== null || strPageNo.equals("")){
	currentPage = 1;
}else currentPage = Integer.parseInt(strPageNo);

	
    int totalRecords = 0;
	NewsService service = new NewsService();
	
 	List<NewsBean> list =null;

	 list = service.getNewsSet(currentPage,PAGESIZE);
	 totalRecords = service.getTotalNewsNum();
	 
	
 	int totalPages = totalRecords % PAGESIZE ==0 ? totalRecords / PAGESIZE :totalRecords/ PAGESIZE +1;
 	if(currentPage>totalPages) currentPage =totalPages; 
 	
 	
 	 
%>





 <div class="result">
 <form  class="pure-form">
   <fieldset>
      <legend>新闻发布</legend>
 	 
		    
		<input id="search_news" name="search_news" onkeydown="enterkeyprevent()"  placeholder="输入关键字" type="text"  maxlength="128" />
		<input type="button" class="button" onclick="searchNews()"  value="查找"> 
		            </fieldset>
</form>
<% 
  if(totalRecords == 0){
	out.println("未找到相应记录。");
	return; 
	} %>
<div id="showTable">

	<table class="pure-table">
    <thead>
        <tr>
            <th></th>
            <th><center>题目</center></th>
            <th><center>修改状态</center></th>
            <th><center>操作</center></th>
        </tr>
    </thead>
<form action="" method="get" id="releaseform" name="releaseform"><input type="hidden"  name="passid" >
    <tbody style="font-weight:normal">
    	<%     	  	
    	for(NewsBean news :list){     	   		
    	%>
        <tr>        		
            <td><input type='checkbox' value="<%= news.getNewsId() %>" name="id"  id="<%= news.getNewsId() %>" ></td>
            <td><label for="<%= news.getNewsId() %>"><%= news.getTitle() %></label></td>
            <td><center><%= str[news.getStatus()] %></center></td>
			<td><a href="javascript:void(0); id="preview" onclick="previewNews(<%= news.getNewsId() %>)" >预览</a>
			<a href="javascript:void(0);" onclick="modify(<%= news.getNewsId() %>)" >编辑</a>
			<a href="javascript:void(0);"  onclick="revokeNews(<%= news.getNewsId() %>)">撤回</a>
			<a href="javascript:void(0);"  onclick="deleteNews(<%= news.getNewsId() %>)">删除</a></td>
        </tr>
	<% }%>
    </tbody>
</table>  

<!-- 分页  -->
		<div>		
		 <ul class="pagination pagination-sm" >
			<li><a href="javascript:void(0)" onclick="javascript:turnpage(-1,'release')">&laquo;</a></li>
			<%  
				
				
				if( currentPage  > to ) {
				from = currentPage;
				
				} 
				to = from + PAGE_BAR_SIZE-1;  
				if( currentPage  < from  ) {
				to = currentPage;
				from =to - PAGE_BAR_SIZE+1;
				}
				 
				if(to > totalPages) to = totalPages;
				if(from  < 1) {from = 1; to = from + PAGE_BAR_SIZE-1;  }
				int i = from ;
				
				while (i <=to){  
					
			%>
							 
				<li id="page<%= i %>"><a href="javascript:void(0)"   <%= (currentPage == i )? "style='color:#fff; background-color: #428bca;'":""%> onclick="javascript:gotopage(<%= i %>,'release')"><%= i %></a></li>
							 
						   
			<% i++; 
		
			}%>	      
						 
						 
			<li><a href="javascript:void(0)" onclick="javascript:turnpage(1,'release')">&raquo;</a></li>
		</ul>
			<input id="totalPages" name="pagination.totalPages" type="hidden" value="<%= totalPages %>"/>
					
			<input id="currentPage" name="pagination.currentPage" type="hidden" value="<%= currentPage %>"/>
		</div>
<!-- 分页结束  -->
<input type='button' class="button" id="release" value="Release" onclick="releaseMultipleNews()">
</form>

	</div>		

</div>