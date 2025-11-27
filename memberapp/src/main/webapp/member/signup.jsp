<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<style>
body {font-family: Arial, Helvetica, sans-serif;}
* {box-sizing: border-box}

/* Full-width input fields */
input[type=text], input[type=password] {
  width: 100%;
  padding: 15px;
  margin: 5px 0 22px 0;
  display: inline-block;
  border: none;
  background: #f1f1f1;
}

input[type=text]:focus, input[type=password]:focus {
  background-color: #ddd;
  outline: none;
}

hr {
  border: 1px solid #f1f1f1;
  margin-bottom: 25px;
}

/* Set a style for all buttons */
button {
  background-color: #04AA6D;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  cursor: pointer;
  width: 100%;
  opacity: 0.9;
}

button:hover {
  opacity:1;
}

/* Extra styles for the cancel button */
.cancelbtn {
  padding: 14px 20px;
  background-color: #f44336;
}

/* Float cancel and signup buttons and add an equal width */
.cancelbtn, .signupbtn {
  float: left;
  width: 50%;
}

/* Add padding to container elements */
.container {
  padding: 16px;
}

/* Clear floats */
.clearfix::after {
  content: "";
  clear: both;
  display: table;
}

/* Change styles for cancel button and signup button on extra small screens */
@media screen and (max-width: 300px) {
  .cancelbtn, .signupbtn {
     width: 100%;
  }
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
/* JQuery의 문법 구조는 아래와 같이 단순!
 	형식(Syntax) : $(누구를).어떻게();
 	'누구'를 자리에 올 수 있는 주체는 바로 CSS의 선택자
 	순수 javascript(=vanilla script)로 onLoad를 구현하면 아래와 같다.
	
 	window.addEventListener("load", function()){
	});
	이렇게 쓰던게 아래와 같이 단순해짐.
	$(document).ready(function()){
		alert("문서 로드됐어");
	});
	
	여기서 멈추지 않고 더 줄여서
*/
	$(function(){
		// alert("단축식으로 로드");
		$("#bt_signup").click(function(){
			// 폼 양식을 서버로 전송하자
			/*
			$("#form1").action="/member/signup";
			$("#form1").method="post";
			$("#form1").submit();
			*/
			$("#form1").attr({
				action:"/member/signup",
				method:"post"
			})
			$("#form1").submit();
		});
	});
	
</script>
<body>

<form id="form1" style="border:1px solid #ccc">
  <div class="container">
    <h1>Sign Up</h1>
    <p>Please fill in this form to create an account.</p>
    <hr>

    <label for="email"><b>ID</b></label>
    <input type="text" placeholder="Enter Your ID" name="id" required>

    <label for="psw"><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="pwd" required>

    <label for="psw-repeat"><b>Repeat Password</b></label><!-- 이부분은 비밀번호 확인용! DB로 전송하지x -->
    <input type="password" placeholder="Repeat Password" name="pwd-repeat" required>
    
    <label for="psw-repeat"><b>Your Name</b></label>
    <input type="text" placeholder="Your Name.." name="name" required>
    
    <label>
      <input type="checkbox" checked="checked" name="remember" style="margin-bottom:15px"> Remember me
    </label>
    
    <p>By creating an account you agree to our <a href="#" style="color:dodgerblue">Terms & Privacy</a>.</p>

    <div class="clearfix">
      <button type="button" class="cancelbtn">Cancel</button>
      <button type="button" class="signupbtn" id="bt_signup">Sign Up</button>
    </div>
  </div>
</form>

</body>
</html>
