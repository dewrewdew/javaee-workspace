package com.ch.shop.model.topcategory;

import java.util.List;

import com.ch.shop.dto.TopCategory;

// 아래의 서비스 객체조차 컨트롤러가 보유할 때 느슨하게 보유해야 하므로 클래스가 아닌 인터페이스를 정의하자.
public interface TopCategoryService {
	
	public List getList();
}
