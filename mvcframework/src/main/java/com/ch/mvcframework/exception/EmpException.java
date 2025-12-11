package com.ch.mvcframework.exception;


/*사원과 관련된 데이터베이스 처리 시 예외를 표현한 객체
 * javaEE에서는 개발자가 자신만의 예외를 커스텀하여 활용하는 프로그램 작성법을 알아야 함
 * 예) DuplicatedMemberException, MemberRegistException*/
public class EmpException extends RuntimeException{
	// 자바에서 부모의 "생성자"는 상속받지 못하므로 RuntimeException의 생성자 중 우리가 필요할만한 생성자를 호출하여 사용하자
	// 자식이 부모 생성자를 호출하게되면 자식이 부모랑 똑같이 생겼음 그래서 생성자는 상속받지 못함!
	
	// 에러메시지를 심을 수 있는 생성자
	public EmpException(String msg) {
		super(msg);
	}
	
	// 에러메세지 + 에러 원인을 심을 수 있는 생성자
	public EmpException(String msg, Throwable e) {
		super(msg, e);
	}
	
	// 에러 원인만 넣을 수 있는 생성자
	public EmpException(Throwable e) {
		super(e);
	}
}
