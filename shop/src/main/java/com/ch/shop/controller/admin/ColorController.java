package com.ch.shop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ch.shop.dto.Color;
import com.ch.shop.model.color.ColorService;

//관리자측의 색상과 관련된 요청을 처리하는 컨트롤러
@Controller
public class ColorController {
	
	@Autowired
	private ColorService colorService;
	
	//클라이언트가 비동기방식으로 색상을 원하므로, 서버측에서는 jsp로 렌더링 결과인 Html 문서로 응답하면 안됨!!
	//JSON문자열로 응답해야 한다..
	//원래는 개발자가 String 응답문자열을 보내야 하지만, 고생스러우므로, 스프링에게 자바객체를 JSON 문자열로 변환해주기를 맡기자
	//이 기능을 구현하려면? @ResponseBody를 명시하되, 컨버터(Converter)가 등록되어 잇어야 한다
	@GetMapping("/color/list")
	@ResponseBody
	public List<Color> getList(){ 
		return colorService.getList();
	}
	
}






