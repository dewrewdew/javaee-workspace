package com.ch.shop.exception;

/*try-catch문으로 에러를 작성하면 그부분에서만 확인되고 끝남 -> 사용자에게도 알려주거나 하려면 오류를 동네방네 알려줘야함. 그래서 클래스 별도 생성!*/
/*아래의 클래스 자바의 RuntimeException을 상속받아 개발자만의 예외 객체로 커스텀하기 위함*/
public class BoardException extends RuntimeException{
	
	// 자바에서 부모의 생성자는 물려받지 못한다. 왜? 생성자는 해당 객체만의 초기화 작업에 사용되므로
	// 만일 부모의 생성자마저도 물려받게 되면 내가 부모가 되어버리는 꼴이 됨.
	public BoardException(String msg) {
		super(msg); // 에러메시지를 담을 수 있는 부모의 생성자 호출
	}
	
	// Throwable은 예외 객체의 최상위 인터페이스이므로 어떤 종류의 에러가 나더라도 이 객체로 받을 수 있기 때문
	public BoardException(String msg, Throwable e) {
		super(msg, e); // 에러 메시지를 담을 수 있는 부모의 생성자 호출
	}
	
	public BoardException(Throwable e) {
		super(e); // 에러 원인을 담을 수 있는 부모의 생성자 호출
	}
}
