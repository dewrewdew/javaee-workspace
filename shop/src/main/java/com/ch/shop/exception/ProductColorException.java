package com.ch.shop.exception;

/*아래의 클래스는 자바의 RuntimeException을 상속받아, 개발자만의 예외 객체로 커스텀하기 위함*/
public class ProductColorException extends RuntimeException{
	
	//자바에서 부모의 생성자는 물려받지 못한다..이유? 생생자는 해당 객체만의 초기화 작업에 사용되므로,
	//만일 부모의 생성자 마저도 물려받게 되면, 내가 부모가 되어버리는 개념..
	public ProductColorException(String msg) {
		super(msg); // 에러 메시지를 담을 수 있는 부모의 생성자 호출 
	}
	
	//Throwable은 예외 객체의 최상위 인터페이스 이므로, 어떤 종류의 에러가 나도라도, 이 객체로 받을 수 있기 때문..
	public ProductColorException(String msg, Throwable e) {
		super(msg, e); // 에러 메시지와 원인을 담을 수 있는 부모의 생성자 호출 
	}
	
	public ProductColorException(Throwable e) {
		super(e); // 에러 원인을  담을 수 있는 부모의 생성자 호출 
	}
}
