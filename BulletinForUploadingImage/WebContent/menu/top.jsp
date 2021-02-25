<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>top.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
<link href="https://fonts.google.com/specimen/Black+Han+Sans#standard-styles" rel="stylesheet">
<style type="text/css">

*
{
	font-family: "Black Han Sans";
}

</style>
</head>
<body>

<div align="center">
	<table style="width: 800px;">
		<tr style="height: 30px;">
			<td style="width: 400px;" align="left">
				| <a href="<%=cp%>">메인</a>
				| <a href="<%=cp%>">게시판</a>
				| <a href="<%=cp%>">방명록</a>
				| <a href="<%=cp%>/image/list.action">사진첩</a>|		
			</td>
			
			<td style="width: 400px;" align="right">
				<!-- 
				홍길동 님
				<a href="">로그아웃</a>
				<a href="">정보수정</a>
				<a href="">회원탈퇴</a>
				 -->
				<c:if test="${!empty sessionScope.member }">
					${sessionScope.member.userName }님
					| <a href="<%=cp%>/join/logout.action">로그아웃</a>
					| <a href="<%=cp%>/join/update.action">정보수정</a>
					| <a href="<%=cp%>/join/delete.action">회원탈퇴</a>	
				</c:if>
				
				<c:if test="${empty sessionScope.member }">
					| <a href="<%=cp%>/join/login.action">로그인</a>
					| <a href="<%=cp%>/join/created.action">회원가입</a>	
				</c:if>
			</td>
		</tr>
	</table>
</div>

</body>
</html>