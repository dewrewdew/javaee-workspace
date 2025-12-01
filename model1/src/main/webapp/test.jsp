<%@page import="org.apache.tomcat.jni.Pool"%>
<%@page import="javax.naming.InitialContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%
	// 톰캣에 JNDI로 설정해 놓은 커넥션 풀 사용해보기
	// 톰캣에 설정해 놓은 자원을 이름으로 검색
	InitialContext ctx = new InitialContext(); // JNDI 검색 객체
	DataSource pool = (DataSource)ctx.lookup("java:comp/env/jndi/mysql"); // java:comp/env는 무조건 와야됨. 반환형이 객체니까 (DataSource)형으로 형 변환
	
	Connection con = pool.getConnection(); // 풀에 들어있는 Connection 객체 꺼내기
	
	out.print("풀로부터 얻어온 커넥션 객체는 " +con);
	
%>