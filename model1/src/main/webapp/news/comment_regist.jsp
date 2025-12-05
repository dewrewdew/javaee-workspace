<%@ page contentType="text/html; charset=UTF-8"%>
<!-- html정보가 포함되어버리면 순수한 데이터만 보낼 수 없음!!! -->
<%
	// 클라이언트가 비동기적으로 요청을 시도하므로, 파라미터를 받고 DB에 넣은 후
	// 응답정보는 HTML? JSON?
			
	request.setCharacterEncoding("utf-8"); // 파라미터의 한글이 꺠지지 않도록 인코딩 지정
	
	String msg = request.getParameter("msg");
	String reader=request.getParameter("reader");
	
	System.out.println("msg는 "+ msg);
	System.out.println("reader는 "+ reader);
%>