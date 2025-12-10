<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		out.print(application.getAttribute("born"));
		out.print(session.getAttribute("id"));
		out.print(request.getAttribute("hobby"));
		
		/*
		request의 자료형? HttpServletRequest
		session의 자료형? HttpSession
		application의 자료형? ServletContext
		*/
		// 현재 웹애플리케이션내의 자원의 실제 OS상의 경로를 반환(현재 OS가 리눅스이면 리눅스 경로, 맥이면 맥의 경로, 윈도우면 윈도우 경로)
		String path = application.getRealPath("WEB-INF/servlet-mapping.txt");
		out.print(path);
	%>
</body>
</html>