<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.ch.memberapp.member.Member" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	홈페이지 메인
	<pre>
		jsp에서는 필수적으로 사용되는 javaEE기반의 객체들을 미리 메모리에 올려놓고 이름까지 지정해놓았는데
		이러한 시스템에 의해 결정된 내장되어있는 객체들을 가리켜 jsp내장(built-in) 객체라고 한다.
		따라서 변수명을 바꾸거나 할 수 없음
		지금은 회원 정보를 꺼내오기 위해서는 HttpSession 자료형에 들어있는 member를 꺼내야 하는데
		JSP에서는 HttpSession 자료형에 대한 내장 객체로 session이라는 내장객체를 지원함
	</pre>
	<%
		Member member=(Member)session.getAttribute("member");
		out.print(member.getName() + "님 반갑습니다.");
	%>
</body>
</html>