<%@ page contentType="text/html; charset=UTF-8"%>
<%
	/* 
	하나의 페이지로 모든 기능과 디자인을 합쳐놓은 프로그램의 장단점
	장점 : 개발 시간이 단축됨
	단점 : 디자인과 로직이 뒤섞여있으므로, 디자인을 버릴 경우 로직도 함께 버려야 함.
	*/
%>
<%
	// 클라이언트가 전송한 파라미터를 받아 영화에 대한 피드백 메세지 만들기
	request.setCharacterEncoding("utf-8"); // 파라미터에 대한 인코딩
	String movie = request.getParameter("movie");
	out.print(movie);
	
	// 각 영화에 대한 메시지 만들기
	String msg="선택한 영화가 없음";
	
	if(movie!=null){ // 파라미터가 있을때만 작동하도록
		if(movie.equals("귀멸의칼날")) {
			msg="대꿀잼 영화";
		} else if(movie.equals("주토피아2")){
			msg="닉 쥬디 꿀귀";
		} else if(movie.equals("노트북")) {
			msg="인생영화";
		} else if(movie.equals("라라랜드")) {
			msg="언덕길에서 춤추는 부분이 젤 좋음";
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function request(){
		document.querySelector("form").action="/movie/script/form.jsp";
		document.querySelector("form").method="POST";
		document.querySelector("form").submit();
	}
	
	addEventListener("load", function(){
		document.querySelector("button").addEventListener("click", ()=>{
			request();
		});
	});
</script>
</head>
<body>
	<form>
		<select name = "movie">
			<option value="귀멸의칼날">귀멸의칼날</option>
			<option value="주토피아2">주토피아2</option>
			<option value="노트북">노트북</option>
			<option value="라라랜드">라라랜드</option>
		</select>
		<button type="button">피드백 요청</button>
	</form>
	
	<!--  이 프로그램에 대해 유지보수성을 고려할 필요가 없을정도로 간단한 기능으로 판단된다면 굳이 유지보수성을 염두에 둔 자바 클래스까지 도입할 필요가 없다. 
			따라서 스크립트만으로 해결해보자!
			이러한 개발 방식을 가리켜 이름조차 없는 막개발 방식이라 함. 스크립트 위주의 개발 방식 => 아주 간단한 분야에 유용
			화분심을때 그 규모에 따라 모종삽을 쓸건지, 삽을쓸건지, 포크레인을 쓸건지 결정하는것과 유사
	-->
	<h3>
		선택한 결과 <br>
		<span style="color:red">
			<%=msg %>
		</span>
	</h3>
	
	
	
</body>
</html>