package com.ch.memberapp.member;

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

import com.ch.memberapp.util.ShaManager;

/*회원 가입 요청을 처리하는 서블릿*/
public class RegistServlet extends HttpServlet{
	Connection con;
	PreparedStatement pstmt;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8"); // 문서 자체 문자 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		String url = "jdbc:mysql://172.30.1.10:3306/java";
		String user = "servlet";
		String password = "1234";
		
		
		/*넘겨받은 파라미터 중 비밀번호의 경우, 평문이 아닌 암호화된 상태로 변환해서 DB에 넣자*/
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		
		PrintWriter out = response.getWriter();
		
		out.print("아이디는 " + id + "<br>");
		out.print("비밀번호는 " + ShaManager.getHash(pwd) + "<br>");
		out.print("이름은 " + name + "<br>");
		
		try {
			// 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 접속
			con=DriverManager.getConnection(url, user, password);
			
			if(con==null) {
				out.print("접속 실패");
			} else {
				out.print("접속 성공");
			}
			// 쿼리문 수행
			String sql = "insert into member(id, pwd, name) values(?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, ShaManager.getHash(pwd));
			pstmt.setString(3, name);
			
			int result = pstmt.executeUpdate(); // DML
			
			StringBuffer tag = new StringBuffer();
			tag.append("<script>");
			
			if(result<1) {
				tag.append("alert('가입 실패');");
				tag.append("history.back();");
			} else {
				tag.append("alert('가입 성공');");
				tag.append("location.href='/member/login.jsp';");
			}
			tag.append("</script>");
			out.print(tag.toString());
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 자원 해제
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
