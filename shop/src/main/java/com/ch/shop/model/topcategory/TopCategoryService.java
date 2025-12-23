package com.ch.shop.model.topcategory;

import java.util.List;

/*
 * 아래의 서비스 객체 조차, 컨트롤러가 보유할때 느슨하게 보유해야 하므로, 인터페이스를 정의하자
 * */
public interface TopCategoryService {
	
	public List getList();
}
