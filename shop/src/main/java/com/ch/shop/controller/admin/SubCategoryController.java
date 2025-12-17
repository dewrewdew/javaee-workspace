package com.ch.shop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ch.shop.model.subcategory.SubCategoryService;

import lombok.extern.slf4j.Slf4j;

// 쇼핑묠의 하위 카테고리에 대한 요청을 처리하는 하위 컨트롤러
@Controller
@Slf4j
public class SubCategoryController {
	
	@Autowired
	private SubCategoryService subCategoryService;
	
	// 목록 요청 처리
	// 주의) 클라이언트가 비동기 요청을 시도할 경우, 서버는 절대로 HTML 문서를 원하는 것이 아니므로 데이터를 은밀하게 보내줘야 한다.
	// 특히 개발 프로그래밍에서 표준적으로 많이 사용하는 형식이 바로 JSON 문자열이므로 적극 사용!!!
	@GetMapping("/admin/subcategory/list")
	public void getList(int topcategory_id) {
		
		List subList=subCategoryService.getList(topcategory_id);
		
		log.debug("하위 카테고리는 " + subList);
		
		// 자바 객체Json 문자열로 개발자가 직접 바꾸려면 생고생함
		// 따라서 자바분야의 라이브러리 중 jackson 라이브러리를 이용하자!!
	}
}
