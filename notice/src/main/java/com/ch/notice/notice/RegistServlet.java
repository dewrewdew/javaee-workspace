package com.ch.notice.notice;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.notice.domain.Notice;
import com.ch.notice.repository.NoticeDAO;

// html로부터 글쓰기 요청을 받는 서블릿 정의
// jsp는 사실 서블릿이므로, 현재 이 서블릿의 역할을 대신할 수도 있다.
// 하지만, jsp 자체가 서블릿의 디자인 능력을 보완하기위해 나온 기술이므로
// 현재 이 서블릿에서는 디자인이 필요 없기 때문에, 굳이 jsp를 사용할 필요가 없어서 안씀!
public class RegistServlet extends HttpServlet{

	NoticeDAO noticeDAO=new NoticeDAO();
	// 다른 로직은 포함되어 있지 않고, 오직 DB와 관련된 CRUD만을 담당하는 중립적 객체!
	// 메서드 내에 선언하면 메서드 실행할 때마다 중복 생성됨.
	
	// 클라이언트의 요청이 Get 방식일 경우, 아래의 메서드가 동작
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("요청 감지"); // 서버의 톰캣 콘솔에 출력 (우리는 톰캣이 이클립스 안에있으니 이클립스에 찍힘)
		
		// 아래의 두 줄은 jsp로 구현할 경우 page contentType="text/html;charset=utf-8"에 해당된다!
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		// 이렇게하면 톰캣이 쓰는 한글 html는 안깨지는데 파라미터로 받아오는건 아직 처리가 안되있어 여전히 파라미터값들은 깨짐
		
		// 이제 내가 보내는 한글 데이터도 깨지지 않게 해보자!
		request.setCharacterEncoding("utf-8");
		// 따라서 위 세 줄은 무조건 일단 쓰고 시작하기!!!
		
		// 클라이언트가 전송한 파라미터를 받자(제일 먼저 할 일!!!)
		String title=request.getParameter("title"); // 내부에 파라미터명 적기
		String writer=request.getParameter("writer");
		String content=request.getParameter("content");
		
		PrintWriter out = response.getWriter();
		
		out.print("클라이언트가 전송한 제목은 " +title+ "<br>");
		out.print("클라이언트가 전송한 작성자는 " +writer+ "<br>");
		out.print("클라이언트가 전송한 내용은 " +content+ "<br>");
		
		// mysql의 java db안에 notice에 insert!
		// 필요한 라이브러리(jar)가 있을 경우, 일일이 개발자가 손수 다운로드 받아 WEB-INF/lib에 직접 추가해야되는데
		// 이제는 maven 빌드툴을 사용하자!!!
		// Build - 실행할 수 있는 상태로 구축하는 것을 말함
		// src/animal.Dog.java 작성 후, bin/animal.Dog.class에 대해 직접 javac -d경로 대상 클래스
		Notice notice = new Notice(); // 텅 비어있는 상태 => noticeDAO가 귤 봉지 notice가 귤
		notice.setTitle(title);
		notice.setWriter(writer);
		notice.setContent(content);
		
		// 모두 채워졌으므로 아래의 메서드로 insert 완료.
		// 단, 반환값에 따라 성공, 실패 여부를 처리해야 한다.
		
		int result = noticeDAO.regist(notice);
		
		
		out.print("<script>");
		if(result <1) {
			out.print("alert('등록 실패');");
			out.print("history.back();");
		} else {
			out.print("alert('등록 성공);");
			out.print("location.href='/notice/list.jsp';");
		}
		out.print("</script>");
	}

}