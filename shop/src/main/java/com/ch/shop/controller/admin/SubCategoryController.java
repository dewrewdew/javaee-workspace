package com.ch.shop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ch.shop.dto.SubCategory;
import com.ch.shop.model.subcategory.SubCategoryService;

import lombok.extern.slf4j.Slf4j;


//쇼핑몰의 하위 카테고리에 대한 요청을 처리하는 하위 컨트롤러 
@Controller
@Slf4j
public class SubCategoryController {
	
	@Autowired
	private SubCategoryService subCategoryService;
 	
	//목록 요청 처리
	//주의) 클라이언트가 비동기 요청을 시도할 경우, 서버는 절대로 HTML문서를 원하는 것이 아니므로, 
	//데이터를 보내줘야 한다..특히 개발 프로그래밍에서 표준적으로 많이 사용하는형식이 바로 JSON문자열이므로, 적극 사용!!
	@GetMapping("/subcategory/list")
	@ResponseBody //리턴된 데이터를 응답 정보로 바로 쓰세요!(DispatcherServlet에게..)
							//또한 이 @ResponseBody 를 적용하면, json MessageConvert 를 자동적용 	
	public List<SubCategory> getList(int topcategory_id) {
		
		List subList=subCategoryService.getList(topcategory_id);
		
		log.debug("하위 카테고리는 "+subList);
		
		//자바객체를 JSON문자열로 개발자가 직접 바꾸려면, 고생한다..
		//따라서 자바분야의 라이브러리 중 jackson 라이브러리를 이용하면,  자바객체와 JSoN 문자열 간의 변환을 자동으로 해줌 
		
		//클라이언트가 비동기 요청을 시도했으므로, 서버측의 하위 컨트롤러는 jsp 매핑을 해서는 안되며, DispatcherServlet에게 
		//더이상 return 시킨 정보에 대해 jsp매핑이 아니라, 반환시킨 값 그 자체를 응답정보로 사용하라고 부탁해야 함  
		return subList;		
	}
	
}





