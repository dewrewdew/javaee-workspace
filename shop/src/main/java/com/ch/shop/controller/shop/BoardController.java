package com.ch.shop.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ch.shop.dto.Board;

/*우리가 제작한 MVC 프레임워크에서는 모든 요청마다 1:1 대응하는 컨트롤러를 매핑하는 방식이였으나, 
 * 스프링 MVC는 예를들어 게시판 1개에 대한 목록, 쓰기, 상세보기, 수정, 삭제에 대해 하나의 컨트롤러로 처리가 가능함.
 * 왜? 모든 요청마다 1:1 대응하는 클래스 기반이 아니라, 메서드 기반이기 때문*/
@Controller
public class BoardController {
	
	// 글쓰기 폼 요청 처리 - jsp가 WEB-INF 밑에 위치하였으므로 브라우저에서 jsp를 직접 접근할 수 없다.
	// 따라서 아래의 컨트롤러의 메서드에서 /board/write.jsp를 매핑 걸자
	@RequestMapping("/board/registform")
	public ModelAndView registForm() {
		// 3단계 : 일 시킬게 없다
		// 4단계 : 없다
		// DispatcherServlet에게 완전한 jsp경로를 반환하게되면 파일명이 바뀔 때 이 클래스도 영향을 받으므로
		// 무언가 jsp를 대신할만한 키 등을 구항해야하는데 스프링의 창시자인 로드 존슨은 접두어, 접미어를 활용하는 방식을 고안해 냄
		// 따라서 개발자는 전체 파일명 경로 중 변하지 않는다고 생각하는 부분에 대해 접두어, 접미어를 규칙으로 정하여 알맹이만 반환하는 방법을 쓰면 됨
		// 이 때 하위컨트롤러가 DispatcherServlet에게 정보를 반환할때는 String형으로 반환해도 되지만 ModelAndView라는 객체를 이용할 수도 있다.
		// 참고로 ModelAndView에 데이터를 담을때는 Model객체에 자동으로 담기고, jsp접두어, 접미어를 제외한 문자열을 넣어둘때는 view객체에 담기는데
		// MOdelAndView는 이 두 객체를 합쳐놓을 객체임
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/write");
		return mav; //board/result/registform
	}
	
	// 글 목록 페이지 요청 처리
	@RequestMapping("/board/list")
	public ModelAndView getList() {
		// 3단계 수행
		System.out.println("클라이언트의 목록 요청 감지");
		
		// 4단계 수행 => 결과 저장
		return null;
	}
	
	// 글쓰기 요청 처리
	// 메서드의 매개변수에 VO(Value Object)로 받을 경우 스프링에서 자체적으로 자동 매핑에 의해 파라미터값들을 채워넣는다
	// 단, 전제 조건? 파라미터명과 VO의 변수명이 반드시 일치해야 한다.
	// DTO와 VO는 비슷하기는 하지만, DTO는 테이블을 반영한 객체이다 보니 클라이언트에 노출되지 않도록 하는 것이 좋기때문에
	// 단순히 클라이언트의 파라미터를 받는 것이 목적이라면 DTO보다는 VO를 사용해야 한다.
	@RequestMapping("/board/regist")
	public ModelAndView regist(Board board) {
		System.out.println("제목은 " + board.getTitle());
		System.out.println("작성자는 " + board.getWriter());
		System.out.println("내용은 " + board.getContent());
		
		
		
		return null;
	}
	
	// 글 상세보기 요청 처리
	
	// 글 수정 요청 처리
	
	// 글 삭제 요청 처리
}
