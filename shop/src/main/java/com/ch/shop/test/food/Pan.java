package com.ch.shop.test.food;

/*
 * DI에 의하면, 객체간에 서로 너무 정확하고 구체적인 자료형을 보유할수록 오히려 유지보수성은 떨어진다..
 * 왜?? 의존성이 강해지므로...
 * 
 * 해결책? 특정 객체가 보유할 객체의 자료형은 그 보다 높은 상위자료형을 정의하여 보유하는 것이 해결책임
 *  		 즉, 의존성(Dependency)을 낮추기 위해..
 * */
public interface Pan {
	public void boil();
}







