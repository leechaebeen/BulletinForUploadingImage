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
<title>Member.jsp</title>
<link rel="stylesheet" type="text/css" href="<%=cp %>/data/css/member/member.css">
<style type="text/css">
	*
	{
		margin: 0px;
		padding: 0px;
	}
	
	body
	{
		font-size: 9pt;
		font-family: 굴림;
	}
	
	a
	{
		cursor: pointer;
		color: #000000;
		text-decoration: none;
		font-size: 9pt;
		line-height: 150%;
	}
	
	a:hover, a:active
	{
		color : #F28011;
		text-decoration: underline;
	}
	
	td
	{
		font-size: 9pt;
	}	
	
	.btn1
	{
		font-size: 9pt;
		color: rgb(0,0,0);
		background-color: rgb(245, 245, 245);
		line-height: 16px;
		padding: 1px 3px 1px 3px;
	}
	
	.boxTF
	{
		border : 1px solid gray;
		height: 15px;
		margin-top: 3px;
		padding: 2px;
		border-color: #666666;
		background-color: #ffffff;
		font-family: 굴림;
		font-size: 9pt;   
	}
	
	.selectField
	{
		border: 1px solid gray;
		border-color: #666666;
		background-color: #ffffff;
		font-family: 굴림;
		font-size: 9pt;
		height: 22px; 
		
	}
	
</style>

<script type="text/javascript">
	
	function registerOk() 
	{
		f = document.memberForm;
		str = f.userId.value;
		
		if(!str)
		{
			alert("\n아이디를 입력하세요.");
			f.userId.focus();
			return;
		}
		
		if(str.length<4)
		{
			alert("\n아이디는 4글자 이상 입력하세요.");
			f.userId.focus();
			return;
		}	
		
		str = f.userPwd.value;
		if(!str)
		{
			alert("\n패스워드를 입력하세요.");
			f.userPwd.focus();
			return;
		}
		
		if(str.length<4)
		{
			alert("\n패스워드는 4글자 이상 입력하세요.");
			f.userPwd.focus();
			return;
		}
		
		str = f.tel1.value;
		if(!str)
		{
			alert("\n전화번호를 입력하세요.");
			f.tel1.focus();
			return;
		}
		
		str = f.tel2.value;
		if(!str)
		{
			alert("\n전화번호를 입력하세요.");
			f.tel2.focus();
			return;
		}
		
		str = f.tel3.value;
		if(!str)
		{
			alert("\n전화번호를 입력하세요.");
			f.tel3.focus();
			return;
		}
		
		str = f.email1.value;
		if(!str)
		{
			alert("\n이메일을 입력하세요.");
			f.email1.focus();
			return;
		}
		
		str = f.email2.value;
		if(!str)
		{
			alert("\n이메일을 입력하세요.");
			f.email2.focus();
			return;
		}
		
		//	※	이전의 요청 페이지로부터 넘어온 특정 변수를 확인하여
		//		그 변수의 값에 따라 입력폼 또는 수정폼으로 활용할 수 있도록 처리
		
		// 특정 변수에 입력된 수신 내용
		str = "${mode }";
		
		if(str == "created")
		{
			f.action = "<%=cp%>/join/created_ok.action";
		}
		else if(str == "update")
		{
			f.action = "<%=cp %>/join/update_ok.action";
		}
		
		f.submit();
		
	}
	
	function onlyNum()
	{
		if (event.keyCode < 48 || envent.keyCode > 57)
		{
			event.returnValue = false;
		}	
	}
	
	function changeEmail() 
	{
		var f = document.memberForm;
		
		var str = f.selectEmail.value;
		// alert(str);
		
		if (str != "direct") 
		{
			f.email2.value = str;
			f.email2.readOnly = true;
			f.email1.focus();
		}
		else
		{
			f.email2.value = "";
			f.email2.readOnly = false;
			f.email1.focus();
		}
			
	}

</script>

</head>
<body>

<jsp:include page="/menu/top.jsp"></jsp:include>

<div id="member">
	
	<div id="member_title">
		<!-- mode → "created" / "update" --><!-- !두 모드를 받아서..모드에 따라 다르게 보이도록 처리 -->
		<c:if test="${mode == 'created' }">
		회 원 가 입
		</c:if>
		<c:if test="${mode == 'update' }">
		회 원 수 정
		</c:if>
	</div><!-- close div#member_title -->

	<form name="memberForm" method="post">
		<div class="memberCreated_noLine">
			<ul style="margin: 0px 0px 0px 20px; padding: 0px; list-style-type: square;">
				<c:if test="${mode == 'created' }">
				<li>회원 정보 입력</li>
				</c:if>
				<c:if test="${mode == 'update' }">
				<li>회원 정보 수정</li>
				</c:if>
			</ul>		
		</div>	
		
		<div id="memberCreated">
		
	        <div class="memberCreated_bottomLine">
	           <dl>
	              <dt>아이디</dt>
	              <dd>
	                 <input type="text" name="userId" id="userId" 
	                 size="25" maxlength="10" class="boxTF" 
	                 value="${dto.userId }" 
	                 ${modd=="update" ? "readonly='readonly'" : "" }>               
	              </dd>
	           </dl>
	        </div>
	        
	        <div class="memberCreated_bottomLine">
	           <dl>
	              <dt>패스워드</dt>
	              <dd>
	                 <input type="password" name="userPwd" id="userPwd"
	                 size="25" maxlength="20" class="boxTF"
	                 value="${dto.userPwd }"
	                 ${mode=="update" ? "readonly='readonly'" : "" }>
	              </dd>
	           </dl>
	        </div>

			<div class="memberCreated_bottomLine">
				<dl>
					<dt>이름</dt>
					<dd>
						<input type="text" name="userName" id="userName"
							size="25" maxlength="20" class="boxTF"
							value="${dto.userName }"
							${mode=="update" ? "readonly='readonly'" : "" }>
					</dd>
				</dl>
			</div>
	        
	        <div class="memberCreated_bottomLine">
	           <dl>
	              <dt>전화번호</dt>
	              <dd>
	                 <select name="tel1" class="selectField">
	                    <option value="">선 택</option>
	                    <option value="010"
	                    ${dto.tel1=="010" ? "selected='selected'" : "" }>010</option>
	                    <option value="070"
	                   ${dto.tel1=="070" ? "selected='selected'" : "" }>070</option>
	                </select>
	   				
	   				-
	   				
	   				<input type="text" name="tel2" size="5"
	   				 maxlength="4" class="boxTF" value="${dto.tel2 }"
	   				 onkeypress="onlyNum()">
	   				 <!-- !사전에 차단해야하니까 onkeyup이 아닌 onkeypress -->
	   				 
	   				-
	   				
	   				<input type="text" name="tel3" size="5"
	   				 maxlength="4" class="boxTF" value="${dto.tel3 }"
	   				 onkeypress="onlyNum()">
	   				  	  
	              </dd>
	           </dl>
	        </div>
	        
	        <div class="memberCreated_noLine">
	     				<dl>
	     					<dt>E-Mail</dt>
	     					<dd>
	     						<select name="selectEmail" class="selectField" onchange="changeEmail()">
	     							<option value="">선 택</option>
	     							<option value="gmail.com"
	     							${dto.email2== "gmail.com" ? "selected='selected'" : "" 
	     							}>gmail.com</option>
	     							<option value="hanmail.net"
	     							${dto.email2== "hanmail.net" ? "selected='selected'" : "" 
	     							}>hanmail.net</option>
	     							<option value="naver.com"
	     							${dto.email2== "naver.com" ? "selected='selected'" : "" 
	     							}>naver.com</option>
	     							<option value="direct">직접입력</option>
	     						</select>
	     						<input type="text" name="email1" size="13"
	     							maxlength="30" class="boxTF" value="${dto.email1 }">@
								<input type="text" name="email2" size="13"
	     							maxlength="30" class="boxTF" value="${dto.email2 }"
	     							readonly="readonly">
	     					</dd>
	     				</dl>
	     			</div> 
	        
	     </div><!-- close div#memberCreated -->
	     
	     <div id="memberCreated_footer">
	     	<input type="button" value="등록하기" class="btn1"
	     		onclick="registerOk()">
	     	<input type="reset" value="다시입력" class="btn1"
	     		onclick="document.memberForm.userId.focus();">	
	     	<input type="button" value="등록취소" class="btn1"
	     		onclick="javascript:location.href='<%=cp %>'">
	     	
	     </div><!-- close div#memberCreated_footer -->
	     
	</form>

	<div id="memberCreated_message">
		<span style="color: blue;">${msg }</span>
	</div>

</div><!-- close #member -->

</body>
</html>