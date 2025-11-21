<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%!
	// 선언부는 서블릿으로 변환될 때 자동으로 멤버영역으로 자리잡는다.
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="servlet";
	String pass="1234";
	// 멤버변수라 초기화 하지 않아도 알아서 초기화 됨.

%>


<%
	// page 지식 영역에서 contentType()을 명시한 것은, 이 jsp가 서블릿으로 변환되어질때
	// response 객체의 메서드 중 setContentType("text/html;charset=utf-8")
	// 즉, 간접코딩!
	
	// 오라클 연동하기
	// 고양이가 예외처리 이미 알아서 함
	// 아래의 코드는 원래 순수 java 클래스에서 작성할 경우, 예외처리가 강제되지만
	// 현재 우리의 jsp영역은 실행 직전 tomcat에 의해 서블릿으로 변환되어지며
	// 특히 스크립틀릿 영역은 service() 메서드로 코드가 작성되고, 이때 tomcat이 예외처리까지 해버렸으므로
	// jsp에서는 예외처리할 것을 강요당하지 않음.
	
	// 1단계) 드라이버 로드
	Class.forName("oracle.jdbc.driver.OracleDriver");

	// 2단계) 접속
	con=DriverManager.getConnection(url, user, pass);
	
	// 3단계) 쿼리 실행
	String sql = "select * from gallery";
	pstmt=con.prepareStatement(sql); // 쿼리 수행 객체 생성
	rs=pstmt.executeQuery(); // 쿼리 수행 후 그 결과를 rs로 받자
	
	//쿼리문이 select인 경우 그 결과표를 받는 객체가 resultSet
	
%>
<%!
	/*
	jsp는 사실상 서블릿이다.
	결론 : jsp의 개발 목적 - 서블릿이 디자인을 표현하는데 너무나도 비효율적이므로,
	개발자 대신 디자인 컨텐츠를 시스템인 웹 컨테이너가 대신 작성해주기 위한 스크립트 언어 
	그래서 클래스 없이도 멤버 변수 선언 가능!
	*/
	// 선언부라 한다.
	int x = 7; // 멤버 변수
	
	public int getX(){ // 멤버 메서드
		return x;
	}// 
%>
<%
	// 이 영역을 스크립틀릿이라 하며, 추후 고양이에 의해 이 jsp가 서블릿으로 변환되어질 때
	// 이 영역에 작성한 코드는 service() 안에 작성한 것과 같아진다.
	// 선언한적도 없는 레퍼런스 변수를 사용할 수 있는 이유?
	// jsp는 총 9가지 정도의 내장 객체를 지원함(Built-in Object)
	// 문자 기반의 출력 스트림 객체를 미리 변수명까지 지정해놓음 => out이라 함.
	out.print(getX());
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
table {
  border-collapse: collapse;
  border-spacing: 0;
  width: 100%;
  border: 1px solid #ddd;
}

th, td {

  text-align: left;
  padding: 16px;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}
</style>
</head>
<body>
<pre>
1) JSP란? Java Server Page(자바 기반의 서버에서 실행되는 페이지)
오직 javaEE기반의 서버에서만 해석 및 실행됨
장점 - 서블릿과 달리 HTML을 혼용하여 사용이 가능(서블릿의 디자인 표현의 취약점 보완하기 위한 기술)

2) JSP의 코드 작성 영역
 - jsp는 다음의 3가지 영역에 코드를 작성할 수 있다.
 	(1) 지시영역 - @ 붙은 영역을 의미
 		현재 jsp 페이지의 인코딩, 파일 유형, 다른 클래스 import 등을 위한 영역
 		
 	(2) 선언부 : !가 붙은 영역
      멤버 영역(멤버 변수나 메서드를 선언할 수 있는 영역)
     
    (3) 스크립틀릿 - 실행 영역
    					실질적으로 로직을 작성하게 될 영역
 		
</pre>
<h2>Zebra Striped Table</h2>
<p>For zebra-striped tables, use the nth-child() selector and add a background-color to all even (or odd) table rows:</p>

<table>
  <tr>
    <th>First Name</th>
    <th>Last Name</th>
    <th>Points</th>
  </tr>
<%// rs 객체의 next() 메서드를 호출할 때마다 커서가 밑으로 한칸씩 전진하는데,
	// 이때 커서가 위치한 행의 레코드가 존재할 경우는 true를 반환하지만,
	// 존재하지 않으면 false를 반환
	// 따라서 모든 레코드 만큼 반복문을 수행하려면 next()가 참인 동안 반복하면 된다! 그래서 for문보다 while문이 더 잘어울림(레코드가 몇개인지 모르니까)
	%>
<%while(rs.next()){%>
  <tr>
    <td><%out.print(rs.getInt("gallery_id")); %></td>
    <td><%out.print(rs.getString("title")); %></td>
    <td><%out.print(rs.getString("filename")); %></td>
  </tr>
<%} %>
</table>
</body>
</html>
<%
	rs.close();
	pstmt.close();
	con.close();
%>