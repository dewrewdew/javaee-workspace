<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function request(){
		document.querySelector("form").action="/movie.do";
		document.querySelector("form").method="GET";
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
	
</body>
</html>