package com.ch.shop.test.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ch.shop.config.spring.AppConfig;
import com.ch.shop.test.food.Cook;
import com.ch.shop.test.school.Student;

public class AppMain {
	
	
	public static void main(String[] args) {
		//스프링의 ApplicationContext  는 개발방법에 따라 여러가지 하위 자료형을 지원해준다..
		//예) 빈 설정파일이 xml 일 경우엔 ClasspathXMLApplicaltionContext  
		//     빈 설정파일이 자바의 클래스인 경우엔 AnnotationConfigApplicationContext 
		
		//AnnotationConfigApplicationContext 는 개발자가 설정해놓은 클래스를 읽어들어야 하므로, 생성자의 매개변수로 전달
		//아래의  생성자가 호출되는 순간, 개발자가 설정파일에 @Bean 으로 명시해놓은 객체들의 인스턴스를 생성하여 보관하게 된다..
		//따라서 빈 컨테이너라 부른다..
		AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext(AppConfig.class);
		 
		Cook cook =(Cook)applicationContext.getBean("cook");		
		cook.makeFood();
		
		//학생 객체의 원하는 동작을 시켜 벨리 울리는지 테스트 
		Student student =(Student)applicationContext.getBean("student");
		student.gotoSchool();
		student.study();
		student.rest();
		student.haveLunch();
		student.goHome();
		
	}
}










