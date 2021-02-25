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
<title>list.jsp</title>
<link rel="stylesheet" type="text/css" href="<%=cp %>/data/css/style.css">
<link href="https://fonts.google.com/specimen/Black+Han+Sans#standard-styles" rel="stylesheet">
<style type="text/css">

*
{
	font-family: "Black Han Sans";
}

</style>
<script type="text/javascript">
	
	function searchList() 
	{
		f = document.searchForm;
		f.action = "<%=cp%>/image/list.action";
		f.submit();
	}

</script>
</head>
<body>

<jsp:include page="/menu/top.jsp"></jsp:include>

<br></br>

<div align="center"> 
	<table style="width: 600px; border: 2px solid #D6D4D6; 
				margin: 0px; padding: 0px; align-self: center;">
		<tr style="height: 40px;">
			<td style="padding-left: 25px;">
				<span style="font-weight: bold;">이미지 게시판</span>
			</td>
		</tr>
	</table>
	
	<table style="width: 600px; margin: 0px; padding: 0px; align-self: center;">
		<tr style="height: 30px;">
			<td align="left" style="width: 50%;">
				<!-- Total 99 articles, 20pages / Now page is 12 --> 
				 Total ${dataCount } articles, ${totalPage }pages / Now page is ${currentPage } 
			</td>
			<td align="right">
				<input type="button" value="이미지 등록" class="btn2"
					onclick="javascript:location.href='<%=cp %>/image/created.action'">
			</td>
		</tr>
	</table>
	
	<table style="width: 600px; margin: 1px; padding: 3px;
	 				background-color: #FFFFFF; align-self: center;">
	 	<c:set var="n" value="0"></c:set>
	 	
	 	<c:forEach var="dto" items="${lists }">
	 		
	 		<c:if test="${n == 0 }">
	 		<tr>
	 		</c:if>
			
			<c:if test="${n !=0 && n%3 == 0 }">
			<tr>
			</c:if>
			
				<td style="width: 200px;" align="center">
					<img src = "${imagePath }/${dto.saveFileName}"
						style="width: 180px; height: 180px;">
					<br><br>
					<!-- 작성자 : 홍길동      2020-07-10 -->
					작성자 : ${dto.userName } &nbsp;&nbsp;&nbsp;&nbsp; ${dto.created } 
					<br>
					
					<span style="font-weight: bold;'">${dto.subject }</span>
					
					<c:if test="${sessionScope.member.userId == dto.userId }">
					<br>
					<a href="<%=cp %>/image/delete.action?num=${dto.num}&pageNum=${pageNum}">삭제</a>
										
					</c:if>
					
				</td>
				
				
				<c:set var="n" value="${n+1 }"></c:set>
							
	 	</c:forEach>
	
		<c:if test="${n>0|| n%3 != 0 }">
			<c:forEach var = "i" begin="${n%3+1 }" end="3" step ="1">
					<td>&nbsp;</td>
			</c:forEach>
		</c:if>		
		
		<c:if test="${n!=0 }">
			</tr>
		</c:if>
		
		<!-- 페이징 처리에 따른 인덱스 적용 -->
		<c:if test="${dataCount != 0 }">
			<tr style="height:30px; background-color: #FFFFFF">
				<td align="center" colspan="3">
					${pageIndexList }
				</td>
			</tr>
			
			<tr align="center" style="height: 30px;">
				<td colspan="3">
					
					<!-- 검색 폼 구성 -->
					<form name="searchForm" method="post">
						<select name="searchKey" class="selectField"
							style="height: 22px;">
							<option value="subject">제목</option>
							<option value="userName">작성자</option>	
						</select>
						
						<input type="text" name = "searchValue" class="boxTF" />
						<input type="button" value="검 색" class="btn2"
							onclick="searchList()">
					</form>
				</td>
			</tr>
		</c:if>

		<!-- IMAGEBOARD 테이블에 데이터가 존재하지 않을 경우 -->
		<c:if test="${dataCount == 0 }">
			<tr style="height: 30px; background-color: #FFFFFF;">
				<td align="center" colspan="3">
					등록된 자료가 존재하지 않습니다.
				</td>
			</tr>
		</c:if>
		
	</table>

	<table style="width: 600px; margin: 0px; padding: 0px; align-self: center;">
		<tr style="height: 3px;">
			<td style="background-color: #DBDBDB;">
			</td>
		</tr>
	</table>


</div >


</body>
</html>