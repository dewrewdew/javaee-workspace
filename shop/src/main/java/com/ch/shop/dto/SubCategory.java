package com.ch.shop.dto;

import lombok.Data;

@Data
public class SubCategory {
	private int subcategory_id;
	private String subname;
	
	//부모객체 보유 ( 데이터베이스 상에서의 join이 존재하므로 관계는 부무와 자식을 숫자로 연결시키지만, 
	//OOP에서는 has a 관계로 객체로 연결..)
	private TopCategory topCategory;
}
