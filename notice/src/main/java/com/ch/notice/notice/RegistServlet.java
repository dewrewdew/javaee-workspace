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

// html로부터 글쓰기 요청을 받는 서블릿 정의
// jsp는 사실 서블릿이므로, 현재 이 서블릿의 역할을 대신할 수도 있다.
// 하지만, jsp 자체가 서블릿의 디자인 능력을 보완하기위해 나온 기술이므로
// 현재 이 서블릿에서는 디자인이 필요 없기 때문에, 굳이 jsp를 사용할 필요가 없어서 안씀!
public class RegistServlet extends HttpServlet{
	// 오라클의 경우 jdbc:oracle:thin:@localhost:1521:XE
	String url="jdbc:mysql://localhost:3306/java";
	String user="servlet";
	String password="1234";
	
	Connection con; 
	// 접속을 시도하는 객체가 아니라, 접속이 성공된 이후 그 정보를 가진 객체이므로 접속을 끊을 때 이용할 수 있다.
	// 만일 이 객체가 메모리에 올라오지 못한 경우 접속 실패로 본다!!
	PreparedStatement pstmt; // 쿼리문을 수행하는 객체 (접속이 되어야 쿼리를 실행할 수 있기 때문에 Connection 객체로부터 생성)
	
	
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
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // 드라이버 로드
			out.println("드라이버 로드 성공");
			
			
			// 접속
			con=DriverManager.getConnection(url, user, password);
			if(con==null) {
				out.println("접속 실패");
			} else {
				out.println("접속 성공");
				// 접속 성공했으므로 쿼리 실행 가능! DML(insert, update, delete) => select는 dml로 안치나?ㅁ?
				String sql="insert into notice(title, writer, content) values(?,?,?)";
				pstmt=con.prepareStatement(sql); //pstmt 인스턴스 얻기
				
				// 실행 전에 먼저 바인드 변수의 값부터 설정하자
				pstmt.setString(1, title);
				pstmt.setString(2, writer);
				pstmt.setString(3, content);
				
				// insert 실행(DML일 경우 executeUpdate() 호출, 반환값은? 쿼리에 의해 영향을 받은 레코드 수! insert는 무조건 1건씩 반영되니까 반환값 무조건 1)
				int result=pstmt.executeUpdate();
				if(result==0) {
					out.println("등록 실패");
				} else {
					out.println("등록 성공");
					
					// 브라우저로 하여금 지정한 url로 다시 재접속 하도록 명령
					response.sendRedirect("/notice/list.jsp"); // 웹브라우저는 <script>location.href=url</script>
				}
				
			}
		} catch (ClassNotFoundException e) {
			out.println("드라이버 로드 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}