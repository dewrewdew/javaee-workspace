package com.ch.mvcframework.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropTest {

	public static void main(String[] args) {
		// Map을 상속받은 객체
		// 이 객체는 단독적으로 파일을 접근할 능력은 없다. 따라서 java.io의 스트림 객체들을 이용해야 함.
		/*자바의 스트림
		 * 1) 방향에 따른 기준 : 
		 * 		입력 : 실행중인 프로그램으로 데이터가 들어오는 모습 입력
		 * 		출력 : 실행중인 프로그램에서 데이터가 흘러나가는 모습 출력
		 * 
		 * 2) 데이터 처리 방법
		 * 		바이트 스트림
		 * 		문자 스트림
		 *		버퍼 스트림 
		 *
		 * */
		Properties props = new Properties(); // 난생 처음보는 클래스를 보면 바로 일반, 추상, 인터페이스 => shift+f2로 확인!!!
		try {
			FileInputStream fis = new FileInputStream("C:\\javaee_workspace\\mvcframework\\src\\main\\webapp\\WEB-INF\\servlet-mapping.txt");
			props.load(fis); // 이 시점부터 프로퍼티스 객체는 파일의 내용을 로드한 상태
			String value=props.getProperty("/food.do");
			System.out.println(value);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
