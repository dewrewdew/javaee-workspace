package com.ch.model1.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.prefs.BackingStoreException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.model1.dto.Board;
import com.ch.model1.repository.BoardDAO;

// 수정 요청을 처리하는 서블릿
public class EditServlet extends HttpServlet {
		BoardDAO boardDAO = new BoardDAO(); // jsp의 선언부와 동일한 목적
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*파라미터 4개를 넘겨받아 쿼리를 직접 실행하지 말고, DAO에게 시키자!=> 왜? 유지보수성 높이려고!!*/
		response.setContentType("text/html;charset=utf-8"); // 응답 페이지에 대한 인코딩!
		request.setCharacterEncoding("UTF-8"); // 파라미터 값에 대한 인코딩!
		PrintWriter out = response.getWriter();
		
		String board_id = request.getParameter("board_id");
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		// Tomcat의 콘솔에 출력 (우리의 경우 내부 톰캣이므로 이클립스 콘솔에 출력 => 페이지에 출력되지 않으니 사용자는 볼 수 없음.)
		System.out.println(board_id);
		System.out.println(title);
		System.out.println(writer);
		System.out.println(content);
		
		// update board set title=?, writer=?, content=? where board_id=? board_id가 없으면 전체가 다 수정됨! 그래서 눈에 보이는 변수는 3개지만 id까지 필요한건 총 4개!
		
		// 매개변수로 낱개의 파라미터로 보내지 말고, Board DTO로 모아서 전달하자! 그러니까 transfer object!
		Board board = new Board(); // 텅 빈 상태!
		board.setBoard_id(Integer.parseInt(board_id));
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		int result = boardDAO.update(board);
		
		out.print("<script>");
		if(result > 0) { // 성공처리
			out.print("alert('수정 성공');");
			out.print("location.href='/board/detail.jsp?board_id="+board_id+"';");
		} else { // 실패
			out.print("alert('수정 실패');");
			out.print("history.back();"); // BOM내장 객체 중 history 객체
		}
		out.print("</script>");
		
	}
}
