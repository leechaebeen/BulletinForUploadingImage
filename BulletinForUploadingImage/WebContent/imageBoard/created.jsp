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
<title>created.jsp</title>
<link rel="stylesheet" type="text/css" href="<%=cp %>/data/css/style.css">
<script type="text/javascript" src="<%=cp%>/data/js/util.js"></script>
<script type="text/javascript">

	function sendIt() 
	{
		f= document.myForm;
		
		
		str = f.subject.value;
		str = str.trim();
		if(!str)
		{
			alert("제목을 입력하세요.");
			f.subject.focus();
			return;
		}
		
		str = f.upload.value;
		if(!str)
		{
			alert("이미지 파일을 선택하세요.");
			f.upload.focus();
			return;
		}	
				
		if(!isImageFile(str))
		{
			alert("이미지 파일만 업로드 가능합니다.");
			f.upload.focus();
			return;
		}
		
		f.action = "<%=cp%>/image/created_ok.action";
		f.submit();
		
	}
</script>
</head>
<body>

<jsp:include page="/menu/top.jsp"></jsp:include>

<br><br>

<div align="center">
	<table style="width: 560px; border: 2px solid #D6D4D6; 
			padding: 0px; margin: 0px; align-self: center;">
		<tr style="height: 40px;">
			<td style="padding-left: 25px;">
				<span style="font-weight: bold;">사 진 첩</span>
			</td>
		</tr>
	</table>
	
	<br><br>
	
	<form action="" method="post" name="myForm" enctype="multipart/form-data">
		
		<table style="width: 560px; margin: 0px; padding: 0px; align-self: center;">
			<tr style="height: 3px;">
				<td colspan="2" style="background-color: #DBDBDB; text-align: center; ">
				</td>
			</tr>
			<tr style="height: 30px;">
				<td style="width: 80px; background-color: #EEEEEE; padding-left: 20px;">
					작성자
				</td>
				
				<td style="width: 480px; padding-left: 10px; text-align: left;">
					<!-- 홍길동 -->
					${sessionScope.member.userName }
				</td>
			</tr>
			<tr style="height: 1px;">
				<td colspan="2" style="background-color: #DBDBDB">
				</td>
			</tr>
			<tr style="height: 30px;">
				<td style="width: 80px; background-color: #EEEEEE; padding-left: 20px;">
					제&nbsp;&nbsp;목
				</td>
				<td style="width: 480px; padding-left: 10px; text-align: left; ">
					<input type="text" name="subject" size="74" maxlength="100" class="boxTF">
				</td>
			</tr>
			<tr style="height: 1px;">
				<td colspan="2" style="background-color: #DBDBDB">
				</td>
			</tr>
			<tr style="height: 30px;">
				<td style="width: 80px; background-color: #EEEEEE; padding-left: 20px;">
					파&nbsp;&nbsp;일
				</td>
				<td style="width: 480px; padding-left: 10px; text-align: left;">
					<input type="file" name="upload" size="58" maxlength="100" class="boxTF"
					style="height: 20px;">
				</td>
			</tr>			
			<tr style="height: 3px;">
				<td colspan="2" style="background-color: #DBDBDB">
				</td>
			</tr>
		</table>

		<table style="width: 500px; margin: 0; padding:  3; align-self: center;">
			<tr style="height: 30px;" align="center">
				<td style="align-self: center;">
					<input type="button" value="등록하기" class="btn2"
						onclick="sendIt()">
					<input type="reset" value="다시입력" class="btn2"
						onclick="document.myForm.subject.focus();">
					<input type="button" value="작성취소" class="btn2"
						onclick="location='<%=cp%>/image/list.action'">
					
				</td>
			</tr>
		
		</table>

	</form>

</div>

</body>
</html>