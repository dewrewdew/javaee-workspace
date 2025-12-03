package com.ch.model1.singleton;

/*
 * 전세계 개발자들의 공통적 코드 패턴마다 이름을 붙여서 저서를 한 책의 이름이 Design Pattern
 * 이 책이 출간된 이후부터 개발 시 패턴의 이름을 제시하면 개발자들간 업무 소통이 원활해짐
 * 
 * SingleTon : 하나의 클래스로부터 오직 1개의 인스턴스 생성한 허용하는 클래스 정의 기법
 * */
public class Dog {
	// 클래스는 사용하기 위해서 정의했으므로,
	// 생성자를 private으로 지정한 후 아무것도 보완하지 않으면 절대로 Dog는 외부에서 사용할 수 없다.
	
	/*
	 * Dog() => 생성자를 직접 쓰지 않으면 프로그램이 자동으로 디폴트값으로 생성자를 만들어 둠.
	 * */
	
	private static Dog instance;
	
	private Dog() {
		
	}
	
	// 외부의 객체가 접근할 수 있는 일반 메서드 제공(생성자를 막았으므로)
	// 아래와 같은 메서드를 static 수식자(modifier)가 붙지 않았기 때문에
	// 인스턴스 소속 메서드가 된다.
	// 즉, 외부에서 이 메서드를 호출하려면 new로 인스턴스를 생성한 후 그걸 통해서만 접근이 가능.
	// 인스턴스를 생성해야 사용 가능해서 인스턴스 메서드라 함!

	// 싱글톤 패턴에서는 호출할때마다 new가 실행되는 이 코드가 잘못된 것!
	//	public static Dog getInstance() {
	//		instance = new Dog();
	//		return instance;
	//	}
	
	public static Dog getInstance() {
	// 인스턴스간 공유되고 있는 클래스 변수에 이미 값이 채워져 있으면
	// 인스턴스가 이미 존재하는 것이기 때문에 new를 하지 못하게 한다.
		if(instance==null) {
			instance = new Dog();
		}
		return instance;
	}
	
	
	public void bark() {
		System.out.println("멍멍");
	}
}
