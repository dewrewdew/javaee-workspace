package com.ch.mvcframework.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.repository.BoardDAO;

public class ListController implements Controller{
	
	BoardDAO boardDAO = new BoardDAO();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 3단계 : 알맞는 로직 객체에 일을 시킨다.
		List list = boardDAO.selectAll();
		
		request.setAttribute("list", list); // 컨트롤러는 디자인과 직접적으로 관여할 방법이 x 다시 dispatcher한테 보내고 그걸 dispatcher가 디자인 페이지로 포워딩 해줌!
		// 단, 포워딩 해야할지 말아야할지를 같이 알려줘야 함!!
		// 결과를 화면까지 가져가야 할 때 => 무조건 포워딩(응답하면 거기서 끝나버림)
	}

	// 현재 컨트롤러에서는 디자인 관련한 응답을 해서도 안되고 클라이언트에게 특정 페이지로 재접속하라는 응답 정보조차 보내면 안됨
	// 즉, 클라이언트와의 응답정보에대한 처리는 전면부에 나선 DispatcherServlet이 담당하기 때문.
	// 그러면 하위 컨트롤러에서는 무엇을 담당하는가? DispatcherServlet이 보여줘야할 뷰페이지에 대한 정보만 반환하면 됨
	// 또한, 뷰페이지에 대한 정보 반환 시 왜 .jsp파일을 직접 명시하지 않는가?
	// 파일명이 변경되었을 때 영향을 받지 않기 위해서! 즉, 전통적으로 유지보수성을 높이기 위해서는 자바 클래스내에 자원의 주소등은 하드코딩하지 않는 습관을 가질 것.
	public String getViewName() {
		return "/board/list/result";
	}
	// jsp까지 살려서 가져갈 데이터가 있다면 묻지도 따지지도 말고 포워딩 해야한다. 즉, 저장할 데이터가 있다면 포워딩해야된다고 판단하면 됨!! request객체 죽기전에 저장해야 하니까
	public boolean isForward() {
		return true;
	}
	
}
