<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.Connection"%>
<%! //!붙이면, 선언부이다,(선언부란? 이 jsp가 서블릿으로 변환되어질 때 멤버변수와 멤버 메서드가 정의되는 영역)
	String url="jdbc:mysql://localhost:3306/java"; // service메서드의 멤버변수
	String user="servlet";
	String password="1234";
	
	Connection con; // 접속 정보를 가진 객체
	PreparedStatement pstmt; // 쿼리 수행 객체
	ResultSet rs; // select 수행 후 표를 담아 제어할 수 있는 객체
%>
<%
// JSP는 서블릿이므로 이 영역(스크립틀릿)에서 개발자가 코드를 작성하면, 이 jsp가 Tomcat에 의해 서블릿으로 변환되어질 때
// 생명주기(init, service, destroy) 중 service()메서드 영역에 코드를 작성한 거승로 처리
// 따라서 클라이언트의 요청을 처리하는 메서드인 service() 메서드에서 mysql의 데이터를 가져와 화면에 출력!
// 주의) 서블릿으로도 가능은 하지만, 수많은 코드 라인마다 out.print()출력을 해야하므로 디자인 작업 시 효율성이 떨어진다.
// 따라서, 디자인이 필요하면 jsp 필요없으면 서블릿!
%>
<%
	Class.forName("com.mysql.cj.jdbc.Driver"); // mysql 드라이버
	con=DriverManager.getConnection(url, user, password); // 접속하기
	
	
	String sql="select * from notice";
	
	// TYPE_SCROLL_INSENSITIVE : 스크롤이 가능한 옵션
	// CONCUR_READ_ONLY : 오직 읽기 전용으로만
	pstmt=con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); // 쿼리 수행 객체 생성
	// DML이 아닌 select이기 때문에, 메서드 executeQuery() 를 써야함. => select는 dml로 안치는지 확인!
	rs=pstmt.executeQuery();
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset = "UTF-8">
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
/*링크 밑줄 제거*/
a{text-decoration:none}
</style>
</head>
<body>

<h2>Zebra Striped Table</h2>
<p>For zebra-striped tables, use the nth-child() selector and add a background-color to all even (or odd) table rows:</p>

<table>
  <tr>
    <th>No</th>
    <th>제목</th>
    <th>작성자</th>
    <th>등록일</th>
    <th>조회수</th>
  </tr>
  <%
  	rs.last(); // 커서를 ResultSet의 제일 마지막 행으로 이동 => jsp는 서버 재가동할 필요없음!!
  	out.print("현재 테이블의 총 레코드 수는 " + rs.getRow());
  	
  	// rs의 기본 속성은 ResultSet.TYPE_FORWARD_ONLY로 되어있음 (기본값)
  	// TYPE_FORWARD_ONLY 상수로 지정되면, 커서가 오직 전방향으로 단 한칸씩만 이동 가능함!
  	// PrepareStatement 생성 시 상수를 직접 지정해야 함! => pstmt 선언부로가서 수정하기!
  	
  	// 마지막행으로 이동한 rs의 커서를 다시 원상복귀 시키자.
  	rs.beforeFirst();
  	
  %>
  <% while(rs.next()) {// 레코드 수 만큼! next()메서드가 true를 반환하는 동안만 진행}%>
 <tr>
    <td>0</td>
    <td><a href="/notice/detail.jsp?notice_id=<%= rs.getInt("notice_id") %>"><%=rs.getString("title")%></a></td>
    <td><%=rs.getString("writer")%></td>
    <td><%=rs.getString("regdate")%></td>
    <td><%=rs.getInt("hit")%></td>
  </tr>
  <%} %>
 <!-- rs의 next()는 레코드가 있으면 true, 없으면 false를 반환해줌. 그래서 while문이 잘 어울림! -->
  <tr>
  	<td colspan="5">
  		<button onClick="location.href='/notice/regist.jsp'">글등록</button>
  	</td>
  </tr>
</table>

</body>
</html>
<%
	rs.close();
	pstmt.close();
	con.close();
%>