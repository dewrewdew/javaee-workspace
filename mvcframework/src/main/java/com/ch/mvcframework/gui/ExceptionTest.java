package com.ch.mvcframework.gui;

public class ExceptionTest {
	public static void main(String[] args) {
		
		/*프로그램에서 비정사 수행을 방해하는 요인을 오류라 한다.
		 * 1) 에러 : 네트워크가 끊기거나 램이 타버렸을 떄 등 프로그램의 외적인 상황에 의한 오류
		 * 2) 예외 : 프로그래밍적으로 해결할 수 있는 오류
		 * 자바에서 예외란? 
		 * 강요된 예외 => 개발자가 예외처리를 하지 않으면 컴파일조차 허용되지 않는 예외(한마디로 예외처리를 강제함)
		 * 아주 중요하다고 판단되는 상황에 대해 이미 sun에서 정해놓은 예외
		 * 런타임 예외 => 개발자가 예외처리를 하지 않아도 강요하지 않는 예외(컴파일 가능함)
		 * 	단, 방치해놓으면 실행 시 비정상 종료가 되므로 프로그램의 정상 수행에 방해가 됨.*/
		
		int[] arr = new int[3];
		arr[0]=1;
		arr[1]=2;
		arr[2]=3;
		try {
		arr[3]=4;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("프로그램 이용에 불편을 드려 죄송합니다.");
		}
		
		System.out.println("여기");
	}
}
