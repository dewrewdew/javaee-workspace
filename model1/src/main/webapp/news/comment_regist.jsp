<%@ page contentType="application/json; charset=UTF-8"%>
<%@ page import = "com.ch.model1.repository.CommentDAO" %>
<%@ page import = "com.ch.model1.dto.Comment" %>
<%@ page import = "com.ch.model1.dto.News" %>
<%! CommentDAO commentDAO= new CommentDAO(); %>
<%

	//보통 클래스명 맨 앞글자를 소문자로 만들어서 클래스명 그대로 사용함 변수명으로
	// html정보가 포함되어버리면 순수한 데이터만 보낼 수 없음!!!
	// 클라이언트가 비동기적으로 요청을 시도하므로, 파라미터를 받고 DB에 넣은 후
	// 응답정보는 HTML? JSON?
			
	request.setCharacterEncoding("utf-8"); // 파라미터의 한글이 꺠지지 않도록 인코딩 지정
	
	String msg = request.getParameter("msg"); // 댓글 내용
	String reader=request.getParameter("reader"); // 댓글 작성자
	String news_id = request.getParameter("news_id"); // 제일 중요!!! 부모의 pk
	
	System.out.println("msg는 "+ msg); 
	System.out.println("reader는 "+ reader);
	System.out.println("news_id는" + news_id);
	
	// 파라미터를 하나의 DTO로 모으기
	Comment comment = new Comment(); // 비닐봉투 준비
	comment.setMsg(msg);
	comment.setReader(reader);
	
	// 부모를 숫자가 아닌 객체형태로 보유하고 있으므로 일단은 인스턴스가 생겨나야 거기다가 넣던말던함 => new 로 인스턴스를 생성하는것은 heap영역 메모리에 업로드하는 행위!!
	News news = new News(); 
	news.setNews_id(Integer.parseInt(news_id)); // 여기까지는 comment와 news 인스턴스가 서로 관련없이 존재함.
	// 자바에서의 has a 관계가 db에서의 foreign key => oop라 그런 것! 현실세계를 표현하는 방법
	
	// 두 객체가 관련성이 전혀 없는 상태이므로, comment안으로 news를 보유시키자.
	comment.setNews(news); // 부모를 자식한테 밀어넣기
	
	
	// DAO에게 일 시키기
	int result = commentDAO.insert(comment);
	System.out.println("등록 결과 " + result);
	
	// 결과 처리
	// 클라이언트는 비동기로 요청을 시도했기 때문에 서버측에서 만일 완전한 html로 응답을 해버리면
	// 클라이언트의 의도와는 달리 동기방식을 염두에 둔 응답 정보이므로, 서버측에서는 순수 데이터형태로 응답정보를 보내야 한다.
	// 이 때 압도적으로 많이 사용되는 데이터 형태는 json이다.
	// 이유 : json은 그냥 문자열이기 때문에 모든 시스템(linux, mac, android, ios) 상관없이 시스템 중립적이므로
	if(result <1) {
		out.print("{\"resultMsg\":\"등록 실패\"}"); // 고양이가 out.print를 보고 글씨를 html로 작성할 것!! 즉, 태그가 없는 html임 => 맨 윗줄에 있는 text/html 대신 application/json으로 변형하는게 더 잘 어울림
	} else {
		out.print("{\"resultMsg\":\"등록 성공\"}");
	}
	
	%>