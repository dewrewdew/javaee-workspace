package com.ch.model1.dto;

import java.util.List;

import lombok.Data;


@Data
public class Items {
	
	// 검색 결과인 item 배열 여러개의 자식을 아래와 같이 List로 표현
	private List<Item> item; 
}