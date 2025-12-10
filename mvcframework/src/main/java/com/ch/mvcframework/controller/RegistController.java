package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.dto.Board;
import com.ch.mvcframework.repository.BoardDAO;

/*글쓰기 요청을 처리하는 하위 컨트롤러
 * 1) 요청을 받는다. DispatcherServlet
 * 2) 요청을 분석한다. DispatcherServlet
 * 3) 알맞는 로직 객체에 일 시킨다. 하위 컨트롤러
 * 4) 결과 페이지에 가져갈것이 있을 경우 결과를 저장(session X=>메모리 낭비, request O) 하위컨트롤러
 * 5) 컨트롤러는 디자인에 관여하면 안되므로 알맞는 view페이지를 보여주기
 * */
public class RegistController implements Controller{
	BoardDAO boardDAO = new BoardDAO();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 	3단계 : 로직 객체에게 일 시키기
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		Board board = new Board(); // empty
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		int result = boardDAO.insert(board); // 등록 시키기
		
		// 등록 후 성공 시 게시물 목록을 보여줘야 함
		// response.sendRedirect("/board/list.jsp");
	}
	
	// DispatcherServlet이 보여줘야 할 페이지 정보를 반환
	public String getViewName() {
		return "/board/regist/result";
	}

	public boolean isForward() {
		return false;
	}
	// 여기서 포워딩을 안하면 글 등록 버튼을 누른 후 목록이 보이긴 하지만 주소창에는 regist로 그대로 남아있음
	// 즉 응답하지 않아 계속 regist에 있는거라 새로고침할때마다 총 리스트 수가 늘어남
	// 따라서 항상 글 등록 후에는 접속을 다 끊고 list.do로 다시 재접속 할 수 있게 해야됨
	// 재접속은??? 응답!! 그럼 포워딩을 안하는거네!
	// DML은 보통 가져가서 보여줄게 없음. 그러나 select는 보통 값을 가져가야해서 보통 포워딩을 함

}
