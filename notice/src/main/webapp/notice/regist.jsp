<%@ page contentType="text/html; charset=UTF-8"%>
<%
	// 위의 페이지 영역은 현재 jsp가 Tomcat에 의해 서블릿으로 코딩 되어 질때
	// response.setContextType("text/html");
	// charset=UTF-8 response.setCharacterEncoding("utf-8");
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
	background-color: #04AA6D;
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
	//JavaScript 언어는 DB에 직접적으로 통신 가능? 불가
	//JS는 클라이언트, 즉 프론트영역 언어이므로 원본 소스가 그냥 노출되어버림. 아래와 같이 쓰면 큰일남!!
	//function regist(){
		// let url="jdbc:mysql://localhost:3306/java";
		// let user="servlet";
		// let password="1234";
//}
function regist(){
	// JS는 DB와의 통신 자체가 막혀있기 때문에, 직접 DB에 쿼리문을 날리는것이 아니라,
	// Tomcat과 같은 웹컨테이너(서버)에게 부탁을 한다! 즉 요청을 한다.
	let form1=document.getElementById("form1");
	form1.action="/notice/regist"; // 서블릿 주소
	form1.method="post";
	// HTTP 프로토콜은 머리와 몸으로 데이터를 구성하여 통신을 하는 규약을 말함
	// 이때 서버로 데이터가 양이 많거나, 노출되지 않으려면 편지지에 해당하는 Post 방식을 쓴다.
	// 반면, 서로 데이터의 양이 적거나, 노출되어도 상관없을 경우 편지봉투에 해당하는 Get 방식을 쓴다.
	// Get/Post => Post는 보안 방식은 아님 상대적으로 안전하다 뿐이지 이것도 위험함!
	// Get은 데이터가 노출되면서 head를 통해 날아가는것! 즉 편지 봉투에다 내용 다 써서 보내는것!
	form1.submit(); // 전송이 발생
}

</script>
</head>
<body>

	<div class="container">
		<form id="form1"><!-- 얘도 곧 form DOM이 됨! -->
			<input type="text" id="fname" name="title" placeholder="제목 입력.."> <!-- name을 주는건 파라미터를 기입하겠다는 뜻과 같음! -->
			<input type="text" id="lname" name="writer" placeholder="작성자 입력..">
			<textarea id="subject" name="content" placeholder="내용을 입력하세요.." style="height: 200px"></textarea>
			<!-- js에서 링크를 표현한 내장객체를 location -->
			<input type="button" value="Submit" onClick="regist();">
		</form>
	</div>

</body>
</html>
