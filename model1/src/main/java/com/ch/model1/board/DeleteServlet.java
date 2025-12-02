package com.ch.model1.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.model1.repository.BoardDAO;

// 삭제 요청을 처리하는 서블릿
// get?post? => 쿼리문으로 판단!!!
// delete from board where board_id=8;
// get 써도되는데 이미 hidden id가 있기때문에 form 통채로 그냥 보내버리자!
public class DeleteServlet extends HttpServlet{
	BoardDAO boardDAO = new BoardDAO();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8"); //이건 한글로 등록 됐다 안됐다 표기하기 위해 인코딩!
		// request 인코딩은? 어차피 form태그 중 id만쓸거라 필요 x
		
		// 파라미터 받기(파라미터 값에서 꺼내서 쓸 값에 한글은 없으므로 굳이 request.setCharacterEncoding()처리는 불필요
		String board_id = request.getParameter("board_id");
		
		// DAO에게 일 시키고 삭제 처리
		int result = boardDAO.delete(Integer.parseInt(board_id));
		
		StringBuffer tag = new StringBuffer();
		tag.append("<script>");
		if(result < 1){
			tag.append("alert('삭제 실패');");
			tag.append("history.back();");
		} else {
			tag.append("alert('삭제 성공');");
			tag.append("location.href='/board/list.jsp';");
		}
		tag.append("</script>");
		
		PrintWriter out = response.getWriter();
		out.print(tag.toString());
	}
}
