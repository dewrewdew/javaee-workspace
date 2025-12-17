package com.ch.shop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ch.shop.model.topcategory.TopCategoryService;

/*쇼핑몰의 관리자에서 상품과 관련된 요청을 처리하는 하위 컨트롤러*/
@Controller
public class ProductController { // 그냥 페이지만 볼 때는 get!
	
	// 서비스 보유 (느슨하게 보유)
	@Autowired
	private TopCategoryService topCategoryService;
	
	// 상품 등록 폼 요청 처리
	@GetMapping("/admin/product/registform") // => 가상의 매핑!! 웹상에서 검색해서 들어오기 위함 web-inf로 직접 치고 들어올 수 없으니까!
	public String getRegistForm(Model model) {
		// 3단계 : 상품 페이지에 출력할 상위 카테고리 가져오기
		List topList = topCategoryService.getList();
		// 4단계 : 결과 저장(request 직접 해야 하지만 스프링에서는 Model객체를 이용하면 간접적으로 저장이 됨)
		// jsp까지 topList를 살려서 가야하므로 포워딩 처리해야함. 스프링 개발자가 redirect를 명시하지 않으면 디폴트가 포워딩
		model.addAttribute("topList", topList); // request.setAttribute("topList", topList);와 동일 효과
		return "admin/product/regist"; // WEB-INF ~~~ 여기서 접두사 접미사 다 떼고 알맹이만 보내기!
	}

}
