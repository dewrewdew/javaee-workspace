package com.ch.shop.dto;

import lombok.Data;

@Data
public class SubCategory {
	private int subcategory_id;
	private String subname;
	
	// 부모 객체 보유하자(데이터베이스 상에서의 관계는 join이 존재하므로 관계를 부모와 자식을 숫자로 연결시키지만 oop에서는 객체로 연결)
	private TopCategory topCategory;
}
