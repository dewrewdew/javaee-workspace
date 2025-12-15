package com.ch.shop.test.food;

/*현실의 요리사를 정의 => is a와 has a 로 세상 모든것을 표현 가능!*/
public class Cook {
	private Pan pan; // has a 관계
	
	public Cook(Pan pan) {// 2)생성자로 DI 준비
		this.pan = pan;
		// 생성자로 초기화하고 보유하는 전형적인 자바 has a 관계 표현 방식
		// new하려는 시도 자체의 문제점? new연산자 뒤에 정확한 자료형을 따르는 생성자가 오기 때문에 아무리 has a 관계를 상위 자료형으로  느슨하게 처리해도 소용 없게 됨.
		// 해결책? 굳이 현재 클래스에서 직접 인스턴스를 생성하려 하지 말고 외부의 어떤 주체가 대신 인스턴스를 생성하여 메서드로 주입을 시켜주면 됨
		// 스프링에서는 이 외부의 주체가 바로 스프링의 애플리케이션 컨텍스트라는 객체가 담당하게됨
		// pan = new Induction();
	}
	
	
	// 주사를 맞아야하는 입장이므로 setter를 준비해두자!!
	// 이제 new로 직접 생성안하고 객체를 받아올 준비만 해둠!
	// 특정 객체를 필요로 할 때는 그 객체의 상위 자료형을 매개변수로 갖는 1)setter 또는 2)생성자를 준비하면 됨.
	public void setPan(Pan pan) { // 1)setter로 DI 준비
		this.pan = pan;
	}
	
	public void makeFood() {
		pan.boil();
	}
	
}
