<%@page import="com.ch.shop.dto.TopCategory"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	List<TopCategory> topList = (List)request.getAttribute("topList");
	out.print(topList);
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>AdminLTE 3 | Dashboard</title>

	<%@ include file="../inc/head_link.jsp" %>  
</head>
<body class="hold-transition sidebar-mini layout-fixed">
<div class="wrapper">

  <!-- Preloader -->
	<%@ include file="../inc/preloader.jsp" %>

  <!-- Navbar -->
	<%@ include file="../inc/navbar.jsp" %>	
  <!-- /.navbar -->

  <!-- Main Sidebar Container -->
	<%@ include file="../inc/sidebar.jsp" %>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0">상품등록</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">상품관리</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
        <!-- 메인 컨텐츠 시작 -->
        <div class="row">
        	<div class="col-md-12">
        	
	            <div class="card card-info">
	              <div class="card-header">
	                <h3 class="card-title">Product Registration</h3>
	              </div>
	              <!-- /.card-header -->
	              <!-- form start -->
	              <form>
	                <div class="card-body">
	                
                	<div class="form-group row">
                		<div class="col-md-6">
		                        <select class="form-control" name="topcategory">
		                        <%for(TopCategory topCategory : topList) { %>
		                          <option value="<%=topCategory.getTopcategory_id()%>"><%=topCategory.getTopname() %></option>
		                        <%} %>
		                        </select>
                      </div>
	                      
	                		<div class="col-md-6">
			                        <select class="form-control">
			                          <option>option 1</option>
			                        </select>
	                      </div>   
                      </div>                   
	                
	                  <div class="form-group">
	                    <input type="text" class="form-control" name="product_name" placeholder="상품명">
	                  </div>
	                  
       	              <div class="form-group">
	                    <input type="text" class="form-control" name="brand" placeholder="브랜드">
	                  </div>
	                 
   	                  <div class="form-group">
	                    <input type="number" class="form-control" name="price" placeholder="가격(숫자로 입력)">
	                  </div>
	                  
	                  <div class="form-group">
	                    <input type="number" class="form-control" name="discount" placeholder="할인가(숫자로 입력)">
	                  </div>
	                  
	                  <div class="form-group">
	                    <input type="text" class="form-control" name="introduce" placeholder="간단소개">
	                  </div>
	                  
	                  <div class="form-group">
	                    <textarea id="summernote" class="form-control" name="detail" placeholder="간단소개"></textarea>
	                  </div>
	                  
	                  
	                  
	                  
	                  <div class="form-group">
	                    <label for="exampleInputFile">File input</label>
	                    <div class="input-group">
	                      <div class="custom-file">
	                        <input type="file" class="custom-file-input" id="exampleInputFile">
	                        <label class="custom-file-label" for="exampleInputFile">Choose file</label>
	                      </div>
	                      <div class="input-group-append">
	                        <span class="input-group-text">Upload</span>
	                      </div>
	                    </div>
	                  </div>
	                </div>
	                <!-- /.card-body -->
	
	                <div class="card-footer">
	                  <button type="submit" class="btn btn-info">Submit</button>
	                </div>
	              </form>
	            </div>
        	
        	</div>
        </div>
        <!-- 메인 컨텐츠 끝 -->
      </div><!-- /.container-fluid -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
	<%@ include file="../inc/footer.jsp" %>
	
  <!-- Control Sidebar -->
	<%@ include file="../inc/control_sidebar.jsp" %>  
  <!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->
	<%@ include file="../inc/footer_link.jsp" %>
	<script>
	function getSubCategory(){
		// JQuery의 비동기 통신
		// 하위 카테고리에 대한 요청을 받을 수 있는 자
		$.ajax({
			url : "/admin/subcategory/list?topcategory_id="+$("select[name='topcategory']").val(),
			method: "GET",
			// 요청 후 서버에서 응답이 도착했을 때 동작할 속성 및 콜백함수 정의
			// 서버의 응답이 200번대이면 아래의 success에 명시된 익명 함수가 동작하고,
			// result : 서버에 보낸 데이터, status : 서버의 상태, xhr : XMLHttpRequest 객체(단, xhr은 거의 쓸 일이 없음)
			success: function(result, status, xhr) {
				
			},
			// 서버의 응답이 300번대 이상이면, 즉, 문제가 있을 경우 아래의 error 속성에 명시된 익명함수가 동작한다.
			error: function(xhr, status, err) {
				
			}
			
		});
	}
	
	
	
	$(()=>{
		$("#summernote").summernote();
		
		// 상위 카테고리의 select 상자의 값을 변경할 때, 비동기 방식으로 즉 새로고침없이 하위 카테고리를 출력해주면 유저들이 불편함을 겪지 않게 된다.
		// 지금까지는 js 순수 코드를 이용하여 비동기 통신을 수행했지만, 이번 예제에서는 Jquery가 지원하는 비동기 통신 방법을 써보자
		$("select[name='topcategory']").change(()=>{
			getSubCategory();
		});
	});
	
	</script>
</body>
</html>