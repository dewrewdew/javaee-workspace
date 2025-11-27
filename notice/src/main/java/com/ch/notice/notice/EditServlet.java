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

// 클라이언트의 수정 요청을 처리하는 서블릿
// 수정 내용폼의 데이터가 규모가 크기 때문에 Http 전송 메서드 중 POST로 요청이 들어올 것
public class EditServlet extends HttpServlet{
	
	String url="jdbc:mysql://localhost:3306/java";
	String user="servlet";
	String password="1234";
	
	Connection con;
	PreparedStatement pstmt;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8"); // jsp의 지시영역과 동일!
		PrintWriter out=response.getWriter(); // tomcat이 html 작성 시 사용할 내용을 담을 문자 기반 출력 스트림
		request.setCharacterEncoding("utf-8"); // 전송되는 데이터에 대한 인코딩 처리
		// 클라이언트가 전송한 파라미터들을 이용하여 쿼리문 수행
		// DML 중 수정 SQL - update notice set title=넘겨받은 파라미터, writer=파라미터 값, content=파라미터 값
		// 						where notice_id=파라미터 값
		String title=request.getParameter("title");
		String writer=request.getParameter("writer");
		String content=request.getParameter("content");
		String notice_id=request.getParameter("notice_id");
		
		out.println("title="+title);
		out.println("writer"+writer);
		out.println("content="+content);
		out.println("notice_id="+notice_id);
		// String sql="update notice set title=?, writer=?, content=? where notice_id?";
		
		try {
			// 드라이버 로드
				Class.forName("com.mysql.cj.jdbc.Driver");
			// 접속
				con=DriverManager.getConnection(url, user, password);
			//쿼리 수행
				String sql="update notice set title=?, writer=?, content=? where notice_id=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, writer);
				pstmt.setString(3, content);
				/*
				 * java에서 객체 자료형과 기본 자료형간에도 형 변환을 지원해주는 객체가 있다.(wrapper클래스)
				 * java의 기본 자료형마다 1:1 대응하는 객체이다.
				 * byte - Byte
				 * short - Short
				 * int - Integer "8"이라는 스트링 객체를 기본 자료형인 8로 바꾸려면 방법이 없다. 따라서 래퍼 클래스로 객체에서 기본자료형을 변환
				 * long - Long
				 * float - Float
				 * double - Double
				 * */
				pstmt.setInt(4, Integer.parseInt(notice_id)); // notice_id 값 "8" => 8로 바꿔줘야함(문자열로인식하므로)
				int result=pstmt.executeUpdate(); // DML 쿼리 수행
				
				out.print("<script>");
				if(result<1) {
					out.print("alert('수정 실패');");
					out.print("history.back();"); // 세미콜론 빠지지 않도록 주의 (브라우저 먹통됨)
				} else {
					out.print("alert('수정 성공');");
					// 사용자가 방금 보았던 상세글로 다시 이동하게끔 처리
					out.print("location.href='/notice/detail.jsp?notice_id="+notice_id+"'");
				}
				out.print("</script>");
				
				//
				/*int x=5;
				Integer yInteger=5;
				x=y;
				*/
				// 5가 자동으로 Integer 객체처리 되었다 하여 즉 무언가 객체로 포장을 했다고 Boxing
				// 그 반대의 현상을 가리켜 unBoxing
				
				
				
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(content !=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
