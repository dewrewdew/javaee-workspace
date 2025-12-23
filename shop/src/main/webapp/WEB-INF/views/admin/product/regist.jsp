<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.ch.shop.dto.TopCategory" %>
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
	              <form id="product-form">
	                <div class="card-body">
						
						<div class="form-group row">
							<div class="col-md-6">
		                        <select class="form-control" name="topcategory"></select>
	                      	</div>
	                      	
							<div class="col-md-6">
		                        <select class="form-control" name="subcategory"></select>
	                      	</div>
						</div>	                      		
                      							
	                  <div class="form-group">
	                    <input type="text" class="form-control"  name="product_name" placeholder="상품명 ">
	                  </div>
	                  
	                  <div class="form-group">
	                    <input type="text" class="form-control"  name="brand" placeholder="브랜드 ">
	                  </div>
	                  
	                  <div class="form-group">
	                    <input type="number" class="form-control"  name="price" placeholder="가격(숫자로 입력) ">
	                  </div>
	                  
	                  <div class="form-group">
	                    <input type="number" class="form-control"  name="discount" placeholder="할인가(숫자로 입력) ">
	                  </div>
	                  
					<div class="form-group row">
						<div class="col-md-6">
							<select multiple class="form-control" name="color"></select>
						</div>
						                	
						<div class="col-md-6">
							<select multiple class="form-control" name="size"></select>
						</div>
					</div>
	                  
	                  <div class="form-group">
	                    <input type="text" class="form-control"  name="introduce" placeholder="간단소개">
	                  </div>
	                  
	                  <div class="form-group">
	                    <textarea id="summernote" class="form-control"  name="detail" placeholder="간단소개"></textarea>
	                  </div>

	                  
	                  <div class="form-group">
	                    <label for="exampleInputFile">File input</label>
	                    <div class="input-group">
	                      <div class="custom-file">
	                        <input type="file" class="custom-file-input" id="product-img" multiple>
	                        <label class="custom-file-label" for="exampleInputFile">Choose file</label>
	                      </div>
	                      <div class="input-group-append">
	                        <span class="input-group-text">Upload</span>
	                      </div>
	                    </div>
	                  </div>
	                  
	                  <div class="form-group row">
	                  	<div class="col-md-12" id="product-preview"></div>
	                  </div>
	                  
	           
	                </div>
	                <!-- /.card-body -->
	
	                <div class="card-footer">
	                  <button type="button" class="btn btn-info">Submit</button>
	                  <button type="button" class="btn btn-info">상품목록</button>
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
	<script src="/static/adminlte/custom/js/PreviewImg.js"></script>
	<script>
	//외부의 영역에서 접근하게 하려고 전역변수로 선언
	let selectedFile;
	
	//이 함수는 상위,하위를 모두 처리해야 하므로, 호출 시 상위를 원하는지, 하위를 원하는지 구분해줘야한다
	//printCategory("상위 카테고리","topcategory", list)
	function printCategory(title,category, list){
		let tag="<option value='0'>"+title+"</option>";
		
		for(let i=0;i<list.length; i++){
			if(category=="topcategory"){
				tag+="<option value='"+list[i].topcategory_id+"'>"+list[i].topname+"</option>"; //기존 <option value="1"></option>에 누적
			}else if(category=="subcategory"){
				tag+="<option value='"+list[i].subcategory_id+"'>"+list[i].subname+"</option>"; //기존 <option></option>에 누적
			}else if(category=="color"){
				tag+="<option value='"+list[i].color_id+"'>"+list[i].color_name+"</option>"; //기존 <option></option>에 누적
			}else if(category=="size"){
				tag+="<option value='"+list[i].size_id+"'>"+list[i].size_name+"</option>"; //기존 <option></option>에 누적
			}
		}
		$("select[name='"+category+"']").html(tag);
	}	
	
	//최상위 카테고리 비동기로 가져오기 
	function getTopCategory(){
		$.ajax({
			url:"/admin/topcategory/list",
			method:"GET",
			success:function(result, status, xhr){
				//서버에서 응답한 정보인 result를 이용하여 select박스에 출력 
				printCategory("상위 카테고리 선택","topcategory", result);
			},
			error:function(xhr, status, err){
				
			}			
		});		
	}
	
	function getSubCategory(){
		//JQuery의 비동기통신 
		$.ajax({
			url:"/admin/subcategory/list?topcategory_id="+$("select[name='topcategory']").val() ,
			method:"GET", 
		//요청 후 서버에서 응답이 도착했을때 동작할 속성및 콜백함수 정의
			//서버의 응답이 200 번대 이면 아래의 success에 명시된익명함수가 동작하고,
			//result :서버에 보낸 데이터, status 서버의 상태, xhr XMLHttpRequest객체 
			success:function(result, status, xhr ){
				//스프링에서 문자열을 전송 시 content-type을 json으로 전송했기 때문에, 클라이언트 측인 자바스크립트에서 
				//별도로 JSON.parse() 과정이 필요없게 되었음(편해졌다)
				console.log(result[0].subname);
				printCategory("하위 카테고리 선택","subcategory", result);; //자바스크립의 객체로 이루어진 배열 전달 
			},
			//서버의 응답이 300번대 이상이면, 즉 문제가 있을 경우 error속성에 명시된 익명함수가 동작함 
			error:function(xhr, status, err){
				
			}
				
		});
	}
	
	//자바스크립트의 이벤트 객체정보 안에 포함된 유사 배열 객체를 이용하여 화면에, 유저가 선택한 이미지를 그려보자
	function preview(imgList){
		
		//imgList는 자바스크립트의 문법이 아닌, 즉 브라우저 API 지원하는 배열 유사객체이므로, 읽기전용이다
		//따라서 사용자가 이미지를 제거할 경우, 배열 명단에서도 제거(동기화)해야 하므로, 배열 유사객체인 FileList와 동일하게 생긴
		//순수 자바스크립트의 배열로 복사하자 , 이 시점부터는 사용자가 이미지를 제거할때 자바스크립트의 배열의 한요소를 제거하고,
		//또한 제거가 완료된 후 서버로 전송할때도 FileList가 아닌 자바스크립트 배열을 이용하여 서버로 전송하겠다!!
		selectedFile=Array.from(imgList); //imgList라는 FileList 읽기전용 유사배열을, 자바스크립트의 배열로 반환 
		console.log("쓰기가 가능한 js배열 생성 결과", selectedFile);		
		
		for(let i=0;i<selectedFile.length;i++){
			let reader = new FileReader(); //순수 자바스클비트가 아닌 브라우저 API
			
			//이미지가 아래의 메서드에서 다 읽혀지면 호출되는 콜백함수 처리
			//또한 읽혀진 파일 정보는 매개변수로 전달되어진다..
			reader.onload=function(e){
				//우리가 만든 클래스로부터 인스턴스 생성하기!!
				console.log(e.target);
				let previewImg = new PreviewImg(document.getElementById("product-preview"),selectedFile[i], e.target.result,100,100 );
			}
			reader.readAsDataURL(imgList[i]);//지정한 파일 객체를 읽어들이는 메서드!!
		}
		
	}
	
	
	//JQuery가 지원하는 ajax 기술인 Jquery ajax 를 활용하여 비동기로 가져오기 
	function getColorList(){
		$.ajax({
			url:"/admin/color/list",
			method:"GET",
			success:function(result, status, xhr){
				console.log("서버에서 가져온 색상목록은 ",  result);
				printCategory("색상 선택","color", result);
			},
			error:function(xhr, status, err){
				
			}
		});
	}
	
	function getSizeList(){
		$.ajax({
			url:"/admin/size/list",
			method:"GET",
			success:function(result, status, xhr){
				console.log("서버에서 가져온 사이즈 목록은 ",  result);
				printCategory("사이즈 선택","size", result);
			},
			error:function(xhr, status, err){
				
			}
		});
	}
	
	/*--------------------------------------------------
	 지금까지는 파일을 업로드 수행은 동기방식으로만 시도했었음..
	 비동기방식으로도 파일을 업로드 할 수 있다..
	 폼양식에 바이너리 포함되었을 경우 비동기로 전송하려면, 브라우저 자체에서 제공하는 API인 FormData 를 사용할 수 있다.
	 FormData 자체적으로 자동으로 바이너리 파일 전송 시 사용되는 인코딩 방식인 multipart/form-data 가 처리되어 있다 
	--------------------------------------------------*/
	function regist(){
		//파라미터를 채울때는 append() 메서드 사용함 
		//formData.append("product_name", $("input[name='product_name']").val() );		
		//개발자가 일일이 파라미터를 지정하면 Form의 파라미터 수가 많을 경우 코드량이 많아지므로, 
		//FormData에 기존의 Form을 대입하자!!
		let formData = new FormData(document.getElementById("product-form")); //$("#product-form") 적용불가
	
	 	formData.delete("photo");//비동기 방식이므로, 새로고침이 발생하지 않기 때문에, 등록버튼을 여러번 누를경우	
		 										//formData에 photo 파라미터가 계속 누적됨..따라서 등록버튼을 누를때마다, 
		 										//기존의 이미지 쌓여있던 이미지 파라미터를 제거해버리자
		 										
		//사용자가 선택한 이미지 배열만큼 formData에 추가하기
		for(let i=0; i<selectedFile.length;i++){
			formData.append("photo", selectedFile[i] );
		}
		formData.append("subCategory.subcategory_id", $("select[name='subcategory']").val() );
		
		//비동기 요청이기 때문에 JQuery Ajax 사용하기 
		$.ajax({
			url:"/admin/product/regist",
			method:"POST",
			data: formData,
			/*processData 속성 의미: JQuery Ajax 는 파라미터 전송 시 원래 json문자열로 전송함 
			{name:"zino", age"20} 을 name=zino&age=20 로 자동 변환해주는데, 우리의 경우 formData를 이용하기 때문에
			전송할 데이터에 바이너리 파일이 포함되어있다..따라서 바이너리 파일을 대상으로 자동 문자열 변환을 허용하면 에러가 난다..
			즉 올바르지 않은 형식의 데이터전송이 되므로, JQuery로 하여금 문자열 자동 변환을 하지 말라고 막는 속성
			*/
			processData:false,
			
			/*	contentType 속성 의미 
				QJquery Ajax에게 POST 전송을 맡기면 자동으로 contentType:aplication/x-www-form-urlencoded; charset=UTF-8
				변경하게 되는데, 우리의 경우 이미지와 같은 바이너리 파일이 포함되어 잇으므로, JQuery로 하여금 자동으로 인코딩을 지정하지
				못하게 금지..				
			*/
			contentType:false,
			//서버의 응답 코드가 200(성공)일 경우 아래의  success속성에 명시된 콜백함수가 동작함 
			success:function(result, status, xhr){
				//서버측에서 응답한 데이터 형식이 순수 문자열일 경우, js의 객체리터럴로 변환해야, 점찍고 쓸 수 있다..
				//그러기 위해서는 문자열을 --> 객체로   JSON.parse() 로 처리 
				//let obj=JSON.parse(result);
				//console.log(result);
				alert("success : "+result.message);	//"{ "messagse" :"상품등록"}"		
			},
			
			//서버측에서 응답한 응답 코드가 에러인 경우 (300 이상인 경우 아래의 error속성에 명시된 콜백함 수 호출)
			error:function(xhr, status, err){
				let obj=JSON.parse(xhr.responseText);
				alert("error : "+obj.message);
				//console.log(xhr.responseText, xhr.responseText.length);
			}
			
		});
		
	}
	
	$(()=>{
		$("#summernote").summernote();
		
		
		
		//상위카테고리의 select 상자의 값을 변경할때, 비동기방식으로 즉 새로고침 없이 하위 카테고리를 출력해주면 
		//유저들이 불편함을 겪지 않게 된다..
		//지금까지는 js 순수 코드를 이용하여 비동기통신을 수행했지만, 이번 프로그램에서는 Jquery가 지원하는 비동기통신 방법을 써보자
		getTopCategory();
		
		$("select[name='topcategory']").change(()=>{
			getSubCategory();
		});
		
		//색상을 비동기로 가져오기
		getColorList();		
		
		//사이즈를 비동기로 가져오기 
		getSizeList();
		
		
		//유저가 이미지 컴포넌트에서 이미지를 변경할때를 처리하는 이벤트 연결 
		$("#product-img").change((e)=>{
			//사용자가 이미지 컴포넌트를 통해 이미지를 선택하면, 선택한 갯수에 따라 배열로 모아서 반환해주는데, 
			//이때 이미지 정보가 담겨잇는 FileList라는 객체는 , 순수 자바스크립트언어에서 지원하는 객체가 아니라, 
			//크롬과 같은 브라우저 Api제공하는 객체이다. 특히 FileList(readOnly)는 읽기전용 이므로, 개발자가 수정하거나, 삭제할 수 없다..
			console.log(e);
			
			console.log("이미지 바꿨어? ", e.target.files);
			preview(e.target.files);//배열 유사객체를 가지고 호출!
		});
		
		$($(".card-footer button")[0]).click(()=>{
			regist();
		});
		
		$($(".card-footer button")[1]).click(()=>{
			location.href="/admin/product/list";
		});
		
	});
	</script>
</body>
</html>











