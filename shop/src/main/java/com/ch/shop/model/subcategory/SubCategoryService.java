package com.ch.shop.model.subcategory;

import java.util.List;

public interface SubCategoryService {
	
	public List getList();//모든 레코드 가져오기 
	
	//상위 카테고리에 소속된 목록 가져오기 
	public List getList(int topcategory_id);
}
