<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix ="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TDlist</title>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        
        <style>
            #top{
                position: absolute;
                background-color: #eae9e9;
                width: 1000px;
                height: 100px;
                text-align: center;
                border-radius: 10px;
                top: 15%;
                left: 20%;
                
            }
            #top a{
                position: absolute;
                left: 94%;
                top: 60%;
            }
            #top a img{
            	position: absolute;
            	top: 50%;
                height: 30px;
                width: 50px;
            }
            #list{
                position: absolute;
                background-color: #eae9e9;
                width: 1000px;
                height: auto;
                padding: 10px;
                margin-top: 20px;
                text-align: center;
                border-radius: 10px;
                top: 35%;
                left: 20%;
            }
            #list th,td{
                background-color: white;
                margin: 5px;
                width: 900px;
                padding: 5px;
                border-radius: 10px;
                list-style-type: none;
            }
            #list th{
            	background-color: #d8fae6;
            }
             #subject:hover{
            	background-color: #d8fae6;
            }
            .search {
                position: absolute;
                background-color: #eae9e9;
                width: 1000px;
                height: 50px;
                text-align: center;
                border-radius: 10px;
                top: 30%;
                left: 20%;
            }
            .search input{
            	border:0;
                text-align: center; 
                margin: 5px;  
                width: 800px;
                height: 20px;
                border-radius: 10px;
            }
            .search button{
            	background-color: rgb(246, 247, 248);
           		border: 0;
           		outline: 0;
            	width: 50px;
            	height: 30px;
            	border-radius: 10px;
            	padding: 5px;
            }
            .search button:hover{
            	background-color: #d8fae6;
            	font-weight: 600;
            	cursor: pointer;

            }
			#paging{
	            text-align: center;
	         }    
	         #list a:link,a:visited{
	            text-decoration: none;
	            color:black;
	            font-size:20px;
	         }
	         #d{
	            font-size:20px;
	         }
	         #paging{
	            position:absolute;
	            left: 50%;
	            top: 92%;
	         }
	         a{
	         	text-decoration: none;
	         }
	         #list .id{
	         	width: 120px;
	         }
	         #write{
	         	background-color: rgb(246, 247, 248);
	         	border-radius: 10px;
	         	padding: 4px;
	         	font-size: 14px;
	         }
	         #write:hover{
	         	background-color: #d8fae6;
	         	font-weight: 600;
            	cursor: pointer;
	         }
	         .back{
	         	position: absolute;
	         	background-color: rgb(246, 247, 248);
	         	border-radius: 30px;
	         	width: 1500px;
	         	height: 800px;
	         	left: 5%;
	         }

        </style>
    </head>
    <body>
  <%@ include file="navi.jsp" %>
	   <div class="back"></div> 
       <div id="top">
           <h1>Travel Diary</h1>
           <a id="write" href="tdWriteForm">&nbsp;글쓰기&nbsp;</a>
       </div>
       <div class="search">
            <input type="text" placeholder="검색어를 입력해주세요.">
            <button>검색</button>
       </div>
       <div>
       	<table id="list">
			<tr>
				<th class="id">ID</th>
				<th>Subject</th>
			</tr>
			<c:forEach items="${diaryList}" var="dto">
				<tr>
					<td class="id">${dto.id}</td>
					<td id="subject"><a href="tdDetail?idx=${dto.diary_number}">${dto.diary_subject}</a></td>
				</tr>
			</c:forEach>
		</table>
       </div>
       
        <div id="paging" style="display: block; text-align: center;">		
		<c:if test="${paging.startPage != 1 }">
			<a href="./tdList?nowPage=${paging.startPage - 1 }&cntPerPage=${paging.cntPerPage}">이전&nbsp;</a>
		</c:if>
		<c:forEach begin="${paging.startPage }" end="${paging.endPage }" var="p">
			<c:choose>
				<c:when test="${p == paging.nowPage }">
					<b id="d">${p }</b>&nbsp;
				</c:when>
				<c:when test="${p != paging.nowPage }">
					<a href="./tdList?nowPage=${p }&cntPerPage=${paging.cntPerPage}">${p }&nbsp;</a>
				</c:when>
			</c:choose>
		</c:forEach>
		<c:if test="${paging.endPage != paging.lastPage}">
			<a href="./tdList?nowPage=${paging.endPage+1 }&cntPerPage=${paging.cntPerPage}">&nbsp;다음</a>
		</c:if>
	</div>	
	
    </body>
 
    <script>
     
    var msg = "${msg}";
    if(msg!=""){
    	alert(msg);
    }
        
    </script>
</html>