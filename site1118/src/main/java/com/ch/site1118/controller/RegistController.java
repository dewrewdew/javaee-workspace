package com.ch.site1118.controller;

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
import javax.swing.plaf.synth.SynthOptionPaneUI;

// 회원 등록 요청을 처리할 서블릿 클래스
// HTTP 요청 방식 중, 클라이언트가 서버로 데이터를 전송해오는 방식은 POST 방식
// 따라서 HttpServlet이 보유한 doXXX형 메서드 중 doPost를 재정의해야함 
public class RegistController extends HttpServlet{
	
	// post 요청을 처리하는 메서드!
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("클라이언트의 post 요청 감지"); // 웹 브라우저가 아닌 현재 톰캣의 콘솔에 출력
		// 클라이언트가 전송한 id, pwd, name을 받아서 출력해보기
		// 한글 파라미터가 깨지지 않으려면 request 객체 인코딩을 지정해야 한다.
		request.setCharacterEncoding("utf-8"); // 파라미터 받기 전에 세팅해야 함!
		String id			=request.getParameter("id");
		String pwd		=request.getParameter("pwd");
		String name		=request.getParameter("name");
		
		System.out.println("전송받은 아이디는 " +id);
		System.out.println("전송받은 패스워드는 " +pwd);
		System.out.println("전송받은 이름은 " +name);
		
		// 응답 객체가 보유한(response객체) 문자 기반의 출력 스트림에 개발자가 유저에게 전달하고 싶은 메세지를 보관하자
		response.setContentType("text/html;"); // 브라우저에게 이 문서의 형식이 html임을 알린다.
		response.setCharacterEncoding("utf-8"); // 이 html에서 사용될 문자열에 대해 전세계 모든 언어를 깨지지 않도록 저장하는 UTF-8사용
		PrintWriter out=response.getWriter();
		
		
		// mysql에 넣어주기
		
		// java언어가 해당 데이터베이스 서버를 제어하려면, 접속에 앞서 최우선으로 해당 DB제품을 핸들링할 수 있는
		// 라이브러리인 일명 드라이버를 보유하고 있어야한다.(압축된 jar형태)
		// 보통 드라이버는 java가 자체적으로 보유할 수 없다.(java 입장에서는 어떠한 db가 존재하는지 알 수 없다.)
		// 따라서 드라이버 제작의 의무는 db제품을 판매하는 벤더사에게 있다.
		
		// jvm의 3가지 메모리영역중 Method영역에 동적으로 클래스를 Load시키는 api
		// 보통은 jvm이 자동으로 로드해주지만
		// 개발자가 원하는 시점에 원하는 클래스를 로드시킬 경우
		// 아래와같은 Class 클래스가 static 메서드인 forName() 메서드를 사용하기도 한다.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}
		
		// mysql접속!
		// 자바에서 데이터베이스를 다루는 기술을 가리켜 JDBC라(Java Database Connectivity)라 한다.
		// 이 기술은 javaSE의 java.sql 패키지에서 주로 지원함
		// 현재 우리가 개발중인 분야가 java EE라면 javaEE는 이미 javaSE를 포함하고 있다.
		// cmd를 통해 mysql을 구동하듯이 지금은 java로 mysql을 구동하는 것!
		String urlString="jdbc:mysql://127.0.0.1:3306/java"; // 암기 필수!!! jdbc:db종류:주소:포트번호/데이터베이스 이름
		String user="servlet";
		String pass="1234";
		
		Connection con = null; // finally에서 보이도록
		PreparedStatement pstmt = null; // finally에서 보이도록
		try {
			con=DriverManager.getConnection(urlString, user, pass);
			// Connection이란? 접속 성공 후 그 정보를 가진 객체이므로, 접속을 끊고 싶을 경우 이 객체를 이용하면 됨.
			// ex) con.close(); 접속해제
			
			// 주의 : jdbc에서 데이터베이스에 접속 성공 여부를 판단할때는 절대로 try문이 성공이고 catch문이 실패라고 생각하면 절대 안됨
			// getConnection()메서드가 반환하는 Connection 인터페이스가 null인지 여부로 판단해야 함!!!
			if(con == null) {
				System.out.println("접속 실패");
			}else {
				System.out.println("접속 성공");
				
				// insert문 수행
				// JDBC 객체 중 쿼리 수행을 담당하는 객체가 바로 PreparedStatement 인터페이스이다.
				// 그리고 이 객체는 접속을 성공을 해야 얻을 수 있다.(당연하지 접속을 해야 쿼리를 수행하던 말던 하니까!)
				
			
				pstmt=con.prepareStatement("insert into member(id, pwd, name) values('"+id+"','"+pwd+"','"+name+"')"); // 쿼리문 sql에서 쓰던거 그대로가져와서 ;만 떼고 작성
				
				// 중지된 쿼리문을 실행하자
				int result = pstmt.executeUpdate(); // sql과 연동하는 단계까지는 javaSE => 따라서 javaSE api문서에서 직접 검색해보기!
				// DML 메서드 실행 후 반환되는 값은 이 메서드에 의해 영향을 받은 레코드 수가 반환됨
				// 따라서 1보다 작은 수가 반환되면, 이 쿼리에 의해 영향을 받은 레코드가 없으므로 수행 실패
				if(result<1) {
					System.out.println("등록 실패");
					out.print("<script>"); // html 문서에 있는 <script>
					out.print("alert('등록실패')");
					out.print("</script>"); // html 문서에 있는 </script>
					
				} else {
					System.out.println("등록 성공");
					out.print("<script>"); // html 문서에 있는 <script>
					out.print("alert('등록성공')");
					out.print("</script>"); // html 문서에 있는 </script>
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(); // print해줘 stack구조로 내역을 trace해서 즉, 에러를 좀 뿌려줘
		}finally { // try든 catch문이든 무조건 거쳐가야하는 명령문
			if(pstmt!=null) {// 존재할때만 닫음 => 꼭 해야함!!!!!!
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