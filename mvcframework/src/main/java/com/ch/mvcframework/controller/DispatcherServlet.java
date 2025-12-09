package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.net.httpserver.Request;

/*엔터프라이즈급의 규모가 큰 애플리케이션에서 클라이언트의 수많은 요청마다 1:1 대응하는 서블릿을 선언하고 매핑한다면,
 * 매핑 규모가 너무나 방댖하고 유지보수성이 오히려 저해된다.
 * 해결책) 앞으로 요청에 대한 매핑은 오직 하나의 진입점으로 몰아서 관리하자(예 - 대기업의 고객센터와 흡사)
 * */
public class DispatcherServlet  extends HttpServlet{
	
	// 음식, 영화, 블로그, 음악 등등의 모든 요청을 이 클래스에서 받아야 함.
	// 이 때 요청 시 메서드가 get, post, put, delete 등 모든 종류의 요청을 다 받을 수 있어야 함.
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	
	
	// 클라이언트의 요청 방식이 다양하므로, 어떤 요청방식으로 들어오더라도 아래의 메서드 하나로 몰아넣으면 코드는 메서드(doPost, doGet) 각각 작성할 필요가 없다.
	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.print("클라이언트의 요청 감지");
		
		
		/*모든 컨트롤러의 5대 업무
		 * 1) 요청을 받는다.
		 * 2) 요청을 분석한다.
		 * 3) 알맞는 로직 객체에게 일 시키기
		 * 4) 결과는 뷰에서 보여줘야 하므로, 뷰 페이지로 가져갈 결과 저장(세션 아닌 request에)
		 * 5) 결과 페이지 보여주기
		 * */
		
		// 요청 분석(음식, 영화 등 현재 클라이언트가 요청한 유형이 무엇인지부터 파악)
		// 클라이언트가 요청 시 사용한 주소 표현식인 URI가 곧 클라이언트가 원하는 게 무엇인지에 대한 구분값이기도 하다.
		String uri = request.getRequestURI();
		System.out.println("클라이언트가 요청 시 사용한 uri는 " + uri);
		
		if(uri.equals("/movie.do")) { // 클라이언트가 영화에 대한 조언을 구함
			MovieController controller = new MovieController();
			controller.execute(request, response);
			// 영화 전담 컨트롤러에게 요청 전달
		} else if(uri.equals("/food.do")) { // 클라이언트가 음식에 대한 조언을 구함
			// 음식 전담 컨트롤러에게 요청 전달
			FoodController controller = new FoodController();
			controller.handle(request, response);
		}
		
		
		
		
		
		
		
	}
}