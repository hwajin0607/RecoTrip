<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
		<style>
		table, th, td{
			border: 1px solid black;
			border-collapse: collapse;
			padding: 5px 10px;
		}
		td{
			text-align: center;
		}
		div{
			margin: 5px;
		}
				
		</style>
	</head>
	<body>
		<!--<c:if test="${sessionScope.loginId != null}">-->
		<!--</c:if>-->
		<div>
			안녕하세요 ${sessionScope.loginId}님
			<button onclick="location.href='logout'">로그아웃</button>
		</div>
		<button onclick="location.href='ask_writeForm'">글쓰기</button>
	
		<table>
			<tr>
				<th>글 번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
			</tr>
			<c:forEach items = "${ask_list}" var = "dto">
			<tr>
				<td>${dto.ask_num}</td>
				<td><a href = "./ask_detail?ask_num=${dto.ask_num}">${dto.ask_subject}</a></td>
				<td>${dto.id}</td>
				<td>${dto.ask_reg_date}</td>
			</tr>
			</c:forEach>
			
			
		</table>
	</body>
	<script>
	
	</script>
</html>