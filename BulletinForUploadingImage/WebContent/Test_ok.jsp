<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="javax.servlet.jsp.tagext.TryCatchFinally"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="java.io.File"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%

	// request.getParameter("userName"); // null
	// ! 파일은 바이너리 형태로 수신하기 때문에 일반적으로..하는거랑 다르다

	// String root = request.getRealPath("/");	//-- 예전에 사용하던 방식
	String root = pageContext.getServletContext().getRealPath("/");

	String savePath = root + "pds" + "\\" + "saveFile";
	//! 파일이 저장되는 경로
	//  C:\springMVC\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\FileSystemApp07\pds\saveFile
	
	File dir = new File(savePath);
	
	if(!dir.exists())	//! 경로가 없다면
		dir.mkdirs();	//  경로 만들기
	
	String encType = "UTF-8";		//-- 인코딩 방식
	int maxFileSize = 5*1024*1024;	//-- 파일 업로드 크기(5MB)
	
	MultipartRequest multi = null;
	
	try
	{
		multi = new MultipartRequest(request, savePath, maxFileSize, encType, new DefaultFileRenamePolicy());
		//! 	-------------------	 --------									  ------------------------- 
		//!		갈아져서 넘어오는	 갈아져서 넘어오는(바이너리 형태의)   		  파일의 이름을 지정하는 정책
		//!		request를 합쳐서	 request → 헤더의 정보를 보고 합칠 수 있다.
		//!		정보를 얻을 수
		//!		있는 컴포넌트
		
		out.println("작성자 : " + multi.getParameter("userName") + "<br>");
		out.println("제목 : " + multi.getParameter("subject") + "<br>");
		out.println("서버에 저장된 파일 이름 : " + multi.getFilesystemName("uploadFile") + "<br>");
		out.println("업로드한 파일 이름 : " + multi.getOriginalFileName("uploadFile") + "<br>");
		out.println("파일 타입 : " + multi.getContentType("uploadFile") + "<br>");

		File file = multi.getFile("uploadFile");
		
		if(file != null)
		{
			out.println("파일 크기 : " + file.length() + "<br>");
		}
		
	}
	catch(Exception e)
	{
		System.out.println(e.toString());
	}
	
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Test_ok.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
<style type="text/css">
img
{
	height: 30%;
}
</style>
</head>
<body>

<div>
	<h1>업로드 결과</h1>
	<hr>
</div>


<div>
	<a href="<%=cp%>/pds/saveFile/<%=multi.getFilesystemName("uploadFile")%>">test</a>
</div>

<div>
	<img class="photo" src="<%=cp%>/pds/saveFile/<%=multi.getFilesystemName("uploadFile")%>">
</div>



</body>
</html>