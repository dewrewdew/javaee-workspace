package com.ch.shop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ch.shop.dto.Size;
import com.ch.shop.model.size.SizeService;

@Controller
public class SizeController {
	
	@Autowired
	private SizeService sizeService;
	
	@GetMapping("/size/list")
	@ResponseBody
	public List<Size> getList(){	
		return sizeService.getList();
	}
	
}







