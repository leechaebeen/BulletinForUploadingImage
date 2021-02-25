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
<title>login.jsp</title>
<link rel="stylesheet" type="text/css" href="<%=cp %>/data/css/style.css">

<script type="text/javascript">

	function login() 
	{
		f = document.loginForm;
		
		if(!f.userId.value)
		{
			alert("\n아이디를 입력하세요.");
			f.userId.focus();
			return;
		}
		
		if(!f.userPwd.value)
		{
			alert("\n패스워드를 입력하세요.");
			f.userPwd.focus();
			return;
		}
		
		f.action = "<%=cp%>/join/login_ok.action";
		f.submit();
	
	}
</script>

</head>
<body>

<jsp:include page="/menu/top.jsp"></jsp:include>

<br><br>

<form action="" method="post" name="loginForm">
	<div align="center">
		<table style="width: 300px;">
			<tr style="height: 30px;">
				<td colspan="2" align="center">
					<span style="font-weight: bold;">로그인</span>
				</td>
			</tr>
			<tr style="height: 25px;">
				<td style="width: 80px;" align:"left">
					아이디
				</td>
				<td align="left">
					<input type="text" name="userId" class="boxTF" size="15">
				</td>
			</tr>
			<tr style="height: 25px;">
				<td style="width: 80px;" align:"left">
					패스워드
				</td>
				<td align="left">
					<input type="password" name="userPwd" class="boxTF" size="15">
				</td>
			</tr>
			<tr style="height: 25px;">
				<td colspan="2" align="center">
					<input type="button" value="로그인" class="btn2" onclick="login()">
					<input type="button" value="아이디/패스워드 찾기" class="btn2"
					onclick="">
				</td>
			</tr>
			<tr style="height: 30px;">
				<td colspan="2" align="center">
					<span style="color: blue;">${msg }</span>
				</td>
			</tr>			
			
		</table>
	</div>
</form>

</body>
</html>