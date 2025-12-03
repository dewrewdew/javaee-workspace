package com.ch.model1.singleton;

import java.security.PublicKey;

public class UseDog {
	public static void main(String[] args) {
		
		// Dog d1 = new Dog(); // 여기서의 Dog()은 시스템이 자동으로 만들어준 생성자!
		
		// 강아지 클래스가 현재 생성자를 막아놓았으므로 new를 통한 인스턴스 생성을 통해 접근할 방법은 없다.
		// 따라서 강아지가 제공하는 static 메서드 즉, 클래스 메서드들을 통해 강아지 인스턴스를 얻어오자.
		Dog d1=Dog.getInstance();
		System.out.println("d1=" + d1);
		d1.bark();
		d1.bark();
		d1.bark();
		
		Dog d2=Dog.getInstance();
		System.out.println("d2=" + d2);
	}
}
