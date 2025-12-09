<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function request(){
		document.querySelector("form").action="/food.do";
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
		<select name = "food">
			<option value="겉절이">겉절이</option>
			<option value="총각김치">총각김치</option>
			<option value="김치찜">김치찜</option>
			<option value="파김치">파김치</option>
		</select>
		<button type="button">피드백 요청</button>
	</form>
	
</body>
</html>