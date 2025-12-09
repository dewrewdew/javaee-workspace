package com.ch.mvcframework.food.model;

public class FoodManager {
		/*모든 플랫폼에서 재사용 가능한 객체 = Model 영역 정의*/
	public String getAdvice(String food) {
		String msg="선택한 음식이 없음";
		if(food!=null){ // 파라미터가 있을때만 작동하도록
			if(food.equals("겉절이")) {
				msg="김장 끝나자마자 수육이랑 한입";
			} else if(food.equals("총각김치")){
				msg="총각김치가 알타리 김치인가";
			} else if(food.equals("김치찜")) {
				msg="졸맛";
			} else if(food.equals("파김치")) {
				msg="짜파게티랑 호로록";
			}
		}
		return msg;
	}
	
}
