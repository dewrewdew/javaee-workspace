package com.ch.shop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 이 컨트롤러는 관리자 메인 관련된 요청을 처리하는 하위 컨트롤러임
// ComponentScan에 의해 자동으로 인스턴스를 생성해달라
// 그러기 위해서는 개발자가 이 클래스가 검색될 수 있도록 제대로 된 패키지명을 등록해야 함.
@Controller 
public class AdminController {

	// 관리자 모드의 메인인 대시보드 요청을 처리
	@GetMapping("/admin/main")
	public String main() {// 반환값은 String, model and view 둘 중 하나 가능! 딱히 저장할거없으면 String으로!
		// 컨트롤러의 3단계 업무 없네!
		// 4단계도 없네!
		return "admin/index"; // http://localhost:8888/admin/main으로 접속하면 controller를 만날 수 있음!
		// 서버에서 실행되는건 오직 servlet, jsp뿐! html과 css는 서버에서 실행하지 않음 => "정적자원" 이라 함. 서버에서 그냥 보내버림 얘네들은!
	}
}
