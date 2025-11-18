package com.ch.site1118.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		// mysql에 넣어주기
	}
}