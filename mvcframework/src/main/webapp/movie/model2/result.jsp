<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>당신이 선택한 영화에 대한 결과 메세지</h3>
	<%
		// jsp의 내장객체(request, response, session, out등이 있음) => jsp도 서블릿!!
		String msg=(String)request.getAttribute("msg");
	%>
	<%=msg%>
</body>
</html>