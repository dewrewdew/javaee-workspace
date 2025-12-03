<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import = "com.ch.model1.repository.Member2DAO" %>
<%@ page import = "com.ch.model1.dto.Member2" %>
<%@ page import="java.util.List" %>
<%!
	Member2DAO dao = new Member2DAO();
%>
<% 
	System.out.println("클라이언트의 요청 감지"); 
	// Tomcat로그에 출력되지만 우리의 경우 이클립스 내부의 톰캣이므로, 이클립스 콘솔에 출력됨
	
	// 파라미터 받기
	request.setCharacterEncoding("utf-8"); // 파라미터가 꺠지지 않도록 인코딩 지정
	
	String id = request.getParameter("id");
	String name = request.getParameter("name");
	String email = request.getParameter("email");
	
	System.out.println("id="+id);
	System.out.println("name="+name);
	System.out.println("email="+email);
	
	// DTO에 모으기
	Member2 dto = new Member2();
	dto.setId(id);
	dto.setName(name);
	dto.setEmail(email);
	
	int result = dao.insert(dto);
	System.out.println(result);
	
	//입력 성공 후 페이지 보여주기
	// 아래와 같이 비동기 요청에 대해, 응답 정보로서 페이지 접속을 일으키는 코드를 작성하게 되면
	// 클라이언트의 브라우저가 지정한 URL로 재접속을 시도하기 때문에, 그 재접속의 결과인 HTML을 서버로부터 받게되고
	// HTML을 전송받은 브라우저는 해당 HTML을 화면에 렌더링 해버리므로, 새로고침 효과가 나버린다.
	// 즉, without reloading 기능이 상실됨
	// response.sendRedirect("/ajax/main.jsp");
	
	// 해결책 - 서버에서는 화면 전체를 보내지 말고, 순수하게 목록 데이터만을 전송해주면 클라이언트는 그 데이터를 js로 동적 처리
	
	// 게시물 목록 가져오기
	List<Member2> list=dao.selectAll();
	
	
	// Dog d = new Dog();
	// System.out.println(d); => dog의 주소값을 반환함 아래 함수가 이렇게 됨.
	// out.print(list);
	// 클라이언트에게 목록 데이터 보낼 때 어차피 모두 문자열로 밖에 보낼 방법이 없다.
	// 하지만, 이 문자열을 넘겨받은 클라이언트의 브라우저의 자바스크립트는 아래와 같은 문자열로 구성되어 있을 경우
	// 원하는 데이터를 추출하기가 많이 불편하다.
	// 참고로, 아래와 같은 형식은 강의 편의상 전송문자열의 예를 보여주기 위함이였기 때문에,
	// 또 다른 개발자들에 의해서 저 아래의 데이터 형식은 임의로 바뀔 수 있다.
	// 문제점) 앞으로 우리는 Rest API를 다룰 것 이므로, 추후 REST 서버를 구축하여 우리의 서버에 요청을 시도하는 다양한 종류의
	// 클라이언트(스마트폰, 웹 브라우저, 자동차, 로봇)들에게 데이터를 제공해줄 예정인데, 이때 사용할 데이터 형식은 전세계적으로 xml(요즘은 잘 안씀) 또는 json이 압도적이다.
	
	// 해결책? 전세계 개발자들이 주로 사용하는 표준 형식의 데이터를 사용하자(추천 - JSON이 압승!)
	// JSON이란? 문자열 내의 데이터가 유달리 자바스크립트의 객체 리터럴 정의 기법을 따르는 경우 JSON문자열이라고 칭함.
			
	// 아래의 JSON 문자열은 말 그대로 문자열이므로, java는 그냥 String으로 처리한다.
	StringBuffer data = new StringBuffer();
	data.append("{");
	data.append("\"name\" : \"슬\", ");
	data.append(" \"email\":\"google\" ");
	data.append("}");
	
	System.out.println(data.toString());
	
	out.print(data.toString()); // 클라이언트인 웹 브라우저에게 보내기
	
%>