package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.dto.Board;
import com.ch.mvcframework.repository.BoardDAO;

/*수정 요청을 처리하는 하위 컨트롤러*/
public class UpdateController implements Controller{
	BoardDAO boardDAO = new BoardDAO();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String board_id=request.getParameter("board_id");
		String title=request.getParameter("title");
		String writer=request.getParameter("writer");
		String content=request.getParameter("content");
		
		//System.out.println("board_id=" + board_id);
		//System.out.println("title=" + title);
		//System.out.println("writer=" + writer);
		//System.out.println("content=" + content);
		
		// 3단계 : DAO에게 일 시키기
		Board board = new Board();
		board.setBoard_id(Integer.parseInt(board_id));
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		boardDAO.update(board);
		// 수정이 데이터베이스에 반영된 상태이지만 아직 유저가 그 수정된 내용을 보지 못했기 때문에
		// 컨트롤러 중 상세보기 요청을 처리하는 DetailController에게 재접속하게 만들면 된다.
		// 금지사항) 현재 이 UpdateController에서 상세내용 보기를 가져와서 포워딩해도 프로그램은 돌아는 가지만
		// MVC프레임워크에서 UpdateController는 이름에서도 알 수 있듯 수정만을 담당해야 함에도 굳이 상세내용까지 가져와버리면
		// 모든 요청에 대한 업무를 처리하는 컨트롤러가 1:1대응한다는 원칙을 깨버리는 것임
		// 세탁기에 대한 수리요청을 했으나 스마트폰까지 수리해버림.
	}

	@Override
	public String getViewName() {
		return "/board/update/result";
	}

	@Override
	public boolean isForward() {
		return true; // board_id를 살리기 위한 꼼수!!! select쓰지말고 board_id만 뽑아쓰자! 이렇게 되면 화면 url은 edit.do지만 내용은 detail.jsp가 됨!! 새로고침할때마다 edit.do가 실행됨!
	}
	
}
