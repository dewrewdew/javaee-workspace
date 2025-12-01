package com.ch.model1.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.model1.dto.Board;
import com.ch.model1.repository.BoardDAO;

// 글쓰기 요청을 처리하는 서블릿
public class RegistServlet extends HttpServlet{
	// 서블릿의 생명 주기에서 인스턴스는 최초의 요청에 의해 1번만 생성되므로
	// 서블릿의 멤버 변수로 선언한 객체는 1번만 생성됨!
	BoardDAO boardDAO = new BoardDAO();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// jsp의 page 지시영역과 동일한 코드
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		// BoardDAO boardDAO = new BoardDAO(); => 여기서 선언해도 오류는 나지 않지만 누가 여러번 누르면 계~~속 생성됨. 메모리 엄청 잡아먹음!!
		// 넘겨받은 파라미터를 이용하여 이제부터는 DB에 직접 넣지 않고 전담 객체에게 시킬 것!
		// 별도의 데이터베이스 처리 객체를 정의해야 하는 이유 => DB코드의 재사용때문! (코드를 잘 작성해두면 로컬에서도 사용하고 서버에서도 사용 가능 => 유지보수 시간 단축)
		// 다른 로직은 포함시켜서는 안되며 오직 DB 관련된 CRUD만을 담당하는 객체를 가리켜 DAO(Data Access Object)
		
		
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		// 파라미터를 db에 넣기 => DAO를 사용하자!!
		// insert 메서드를 호출하기 전에 낱개로 존재하는 파라미터들을 DTO에 모아서 전달하자
		// RegistServlet has a BoardDAO
		// 자바의 객체와 객체 사이의 관계를 명시할 수 있는데, 단, 2가지 유형으로 나눈다.
		// 자바에서 특정 객체가 다른 객체를 보유한 관계를 has a 관계라 한다.
		// 자바에서 특정 객체가 다른 객체를 상속받는 관계를 is a 관계라 한다.
		Board board = new Board(); // DTO 생성, 이 시점엔 텅 비워져 있음.
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		
		int result = boardDAO.insert(board);
		
		
		PrintWriter out = response.getWriter();
		
		out.print("<script>");
		if(result <1) {
			out.print("alert('등록 실패');");
			out.print("history.back();");
		} else {
			out.print("alert('등록 성공');");
			out.print("location.href='/board/list.jsp';");
		}
		out.print("</script>");
	}
}
