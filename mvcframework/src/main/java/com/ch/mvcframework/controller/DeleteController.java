package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.repository.BoardDAO;

/*삭제 요청을 처리하는 하위 컨트롤러
 * 하위 컨트롤러이므로 컨트롤러 업무 단계중 3(일시키기), 4단계(결과가있다면 결과 저장 => select수행시에 주로 수행 dml할때는 없음 즉, 4단계는 선택사항!)
 * 4단계가 생략되었다는 것은 view로 가져갈 것이 없으므로 클라이언트로 하여금 재접속해도 된다는 것임
 * isForward = false로 처리, 즉 재접속하라는 뜻!
 * ex) 삭제 처리가 되면 클라이언트는 list.do로 재접속하여 갱신된 게시물을 보면됨
 * */
public class DeleteController implements Controller{

	@Override
	// 3단계 : 일 시키기
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDAO boardDAO = new BoardDAO();
		String board_id = request.getParameter("board_id");
		
		int result = boardDAO.delete(Integer.parseInt(board_id));
		
		//4단계??? 없음!! 따라서 포워딩할필요가 없음!!!
		
	}

	@Override
	public String getViewName() {
		return "/board/delete/result";
	}

	@Override
	public boolean isForward() {
		return false;
	}

}
