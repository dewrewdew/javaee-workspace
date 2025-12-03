<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.ch.model1.repository.Member2DAO"%>
<%@ page import="com.ch.model1.dto.Member2"%>
<%!Member2DAO dao = new Member2DAO(); %>
<% 
	// 스크립틀릿
	// 이 jsp가 서블릿으로 변환될 때 service(request, reponse) 메서드 영역
	// 넘어온 파라미터를 받아서 mysql의 member2 테이블에 insert하자
	
	// jsp에서는 개발자가 요청 객체, 응답 객체를 별도로 변수명을 바꿀 수 없다.
	// 이유? 이미 결정되어있기 때문!(내장객체) built-in object => 고양이가 이미 갖고있는 객체
	request.setCharacterEncoding("utf-8");
	// reponse.setContentType은 맨 위에있으니까 안씀!
	String id = request.getParameter("id");
	String name = request.getParameter("name");
	String email = request.getParameter("email");
	
	// PrintWriter 조차도 이미 지원됨. 명칭은 out으로 정해져있다.
	out.print("id="+id+"<br>");
	out.print("name="+name+"<br>");
	out.print("email="+email+"<br>");
	
	// 낱개로 되어 있는 파라미터들을 전달하기 편하게끔 하나의 DTO로 모아서 전달하자
	Member2 dto = new Member2();
	dto.setId(id);
	dto.setName(name);
	dto.setEmail(email);
	
	int result = dao.insert(dto);
	
%>

<script>

<%if(result < 1) {%>
	out.print("실패입니다.");
<%} else { %>
	location.href="/ajax/main.jsp";
<%} %>

</script>


