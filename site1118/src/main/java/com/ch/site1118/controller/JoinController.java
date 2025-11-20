package com.ch.site1118.controller;

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

import com.ch.site1118.util.EmailManager;

// 클라이언트가 전송한 파라미터들을 받아서 오라클에 넣기
// 클라이언트의 요청이 웹 브라우저 이므로 웹상의 요청을 받을 수 있고 오직 서버에서만 실행될 수 있는 클래스인 서블릿으로 정의하자! 

public class JoinController extends HttpServlet{
	
	EmailManager emailManager=new EmailManager(); // null
	
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
	// finally에서 사용하기위해 변수를 밖으로 꺼냄(소멸 방지)
	PreparedStatement pstmt = null;
	// 쓰고 닫아야되니까 밖으로
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
			// 쿼리 수행 PreparedStatement 인터페이스가 담당
			// JDBC는 데이터베이스 제품의 종류가 무엇이든 상관없이 DB를 제어할 수 있는 코드가 동일함
			// 일관성 유지 가능
			// 가능한 이유? 사실 JDBC 드라이버를 제작하는 주체는 벤더사이기 떄문에 모든 벤더사는 java 언어를 제작한 오라클사에서 제시한
			// JDBC 기준 스펙을 따르기 때문에 가능하다.
			// 참고로 우리가 javaEE시간에 별도의 개발 툴 킷을 설치할 필요가 없었던 이유는?
			// 오라클사는 javaEE에 대한 스펙만을 명시하고 실제 서버는 개발하지 않는다.
			// 결국 javaEE 스펙을 따라 서버를 개발하는 벤더사들 모두가 각자 고유의 기술로 서버는 개발하지만
			// 반드시 javaEE에서 명시된 객체명을, 즉, api명을 유지해야 하므로
			// java 개발자들은 어떠한 종류의 서버이던 상관없이 그 코드가 언제나 유지됨(즉, 종류상관없이 동일한코드 사용)
			String sql="insert into member(member_id, id, pwd, name, email)";
			sql+=" values(seq_member.nextval,?,?,?,?)"; // ?는 바인드 변수라 함
			pstmt=con.prepareStatement(sql);
			// 바인드 변수를 사용하게 되면 물음표의 값이 무엇인지 개발자가 PreparedStatement에게 알려줘야 함!
			// 클라이언트가 전송한 파라미터 받기
			// 네트워크로 전송된 모든 파라미터는 모두 문자열로 인식됨! 숫자 문자 상관없고 무조건 문자열! 뭘 입력하든 무조건 문자열로 날라감!!
			// 전송 파라미터의 인코딩을 지정해야 한글등이 깨지지 않는다.
			request.setCharacterEncoding("utf-8"); // 요청 정보를 가진 객체인 request에게 인코딩 지정
			String id 		= request.getParameter("id");
			String pwd 		= request.getParameter("pwd");
			String name		= request.getParameter("name");
			String email 	= request.getParameter("email");
			
			//PreparedStatement에게 쿼리문에 사용할 바인드 변수값을 알려주기
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			
			//쿼리문 실행
			int result=pstmt.executeUpdate(); // DML(insert, update, delete) 수행 시 사용하는 메서드
			// executeUpdate()는 반환값이 int, 그리고 이 int의 의미는 현재 쿼리문에 의해 영향을 받은 레코드의 수를 반환
			// ex) insert는 무조건 1건씩 반영되므로 성공하면 결과값은 1 update, delete는 조건에 따라 영향받는 레코드의 수가 다름 그래서 n이 반환
			// 0이 반환되면 한건도 반영된 레코드가 없다는 의미로 쿼리 반영 실패를 의미!
			if(result!=0) {
				out.print("가입 성공~!");
				emailManager.send(email);
				
				// 회원 목록 페이지 보여주기
				response.sendRedirect("/member/list"); // 브라우저로 하여금 지정한 url로 다시 들어오라는 명령
				// response에다가 redirect 문을 저장한 것 뿐! 코드 분기가 아님!
				// 이때 톰캣은 프린트라이터 + response객체에만 관심있음 (왜냐면 보고 써야하니까)
				// service->doPost를 거쳐서 개발자가 코딩해둔 내용으로 각종 print나 write할일들과 response객체들이 쌓이니까
				// 고양이가 일을 시작함.
				// 그때 이제 고양이가 일을해서 브라우저에 응답을 줌과 동시에 연결을 다 끊어버림
				// 동시에 모든 변수와 실행문과 스레드와 request, response들을 다 삭제해버림 => 고양이가 숙청시작!
				// 그다음에 브라우저 입장에서는 member/list로 가라는 요청이있었으니까 브라우저에서 member/list로 가서 테이블을 출력요청을 보내면서
				// 다시 이제 서버에서 양손에 request, response를 든 스레드가 생기면서 다시 시작되는 것!
				
				
			} else {
				out.print("가입 실패~!");
			}
			
			// 꿀팁! 반환형을 적고 나서 .을 쓰면 관련 메서드들이 추천됨!
		}
	} catch (ClassNotFoundException e) {
		out.print("드라이버 로드 실패");
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
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