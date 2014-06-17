// JavaScript Document

function showPopup(){
		 $(".popup").show("fast");
	}
	$(function(){
		
		
		//submit sources list
		$("#done").click(function(){
		var arr = new Array();
			$("ul li :checkbox:checked").each(function(){
			 	arr.push($(this).attr("id"));
			 });
			
			 if(arr.length==0) return;
			 showPopup();
			 $.post("servlet/GetList",{
			 "sourceLink[]":arr
			 },
			  function(data){
				 $(".popup").hide("fast");
				$(".result").html(data);	
			  });
		  });
	});
	
	//choose all sources
	function chooseAll(){
		if($("#all").is( ':checked' ))
			$("ul li input").prop("checked",true);
		else
			$("ul li input").prop("checked",false);	
	}
	// go to target page
 	function gotopage(pagenum,action){
 		var url  ="";
 		if(action =="search"){
 			url = "servlet/NewsSearch";
 		}else if (action = "release" ){
 			url = "./"+action+".jsp";
 		}
    	 	$.post(url,
			 {currentPage:pagenum,
    	 		key: $("#search_news").val()},
			 function(data){
				 $(".result").html(data);	
	 		});
    	 
     }
 	// prev page and next page
     function turnpage(offset,action){
    	 var url  ="";
  		if(action =="search"){
  			url = "servlet/NewsSearch";
  		}else if (action = "release" ){
  			url = "./"+action+".jsp";
  		}
    	 var currentpage=parseInt($("#currentPage").val());
    	 var totalPages = parseInt($("#totalPages").val());
    	 currentpage += offset;
    	 if(currentpage<=0) {currentpage =1; return;}
    	 if(currentpage > totalPages ) {currentpage = totalPages;return;}
    	 $("#currentPage").val(currentpage);
    	 if(currentpage>0&&currentpage<=totalPages){
    		$.post(url,
			 {currentPage:currentpage,key: $("#search_news").val()},
			 function(data){
				 $(".result").html(data);	
	 		});
    	 }
     }	 
     
     // prevent enter key 
	function enterkeyprevent(){
		
		$("#search_news").keypress(function(event){
		
			if( event.which == 13 ) {
   			  event.preventDefault();
   				searchNews();
 				 }
			});
	}
	function showPopup(){
		 $(".popup").show("fast");
	}
	//menu
	 function pagechange(name){
		  	$(".right").load("./"+name+".jsp",function(responseTxt,statusTxt,xhr){
	    	if(statusTxt=="error")
	      	alert("Error: "+xhr.status+": "+xhr.statusText);
	  		});
		  }
	 
	//edit news
	function modify(id){	 
	 	$.get("./edit.jsp",
		 {id:id},
		 function(data){
			
			 $(".result").html(data);	
			 replaceTextarea();
		 });
   
	}
	
	//deletle news	
	function deleteNews(id){	 
	 	$.get("servlet/NewsDel",
		 {id:id},	 
		 function(data){
			 $(".result").html(data);	
		 });
	}
	
	//search news by keyword
	function searchNews(){	
		
		var keyword =$("#search_news").val();
		if(keyword == "")return;
	 	$.post("servlet/NewsSearch",
		 {key:keyword},
		 
		 function(data){
			 $(".result").html(data);	
		 });
	}

	
	function view(){
		document.f.action="./showpage.jsp";
		document.f.submit();
		
	}
	function saveBack(){
		var text = CKEDITOR.instances.content.getData();
		$("#content").text(text);
		$.post("servlet/NewsEdit",
		
		$("#f").serialize(), 
		function(data,status){
			if(status=="success"){
				$(".result").html(data);
				replaceTextarea();
				$("#saved").show("fast",function(){
					setTimeout(function(){$("#saved").hide("slow");}, 2000);
				});
			}
			else if(status=="error"){
				
				$("#unsaved").show("fast",function(){
					setTimeout(function(){$("#unsaved").hide("slow");}, 2000);
				});
			}
		
		});
	}
	function replaceTextarea(){
		 CKEDITOR.replace('content');     
	}
	function releaseNews(){
		$.post("servlet/NewsRelease",
		{id:$("#id").val()},
		
		function(data,status){
			if(status=="success"){
				alert("发布成功！");
				$(".result").html(data);

			}
			else if(status=="error"){
				alert("发布失败！");
			}
		
		}
		);
	}