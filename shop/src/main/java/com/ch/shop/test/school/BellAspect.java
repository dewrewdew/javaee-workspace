package com.ch.shop.test.school;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/*
 * 아래의 클래스는, 우리 애플리케이션에서 공통적이고도 전반적으로 사용되는 로직을 
 * 특정 객체 안에 DI 처리하는 것이 아니라, 아예 독립적으로 하나의 관점으로 만들어,
 * 이 관점이 관여될 시점에 공통로직을 자동으로 호출할 수 있는 기술인 AOP를 구현하기 위함이다..
 * */
@Aspect
public class BellAspect {
	
	private Bell bell;
	
	public BellAspect(Bell bell) {
		this.bell=bell;
	}	
	//이 관점 객체가, 공통로직인 Bell의 ding 을 어느 위치에서, 어느 시점에 적용할지를 설명하는 메서드를 작성 
	//AOP는 스프링 자체의 기술이 아니라, 예전부터 자바기반의 기술중 AspectJ라는 기술이 있었고, 스프링은 단지 이 AspectJ를 
	//사용하는 것 뿐임..따라서 별도의 의존성이므로, 의존성 라이브러리에 추가해야 한다
	//아래의 @Before 어노테이션 내부에 작성하는 표현식 패턴은 스프링 자체의 문법이 아닌 AspectJ의 문법이므로, 형식을 따라주자 
	@Before("execution(* com.ch.shop.test.school.Student.*(..))")
	public void ringBefore() {
		bell.ding();
	}
	
	@After("execution(* com.ch.shop.test.school.Student.*(..))")
	public void ringAfter() {
		bell.ding();
	}
	
}









