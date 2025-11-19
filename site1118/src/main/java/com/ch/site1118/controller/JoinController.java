package com.ch.site1118.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 클라이언트가 전송한 파라미터들을 받아서 오라클에 넣기
// 클라이언트의 요청이 웹 브라우저 이므로 웹상의 요청을 받을 수 있고 오직 서버에서만 실행될 수 있는 클래스인 서블릿으로 정의하자! 

public class JoinController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// doXXX형 메서드 중 post방식을 처리하기 위한 doPOST메서드 오버라이드 하자
	// web.xml에서 서블릿 매핑도 해야 함
	// 나의 이름이 웹 브라우저에 출력되게끔
	response.setContentType("text/html"); // 브라우저에게 전송할 데이터가 html 문서임을 알려줌
	response.setCharacterEncoding("utf-8"); // 이 html이 지원하는 인코딩 타입을 지정(한글 깨지지 않기 위해)
	PrintWriter out = response.getWriter(); // 응답 객체가 보유한 출력 스트림 얻기
	// 주의할 점 : 아래의 코드에 의해 클라이언트의 브라우저에 곧바로 데이터가 전송되는 게 아님
	// 추후 응답을 마무리하는 시점에 Tomcat과 같은 컨테이너 서버가 out.print()에 의해 누적되어있는 문자열을 이용하여
	// 새로운 html문서를 작성할 때 사용됨 <중요>!!! (고양이가 "참조"만 하는 문서!! 뿌리는 문서가 x)
	out.print("<h1>이슬</h1>");
	// JDBC를 오라클에 insert
	// 드라이버가 있어야 오라클을 제어할 수 있다. 따라서 드라이버 jar파일을 클래스패스에 등록하자
	// 하지만, 현재 사용중인 IDE가 이클립스라면 굳이 환경변수까지 등록할 필요없고 이클립스에 등록하면된다.
	
	// 나만의 라이브러리를 만드는것 => 환경변수를 이클립스에서 설정하는 것 뿐!
	// 드라이버 로드
	Connection con = null;
	// finallt에서 사용하기위해 변수를 밖으로 꺼냄(소멸 방지)
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		out.print("드라이버 로드 성공");
		
		// 오라클에 접속
		// url은 암기할 것!!thin도 그냥 오라클의 특징
		String url="jdbc:oracle:thin:@localhost:1521:XE";
		String user="servlet";
		String password="1234";
		// 접속 후 접속이 성공했는지 알기 위해서는, Connection 인터페이스가 null인지 여부를 판단해야 함.
		// Connection은 접속 성공 후 그 정보를 가진 객체이므로 추후 그 접속을 끊을수도 있다.
		con=DriverManager.getConnection(url, user, password);
		if(con==null) {
			out.print("접속 실패");
		} else {
			out.print("접속 성공");
		}
	} catch (ClassNotFoundException e) {
		out.print("드라이버 로드 실패");
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		if(con!=null) { // "접속이 존재할때만"의 의미
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	}
}