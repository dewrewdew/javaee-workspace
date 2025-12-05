package com.ch.model1.news;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.model1.dto.News;
import com.ch.model1.repository.NewsDAO;


public class RegistServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NewsDAO newsDAO = new NewsDAO();
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); // 파라미터 안깨지게
		// 클라이언트가 동기방식으로 전송한 파라미터를 받아서 데이터베이스(DAO를 이용하여 간접적으로) 시키자
		String title=request.getParameter("title");
		String writer=request.getParameter("writer");
		String content=request.getParameter("content");
		
		PrintWriter out = response.getWriter();
		out.print("title=" + title);
		out.print("writer ="+writer);
		out.print("content=" + content);
		       
		// DAO에게 일 시키기
		News news = new News();
		news.setTitle(title);
		news.setWriter(writer);
		news.setContent(content);
		int result = newsDAO.insert(news);
		
		
		// 클라이언트가 동기 방식으로 요청을 했기 때문에 서버는 화면 전환을 염두에두고
		// 순수 데이터보다는 페이지 전환 처리가 요구됨
		// 글 등록 후 , 클라이언트의 브라우저로 하여금 다시 목록 페이지를 재요청하도록 만들자
		// response.sendRedirect(""); // 이 코드 대신 location.href=""를 사용해도 동일 효과를 나타냄
		// 결과를 출력 성공(목록보이게) or 실패(뒤로가기)
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		if(result <1) {
			sb.append("alert('등록 실패');");
			sb.append("history.back();");
		} else {
			sb.append("alert('등록 성공');");
			sb.append("location.href='/news/list.jsp';");
		}
		
		sb.append("</script>");
		out.print(sb.toString());
	}
}
