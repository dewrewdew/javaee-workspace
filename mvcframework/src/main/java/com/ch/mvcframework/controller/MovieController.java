package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.movie.model.MovieManager;

/*MVC란? Model, View, Controller를 의미하는 디자인 패턴 중 하나를 의미
 * MVC는 다운로드 받거나 눈에 보이는 파일이나 소스가 아니라 그냥 전산분야에서 예전부터 선배들로부터 내려오는 개발 방법 이론일 뿐이다.
 * 
 * MVC 주요내용?
 * 디자인 영역과 로직(모델)영역은 완전히 분리시켜야 유지보수성이 좋아진다.
 * 
 * Model2란?
 * 		JavaEE분야에서 구현한 MVC 패턴을 가리킨다.
 * 		즉, javaEE 분야(웹) 에서 애플리케이션을 개발할 때 디자인과 로직을 분리시키기 위해 사용하여야 할 클래스 유형은 아래와 같다.
 * 		M - 중립적인 모델이므로 순수 java 클래스가 적절
 * 		V - 웹상의 디자인을 표현해야 하므로, html, jsp로 작성
 * 		C - 클라이언트의 요청을 받아야 하고, 오직 Java EE 서버에서만 실행될 수 있어야 하므로 서블릿
 * 			주의) jsp로 사실 서블릿이므로 Controller역할을 수행할 수는 있지만, jsp가 주로 디자인에 사용되므로, 컨트롤러로서의 역할은 주로 서블릿으로 구현함.
 * 
 * MVC 방법론을 따라 웹상에서 구현한 게 Model2라고 생각하면 됨!
 * */
public class MovieController implements Controller{
	MovieManager manager = new MovieManager();
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String movie = request.getParameter("movie");
		// out.print(movie); => 이 클래스의 목적은 컨트롤러이므로 더이상 디자인 영역을 침해해서는 안됨.
		// 즉, 여기서 out.print()를 시도한다는 것은 MVC 중 View 영역을 침범하게됨(월권임!!)
		String msg=manager.getAdvice(movie);
		//변수가 여기있으면 doPost가 끝날때 같이 끝남 => 이걸 result.jsp로 전달할 수 있는 방법을 고민해보자!!
		// 서로 다른 서블릿끼리 연결하고 그런 기능은 없음. 그럼 변수를 어떻게 이어서 사용할까?
		// 내가 원하는곳으로 재접속하게 클라이언트에게 지시!!
		
		// 이때 이 요청과 연관된 세션이 드디어 생성되면서 자동으로 sessionID가 발급됨
		// 또한 응답 정보 생성 시 클라이언트에게 쿠키로 session ID가 함께 전송됨
		// 쿠키 - 영구적(Persistence cookie=하드디스크), 세션쿠키(메모리) => 서버에서 발급받은건 세션쿠키
		
		// HttpSession session = request.getSession();
		
		// 영화에 대한 판단 결과는 세션이 죽을때까지 함께 생존할 수 있으므로
		// 이 요청이 종료되어도, 그 값을 유지할 수 있다.
		// session.setAttribute("msg", msg);
		
		// 세션말고도 데이터를 전달하는 또 다른 방법이 있는 포워딩을 이용해보자
		// 현재 들어온 요청에 대해 응답을 하지 않은 상태로 또 다른 서블릿에 요청을 전달함
		// 이때 지정된 result.jsp 의 서블릿의 service()메서드가 호출됨
		request.setAttribute("msg", msg); // 세션과 생명 유지 시간만 다를뿐 사용방법은 같다.
		RequestDispatcher dis=request.getRequestDispatcher("/movie/model2/result.jsp"); // 포워딩하고 싶은 자원의 url
		// 현재 서블릿에서 응답을 처리하지 않았기 때문에 request는 죽지않고 result.jsp의 서블릿까지 생명이 유지됨
		
		dis.forward(request, response);
		
		// response.sendRedirect("/movie/model2/result.jsp"); => 이 코드는 응답을 하면서 재접속하라고 지시하는 코드. 따라서 응답하지않고 포워딩하는 이 코드에는 어울리지 않음!
		// 응답을 해버리면 요청객체/응답객체 등등이 다 사라져버림
		// <script>location.href=url</script>
		// 위의 판단 결과를 여기서 출력하면 MVC위배됨 따라서 판단 결과를 별도의 디자인영역에서 보여줘야 함.
		
	}

	@Override
	public String getViewName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isForward() {
		// TODO Auto-generated method stub
		return false;
	}

}
