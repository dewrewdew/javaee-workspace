package com.ch.shop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ch.shop.dto.TopCategory;
import com.ch.shop.model.topcategory.TopCategoryService;

//상위 카테고리와 관련된 요청을 처리하는 하위 컨트롤러
@Controller
public class TopCategoryController {
	
	@Autowired
	private TopCategoryService topCategoryService;
	
	@GetMapping("/topcategory/list")
	@ResponseBody
	public List<TopCategory> getList(){
		return topCategoryService.getList();
	}
}






