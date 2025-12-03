<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .container {
        width: 650px;
        height: 500px;
        background-color: aqua;
        margin:auto;
    }

    .aside {
        width: 150px;
        height: 100%;
        background-color: pink;
        float:left;        
    }
        .aside input {
            width: 90%;
        }

        .aside button {
            width: 40%;
        }

    .content {
        width: 500px;
        height: 100%;
        background-color: greenyellow;
        float:left;
    }

</style>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

<script>
    /* 문서가 로드가 되면, 두개의 버튼에 대해 이벤트 연결
       화살표 함수 - 기본 함수정의 기법을 줄여서 표현... */
    $(()=> {       
        // function 대신 => 화살표 함수, 자바에선 람다식
        // 동기버튼에 클릭 이벤트 연결 - 배열 방식으로
        $($("form button")[0]).click(()=>{
            // alert("동기방식의 요청시도");
            $("form").attr({
                action:"/ajax/regist.jsp",
                method:"post"
            });
            $("form").submit();
        });

        // 비동기버튼에 클릭 이벤트 연결 - 배열 방식으로
        $($("form button")[1]).click(()=>{
            alert("비동기방식의 요청시도");
        });
    });
</script>


</head>
<body>
    <div class="container">
        <div class="aside">
            <form>
                <input type="text" placeholder="Your ID" name="id">
                <input type="text" placeholder="Your name" name="name">
                <input type="text" placeholder="Your email" name="email">
                <button>sync</button>
                <button>async</button>
            </form>
        </div>
        <div class="content"></div>
    </div>
</body>
</html>