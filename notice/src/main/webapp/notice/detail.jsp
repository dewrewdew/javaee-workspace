<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<%!
   String url="jdbc:mysql://localhost:3306/java";
   String user="servlet";
   String password="1234";
   
   Connection con; // 접속 정보를 가진 객체
   PreparedStatement pstmt; //쿼리 수행 객체
   ResultSet rs; // select 수행 후, 표를 담아 제어할 수 있는 객체.
%>
<%  
		// 주의!!!!!!!!! detail.jsp는 반드시 notice_id값을 필요로 하므로 링크 사용 시 /notice/detail.jsp만 적으면 에러남
		// detail.jsp에 notice_id를 안넣은 상태로 진행을하니 null값이 들어감.
		// detail.jsp에서는 null을 문자로 인식해서 동일한 값을 스캔 => 에러 발생(empty result set)
		
		// 위의 페이지에 지시 영역은 현재 jsp가 Tomcat에 의해 서블릿으로 코딩되어 질때
      // text/html 부분은 response.setContentType("text/html");
      // charset=UTF-8 부분은 response.setCharacterEncoding("utf-8");

   //   select * from notice where notice_id=2 쿼리를 수행하여 레코드를 화면에 보여주기
         
   //웹 브라우저의 목록을 클릭시 전달되어온 pk를 notice_id 에 적용하자.
   // HTTP 통신에서 주고 받는 파라미터는 모두 문자열로 인식한다! 예) 1은  "1" 이렇게 취급된다.
   String notice_id = request.getParameter("notice_id"); // request란? 서블릿의 service(요청객체,응답객체) 중 HttpServletRequest 인터페이스를
         // 가리키는 내장 객체, 그러다 보니 개발자가 변수명을 정한 것이 아니라 이미 jsp문법에서 정해진 이름이다. 그래서 내장객체라 부름
   out.print("select * from notice where notice_id="+notice_id);
   String sql="select * from notice where notice_id="+notice_id;
   
   Class.forName("com.mysql.cj.jdbc.Driver"); // 드라이버로드
   
   con=DriverManager.getConnection(url, user, password); //접속
   
   pstmt=con.prepareStatement(sql);
   rs=pstmt.executeQuery(); // 쿼리수행
   
   rs.next(); // 커서는 언제나 before first 위치에 있기 때문에, 한칸 이동하자!!
  
   
%>


<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<style>
body {
   font-family: Arial, Helvetica, sans-serif;
}

* {
   box-sizing: border-box;
}

input[type=text], select, textarea {
   width: 100%;
   padding: 12px;
   border: 1px solid #ccc;
   border-radius: 4px;
   box-sizing: border-box;
   margin-top: 6px;
   margin-bottom: 16px;
   resize: vertical;
}

input[type=button] {
   background-color: red;
   color: white;
   padding: 12px 20px;
   border: none;
   border-radius: 4px;
   cursor: pointer;
}

input[type=button]:hover {
   background-color: #45a049;
}

.container {
   border-radius: 5px;
   background-color: #f2f2f2;
   padding: 20px;
}
</style>

<script>
//사용자가 입력한 폼의 내용을 서버로 전송하자
//JavaScript 언어는 DB에 직접적으로 통신 가능?
//JS는 클라이언트 측 (Front영역)이기 때문에 원본소스가 그냥 노출되어 있다.
	function regist() {
   //JS는 DB와의 통신 자체가 막혀있기 때문에, 직접DB에 쿼리문을 날리는것이 아니라,
   //TOMCAT과 같은 웹컨테이너(서버)에게 부탁을 한다. 즉 요청을 한다.
   let form1 = document.getElementById("form1");
   form1.action="/notice/regist"; // 서블릿 주소
   form1.method="Post"; // HTTP 프로토콜은 머리와 몸으로 데이터를 구성하여 통신을 하는 규약을 말함
                              // 이때 서버로 데이터가 양이 많거나, 노출되지 않으려면 편지지에 해당하는 Post방식을 쓴다 
                              // - 이는 드러나지 않을뿐이지 보안은 아니다.
                              
                              // 반면, 서로 데이터의 양이 적거나, 노출되어도 상관없을 경우 편지봉투에 해당하는 Get방식을 쓴다
   form1.submit(); // 전송이 발생
   }
  	function del(){
  		// let res=confirm("삭제하시겠어요?");
  		// console.log("유저의 대답은?", res);
  		
  		if(confirm("삭제하시겠어요?")){
  			location.href='/notice/delete?notice_id=<%=rs.getInt("notice_id")%>'
  		}
  	}
  	function edit(){
  		if(confirm("수정하실래요?")){
  			// 작성된 폼 양식을 서버로 전송!
  			let form1=document.getElementById("form1");
  			form1.action="/notice/edit"; // 서버의 url
  			form1.method="post";
  			form1.submit();
  		}
  	}


</script>

</head>
<body>

   <div class="container">
      <form id="form1">     
         <!-- 파라미터 중 굳이 일반 유저에게 노출될 필요가 없는 경우, 존재는 하나 눈에 보이지 않게 하는 목적으로 사용 
         ex) 신용카드 결제 시스템 등 개발 시 많이 사용
         -->                                     
      	 <input type="hidden" name="notice_id" value="<%=rs.getInt("notice_id")%>" style="background:yellow">
         <input type="text" id="fname" name="title" value="<%=rs.getString("title")%>">
         <input type="text" id="lname" name="writer" value="<%=rs.getString("writer")%>">
         <textarea name="content" style="height:200px"><%=rs.getString("content")%></textarea>
         <!-- textarea 의 경우는 <>이곳<> 에다가 넣어야한다. -->
         <input type="button" value="수정" onClick="edit()">         
         <input type="button" value="삭제" onClick="del()">              
         
         <!-- js 에서 링크를 표현한 내장객체를 location 이라함 -->
         <input type="button" value="목록" onClick="location.href='/notice/list.jsp'">
      </form>
   </div>

</body>
</html>

<%
   rs.close();   
   pstmt.close();
   con.close();
%>
