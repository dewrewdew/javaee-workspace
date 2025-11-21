package com.ch.gallery.controller;

import java.io.File;
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

import com.ch.gallery.util.StringUtil;
import com.oreilly.servlet.MultipartRequest;

// 클라이언트의 업로드를 처리할 서블릿
public class UploadServlet extends HttpServlet{
	
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="servlet";
	String pass="1234";
	
	// 클라이언트의 post 요청을 처리할 메서드 
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter(); // 응답 객체가 보유한 스트림 얻기
		// 업로드를 처리할 cos 컴포넌트를 사용해보자
		// 뭐뭐 쓸수있는지 확인하는법 api문서보기!
		// 1) 자바의 객체는 총 3가지로 나뉨. 일반클래스(안정적 new가능), 추상클래스(비교정 불안정 new불가), 인터페이스(불안정 new불가)
		// 2) 일반클래스다? 그럼 바로 생성자로 뛰어가기 => 오버로딩되어있는 생성자들 중 뭘 사용할지 골라서 new로 선언하고 사용하면 되겠다!
		// MultipartRequest 객체는 일반 클래스이므로,
		// 개발자가 new 연산자를 이용하여 인스턴스를 직접 생성할 수 있다.
		// 따라서 이 객체가 지원하는 생성자를 조사하여 사용하자
		
		// MultipartRequest는 생성자에서 업로드 처리를 하는 객체이다.
		// API에 의하면 4번째 생성자는 용량뿐만 아니라, 파일명에 한글이 포함되어 있어도 깨지지 않도록 처리가 되어있다.
		// 용량은 기본인 바이트 단위이다.(최소 단위 bit, 기본 단위 byte (bit가 8개 모인 단위)
		int maxSize=1024*1024*5;
		MultipartRequest multi= new MultipartRequest(req, "C:\\upload", maxSize, "utf-8");
		
		// 클라이언트가 전송한 데이터 중 텍스트 기반의 데이터를 파라미터를 이용하여 받아보자
		// 클라리언트가 전송한 인코딩 형식이 multipatk/form-data 일때는 기존의 파라미터를 받는 코드인
        // request.getParameter()는 동작하지 못함.. 대신 업로드를 처리한 컴포넌트를 통해서 파라미터를 뽑아내야함.
		String title = multi.getParameter("title");
		out.println("클라이언트가 전송한 제목은 " + title);
		
		// 이미 업로드된 파일은 사용자가 정한 파일명이므로, 웹브라우저에서 표현 시 불안할 수 있음
		// 해결책은? 파일명을 개발자가 정한 규칙, 또는 알고리즘으로 변경한다.
		// 방법) 예-현재시간(밀리세컨드까지 표현), 해시-16진수 문자열
		long time=System.currentTimeMillis();
		
		out.print(time);
		out.print("<br>");
		out.print("업로드 성공");
		
		// 이미지같은 파일들은 따로 빼둠(프로젝트 과부하를 막기 위해)
		// 방금 업로드된 파일명을 조사하여, 현재 시간과 확장자를 조합하여 새로운 파일명 만들기
		// 이미 업로드된 파일 정보는 파일 컴포넌트 스스로가 알고 있다. 우리의 경우 multi
		String oriName=multi.getOriginalFileName("photo"); // HTML에서 부여한 파라미터명을 매개변수로 넣어줘야 함!
		// 클래스에 커서두고 shift+f2
		// subString => String의 일부를 뜻함! string은 배열이므로 subString도 배열 접근 방식을 활용함
		out.print("<br>");
		out.print(oriName);
		
		String extend= StringUtil.getExtendFrom(oriName);
		
		out.print("<br>");
		out.print("추출된 확장자는 " + extend);
		
		// 파일명과 확장자를 구했으니, 업로드된 파일의 이름을 변경하자
		// 자바에서는 파일명을 변경하거나 삭제등을 처리하려면 javaSE java.io.File 클래스를 이용해야 한다.
		File file=multi.getFile("photo"); // 서버에 업로드된 파일을 반환해줌. 즉, new로 새로 생성할 필요가 x 이미 있는걸 가져와야하므로
		out.print("<br>");
		out.print(file);
		
		// File 클래스 메서드 중 파일명을 바꾸는 메서드 사용
		// renameTO() 메서드의 매개변수에는 새롭게 생성될 파일의 경로를 넣어야한다.
		String filename = time + "." + extend; // 여러군데에서 사용할 예정이므로, 변수로 받아놓자
		boolean result=file.renameTo(new File("C:\\upload\\" + filename)); // 새로운 파일을 생성해서 파일명 내가 원하는대로 설정하기! 윈도우만 \씀 나머진 다 /사용
		out.print("<br>");
		
		if(result) {
			out.print("업로드 성공~!");
			
			Connection con = null;
			PreparedStatement pstmt=null; // 쿼리 수행 객체
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				try {
				con=DriverManager.getConnection(url, user, pass);
				if(con==null) {
					out.println("접속 실패");
				} else {
					out.println("접속 성공");
					String sql="insert into gallery(gallery_id, title, filename) values(seq_gallery.nextval,?,?)";
					pstmt=con.prepareStatement(sql); // 접속 객체로 부터 쿼리 수행 객체 인스턴스 얻기
					
					// 쿼리문 수행에 앞서 바인드 변수값을 결정하자
					pstmt.setString(1,  title);
					pstmt.setString(2,  filename);
					
					// 쿼리 수행 DML이므로 executeUpdate() 사용해야함
					// executeUpdate는 쿼리 수행  영향을 받은 레코드 수를 반환하므로
					// 성공이라면 0이 아니어야 함.
					int n=pstmt.executeUpdate();
					if(n<1) {
						out.println("등록 실패");
					} else {
						out.println("등록 성공");
					}	// 목록으로 자동 전환
						resp.sendRedirect("/upload/list.jsp"); // 실행부가 바로 넘어가는게 아님!! 예약일뿐! 톰캣 업무 수행 과정 이해하기!!!
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				if(pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
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
		} else {
			out.print("업로드 실패~!");
		}

	}
}
