package com.ch.mvcframework.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/*엔터프라이즈급의 규모가 큰 애플리케이션에서 클라이언트의 수많은 요청마다 1:1 대응하는 서블릿을 선언하고 매핑한다면,
 * 매핑 규모가 너무나 방댖하고 유지보수성이 오히려 저해된다.
 * 해결책) 앞으로 요청에 대한 매핑은 오직 하나의 진입점으로 몰아서 관리하자(예 - 대기업의 고객센터와 흡사)
 * */
public class DispatcherServlet  extends HttpServlet{
	
	// 결국 if문을 커맨드 패턴과 팩토리 패턴을 이용하여 대체하기 위한 준비물들
	FileInputStream fis;
	Properties props;
	
	// 아래의 init은 서블릿에서 인스턴스가 생성된 직후에 호출되는 서블릿 초기화 목적의 메서드이다.
	// init() 메서드 안에 명시된 매개변수인 ServletConfig은 단어에서 이미 느껴지듯
	// 이 서블릿과 관련된 환경 정보를 갖고있는 객체이다.
	public void init(ServletConfig config) {
		try {
			
			// 서블릿의 환경 정보를 가진 객체인 ServletConfig를 활용하여 현재 애플리케이션의 정보를 가진 ServletContext를 얻기
			ServletContext application =config.getServletContext();
			// 현재 웹 애플리케이션이 이클립스 내부 톰캣으로 실행될 지, 아니면 실제 서버에서 실행될 지 개발자가 알 필요 없이 현재 애플리케이션을 기준으로 파일명만 명시하면
			// 리눅스건, 맥이건, 윈도우건 상황에 맞게 알아서 경로를 반환
			String paramValue=config.getInitParameter("contextConfigLocation");
			System.out.println(paramValue);
			String realPath = application.getRealPath(paramValue);
			System.out.println(realPath);
			
			fis = new FileInputStream(realPath);
			props = new Properties();
			props.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
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
		
		/*아래의 코드에서 클라이언트의 모든 요청마다 1:1 대응하는 if문으로 요청을 처리하게 되면
		 * 역시나 요청량이 많아질 경우 유지보수성이 떨어짐.
		 * 해결책 => 각 요청을 조건문이 아닌 객체로 처리해야 함. GOF(디자인 패턴 저자들)는 Command Pattern + Factory Pattern 이라 함.
		 * Factory Pattern 이란? 객체의 생성 방법에 대해서는 감추어놓고 , 개발자로 하여금 객체의 인스턴스를 얻어갈 수 있도록 정의하는 클래스 정의기법
		 * key 와 value의 쌍으로 되어있는 map의 형태는 if문을 대신할 수 있다!!
		 * */
		
		// if(uri.equals("/movie.do")) { // 클라이언트가 영화에 대한 조언을 구함
			// 영화 전담 컨트롤러에게 요청 전달
			// MovieController controller = new MovieController();
			// controller.execute(request, response);
		// } else if(uri.equals("/food.do")) { // 클라이언트가 음식에 대한 조언을 구함
			// 음식 전담 컨트롤러에게 요청 전달
			// FoodController controller = new FoodController();
			// controller.handle(request, response);
			// String controllerPath = props.getProperty(uri);
			// System.out.println("음식에 동작할 하위 전문 컨트롤러는 " + controllerPath);
		// }
		String controllerPath = props.getProperty(uri);
		System.out.println(uri+"에 동작할 하위 전문 컨트롤러는 " + controllerPath);
		
		// 여기까지는 하위 컨트롤러의 이름만을 추출한 상태이고 실제 동작하는 클래스 및 인스턴스는 아니다.
		// 동적으로 클래스가 로드됨(static == method 영역에)
			try {
				// 클래스에대한 정보를 가진 클래스. 현재 이 클래스가 보유한 메서드명, 생성자, 속성들
				
				Class clazz=Class.forName(controllerPath); // static 영역에 동적으로 클래스의 코드 올리기
				Object obj=clazz.getConstructor().newInstance(); // static 영역에 올라온 클래스 원본코드를 대상으로 인스턴스 1개 생성하기 => new연산자만이 인스턴스를 만들 수 있는 것은 아니다.
				// 동적으로 클래스 로드(실행 전 개발자가 임의로 메서드 영역에 올려둘 수 있게하는 방식 => 메서드영역에 올려놔야 비로소 new로 인스턴스를 생성할 수 있게됨)
				
				// 메모리에 올라온 하위 커내트롤러 객체의 메서드 호출
				// obj.execute(); => 당연히 안됨. object는 최상위 클래스!!
				// 현재 시점에 메모리에 올라온 객체가 MovieController or FoodController인지 알 수 없기 때문에 이들의 최상위 객체인 Controller로 형변환한다.
				Controller controller=(Controller)obj;
				
				// 아래의 메서드 호출의 경우, 분명 부모형인 Controller형의 변수로 메서드를 호출하고는 있으나
				// 자바의 문법 규칙상 자식이 부모의 메서드를 오버라이드한경우(업그레이드한 것으로 간주하여) 자식의 메서드를 호출한다
				// 즉 자료형은 부모형이지만, 동작은 자식 자료형으로할 경우 현실의 생물의 다양성을 반영하였다고하여 다형성(Polymorphism)이라 한다.
				controller.execute(request, response); // 메서드 호출
				
				// 아래의 viewName에는 실제 jsp가 들어있느것이 아니라 검색 키만 들어있으므로
				// DispatcherServlet은 다시 servlet-mapping.txt파일을 검색하여 실제 jsp파일을 얻어 클라이언트에게 응답을 해야함
				String viewName = controller.getViewName();
				
				String viewPage = props.getProperty(viewName); // 뷰의 이름으로 jsp얻기
				System.out.println("이 요청에 의해 보여질 응답페이지는 " + viewPage);
				
				// 하위컨트롤러가 포워딩하라고 부탁한 경우엔 포워딩 처리
				if(controller.isForward()) {
					RequestDispatcher dis = request.getRequestDispatcher(viewPage);
					dis.forward(request, response);
				} else {
					response.sendRedirect(viewPage); // 클라이언트로 하여금 재접속할것을 응답 정보에 추가					
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		
		
		
	}
	// 서블릿의 생명주기 메서드중, 서블릿이 소멸할 때 호출되는 메서드인 destroy() 재정의
	// 반드시 닫아야할 자원등을 해제할 때 유용하게 사용
	public void destroy() {
		if(fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}