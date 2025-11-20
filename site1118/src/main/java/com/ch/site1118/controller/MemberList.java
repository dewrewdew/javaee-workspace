package com.ch.site1118.controller;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*오라클에 들어있는 회원의 목록을 가져와서 화면에 출력*/
public class MemberList extends HttpServlet{
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="servlet";
	String pass="1234";
	// 클라이언트인 브라우저가 목록을 달라고 요청할 것이기 때문에, doXXX형 메서드 중 doGet()을 재정의하자.
	// 클라이언트가 목록을 원하기 때문에!
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8"); // 이렇게 세미콜론으로 연결하면 아래 메서드 작성 불필요!!
		// response.setCharacterEncoding("utf-8");
		PrintWriter out =response.getWriter(); // 고양이가 html작성하기위한 도구!!
		
		Connection con= null; // 접속 후 그 정보를 가진 객체, 따라서 이 객체가 null인 경우 접속은 실패
		PreparedStatement pstmt=null; // 쿼리문 수행 객체, 오직 Connection 객체로 부터 인스턴스를 얻음
													// 쿼리문이란 접속을 전제로 하기 때문
		ResultSet rs=null; // select문의 결과인 표를 가진 객체 => 나중에 닫기 위해 밖에 선언
		// shift + F2누르면 api문서 바로 확인 가능!
		
		// 드라이버 로드
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			out.print("드라이버 로드 성공<br>");
			
			// 오라클에 접속
			con=DriverManager.getConnection(url, user, pass);
			if(con==null) {
				out.print("접속 실패<br>");
			}else {
				out.print("접속 성공<br>");
				
				String sql="select * from member member_id order by member_id asc"; // 오름차순
				pstmt=con.prepareStatement(sql); // pstmt는 con을 통해 업로드! 쿼리 수행 객체 생성
				
				// DML인 경우 executeUpdate()였지만, select문인 경우 원격지 서버의 레코드(표)를 네트워크로 가져와야하므로
				// 그 표 결과를 그대로 반영할 객체가 필요한데, 이 객체를 가리켜 ResultSet이라 한다. 직역하면 결과집합!
				// 원격지에있는 오라클의 결과 표를 그대로 가져다가 나한테 표 형태로 보여줌
				rs=pstmt.executeQuery(); // rs자체를 표라고 생각하자. rs는 포인터를 갖고있음.
				
				// rs를 그냥 표 자체로 생각해도 무방.. 하지만 rs내에 존재하는 레코드들을 접근하기 위해서는
				// 레코드를 가리키는 포인터 역할을 해주는 커서를 제어해야 한다.
				// 이 커서는 rs가 생성되자마자 즉, 생성 즉시에는 어떠한 레코드도 가리키지 않는 상태이므로
				// 개발자가 첫번째 레코드를 접근하려면 포인터를 한 칸 내려야 한다.
				
				StringBuffer tag = new StringBuffer();
				tag.append("<table width=\"100%\"border=\"1px\">");
				tag.append("<thead>");
				tag.append("<tr>");
				tag.append("<th>member_id</th>");
				tag.append("<th>id</th>");
				tag.append("<th>pwd</th>");
				tag.append("<th>name</th>");
				tag.append("<th>regdate</th>");
				tag.append("<th>email</th>");
				tag.append("</tr>");
				tag.append("</thead>");
				tag.append("</tbody>");
				// 반복문으로 모든 레코드를 출력하세요!
				while(rs.next()) {
				// System.out.println(rs.getString("id")+", " + rs.getString("pwd")+", "+rs.getString("name")+", "+rs.getString("email"));
					tag.append("<tr>");
					tag.append("<td>" + rs.getInt("member_id") + "</td>");
					tag.append("<td>" + rs.getString("id") + "</td>");
					tag.append("<td>" + rs.getString("pwd") + "</td>");
					tag.append("<td>" + rs.getString("name") + "</td>");
					tag.append("<td>" + rs.getString("regdate") + "</td>");
					tag.append("<td>" + rs.getString("email") + "</td>");
					tag.append("</tr>");
				}
				tag.append("</tbody>");
				tag.append("</table>");
				out.print(tag.toString());
				out.print("<a href='/member/join.html'>가입하기</a>");
			}
			} catch (ClassNotFoundException e) {
				out.print("드라이버 로드 실패<br>");
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
}
