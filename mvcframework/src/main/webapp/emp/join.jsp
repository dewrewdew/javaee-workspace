<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  <script>
  $(()=>{
	  $("button").click(()=>{
		  $("form").attr({
			  action:"/emp/regist.do",
			  method:"post"
		  });
		  
		  $("form").submit();
	  });
  });
  </script>
</head>
<body>

<div class="container">
  <h2>사원 등록</h2>
  <form>
    <div class="form-group">
      <input type="text" class="form-control" placeholder="부서번호" name="deptno">
    </div>
    <div class="form-group">
      <input type="text" class="form-control" placeholder="부서명" name="dname">
    </div>
    <div class="form-group">
      <input type="text" class="form-control" placeholder="부서 위치" name="loc">
    </div>
    <div class="form-group">
      <input type="text" class="form-control" placeholder="사원번호" name="empno">
    </div>
    <div class="form-group">
      <input type="text" class="form-control" placeholder="사원명" name="ename">
    </div>
    <div class="form-group">
      <input type="text" class="form-control" placeholder="급여" name="sal">
    </div>
    <div class="form-group form-check">
    </div>
    <button type="button" class="btn btn-primary">사원등록</button>
  </form>
</div>

</body>
</html>
