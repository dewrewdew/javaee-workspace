package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.food.model.FoodManager;

/* 음식에 대한 판단 요청을 처리하는 컨트롤러 
 * MVC - 개발 이론, 방법론
 * Model2 - 그 이론을 javaee 기술로 구현해놓은 모델
 * 		M : java 순수 클래스
 * 		V : JSP, HTML
 * 		C : 1) 웹서버에서 실행될 수 있어야 한다.
 * 			 2) 클라이언트의 요청을 받을 수 있어야 한다.
 * 				결론 : 서블릿밖에없음
 * 모델2의 컨트롤러의 요건 위 1, 2번
 * 
 * */
public class FoodController{
	FoodManager manager = new FoodManager();
	
	protected void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 클라이언트의 요청 파라미터 받기
		request.setCharacterEncoding("utf-8");
		
		String food = request.getParameter("food");
		
		// 모델 객체 일 시키기!!
		String msg = manager.getAdvice(food);
		request.setAttribute("msg", msg); // 요청에 대한 응답이 완료되기 전까지는 서버에서 살아있음
		
		RequestDispatcher dis=request.getRequestDispatcher("/food/result.jsp"); // 포워딩 하고싶은 URL
		dis.forward(request, response); // 포워딩 발생
		
		
	}
}
