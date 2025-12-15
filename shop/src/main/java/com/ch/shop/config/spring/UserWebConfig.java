package com.ch.shop.config.spring;



import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.ch.shop.controller.shop.BoardController;

/*이 클래스는 로직을 작성하기 위함이 아니라, 애플리케이션에서 사용할 빈(객체)들 및 그들간의 관계(weaving)을 명시하기 위한 설정 목적의 클래스이며
 * 쇼핑몰의 일반 유저들이 보게되는 애플리케이션쪽 빈들을 관리한다.*/

@Configuration // 단지 xml을 대신할 설정용 클래스에 불과
@EnableWebMvc // 필수!!!!!!!!!(스프링이 지원하는 MVC 프레임워크를 사용하기 위한 어노테이션)
// 일일이 빈으로 등록할 필요가 없는, 많이 알려진 빈들을 가리켜 스프링에서는 컴포넌트라 부른다.
// 또한, 이 컴포넌트들은 패키지 위치만 설정해놓으면 스프링이 알아서 찾아내서(검색) 인스턴스를 자동으로 만들어준다.
// 그렇다면 어떤게 유명한 컴포넌트들일까?
// MVC에서의 Controller는 @Controller를 붙임
// MVC에서의 DAO는 @Repository를 붙임
// MVC에서의 DAO는 @Service를 붙임
// MVC에서의 특정 분류가 딱히 없음에도 자동으로 올리고 싶다면 @Component
@ComponentScan(basePackages="com.ch.shop.controller")
public class UserWebConfig {
	
	// DispatcherServlet이 하위 컨트롤러로부터 반환받은 결과 페이지에 대한 정보는 사실 완전한 JSP경로가 아니므로
	// 이를 해석할 수 있는 자인 ViewResolver에게 맡겨야 하는데,
	// 이 ViewResolver중 유달리 접두어와 접미어 방식을 이해하는 뷰 리절버를 InternalResourceViewResolver라고 한다.
	// 개발자는 이 객체에게 접두어와 접미어를 사전에 등록해놓아야 한다.
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver rv = new InternalResourceViewResolver();
		// 접두어 등록 /WEB-INF-views/  board/list   .jsp
		
		rv.setPrefix("/WEB-INF/views/"); // 접두어 등록
		
		rv.setSuffix(".jsp"); // 접미어 등록
 		return rv;
	}
	
	/*
	 * 스프링이 MVC 프레임워크중 컨트롤러 영역만을 지원하는 것이 아니라, 데이터 베이스 관련 제어도 지원하므로,
	 * 지금까지 순수하게 사용해왔던 mybatis를 스프링이 지원하는 mybatis로 전환해본다.
	 * 스프링이 지원하는 데이터 연동 기술을 사용하려면, spring jdbc 라이브러리를 추가해야 한다.
	 * spring jdbc 4330
	 * */
	/*--------------------------------------------------------------
	 * 1) 개발자가 사용하고 싶은 데이터 소스를 결정 - 톰캣이 지원하는 JNDI를 사용할 예정
	 *------------------------------------------------------------- */
	public DataSource dataSource() throws NamingException{
		JndiTemplate jndi = new JndiTemplate();
		return jndi.lookup("java:comp/env/jndi/mysql", DataSource.class);
	}
	/*--------------------------------------------------------------
	 * 2) 트랜잭션 매니저 등록
	 * - 스프링은 개발자가 사용하는 기술이 JDBC, Mybatis, Hibernate, JPA 이건 상관없이 일관된 방법으로 트랜잭션을 처리할 수 있는 방법을 제공해주는데
	 * 개발자는 자신이 사용하는 기술에 따라 적절한 트랜잭션 매니저를 등록해야 한다.
	 * 예) JDBC 사용 시 - DataSourceTransactionManager를 빈으로 등록해야 함.
	 * 예) Hivernate 사용 시 - HibernateTransactionManager를 빈으로 등록해야 함.
	 * 예) Mybatis 사용 시 - DataSourceTransactionManager를 빈으로 등록해야 함.
	 * 		특히 mybatis의 경우 jdbc와 동일한 datasourceTransactionManager를 사용하는 이유는?
	 * 사실 mybatis는 내부적으로 jdbc를 사용하기 때문
	 * 그리고 이 모든 트랜잭션 매니저의 최상단 객체가 바로 PlatformTransactionManager다.
	 *------------------------------------------------------------- */
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager();
	}
	/*-----------------------------------------------------------------
	 * 3) SqlSession을 관리하는 mybatis의 SqlSessionFactory를 빈으로 등록
	 *-------------------------------------------------------------- */
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		// 순수 mybatis 프레임워크 자체에서 지원하는 객체가 아니라, mybatis-spring에서 지원하는 객체인
		// SqlSessionFactoryBean (끝에 Bean) 을 이용하여 설정 xml 파일을 로드한다.
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		// 패키지에 포함된 파일의 유형이 클래스가 아닌 경우 더이상 패키지로 표현하지 말고 일반 디렉토리로 취급해야 한다. 그래서 . 대신 /으로 작성!
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("com/ch/shop/config/mybatis/config.xml"));
		return sqlSessionFactoryBean.getObject();
	}

}
