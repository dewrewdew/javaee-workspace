package com.ch.shop.test.food;


/*현실의 프라이팬을 정의*/
public class FriPan implements Pan{ // implements는 is a 관계(sun사에서 정의)

	public void boil() {
		System.out.println("음식을 불로 데워요");
	}
}
