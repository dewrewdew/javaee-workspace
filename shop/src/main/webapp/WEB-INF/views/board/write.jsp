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
  
  
<!-- include libraries(jQuery, bootstrap) -->
<!--  <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">-->
<!--  <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>-->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote.min.js"></script>  
  
<script>
$(()=>{
	 $('#summernote').summernote();	
	 
	 //등록 버튼에 이벤트 연결 	 
	 document.getElementById("bt_regsit").addEventListener("click", function(){
		 //서버에 전송 
		 let form1=document.getElementById("form1");
		 form1.action="/board/regist";
		 form1.method="post";
		 form1.submit();
	 });
		 
});
</script>
</head>
<body>

<div class="container">
  <h2>Stacked form</h2>
  <form id="form1">
    
    <div class="form-group">
      <label for="email">제목:</label>
      <input type="email" class="form-control" id="email" placeholder="Enter email" name="title">
    </div>
    
    <div class="form-group">
      <label for="email">작성자:</label>
      <input type="email" class="form-control" id="email" placeholder="Enter email" name="writer">
    </div>

    <div class="form-group">
      <label for="email">내용:</label>
      <textarea type="email"  class="form-control" id="summernote" placeholder="Enter email" name="content"></textarea>
    </div>
    
    <button type="button" id="bt_regsit" class="btn btn-primary">글등록</button>
    <button type="button" id="bt_list" class="btn btn-primary">목록</button>
  </form>
</div>

</body>
</html>
