package com.ch.model1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/*전국 100대 산 관광정보 오픈 api의 응답 결과 중 가장 안쪽에 들어있는 item 정보를 담기위한 DTO
 * Lombok => getter, setter 자동 생성
 * */

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // json 문자열과 자바 객체간에 매핑 시, 자바 객체가 보유하지 않은 속성은 그냥 무시해!
public class Item {
	private String placeNm;
	private Double lat; // 위도
	private Double lot; // 경도
	private String frtrlNm;
}
