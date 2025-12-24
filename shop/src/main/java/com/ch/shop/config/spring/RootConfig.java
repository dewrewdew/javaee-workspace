package com.ch.shop.config.spring;

import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//이 클래스는 로직 작성용이 아니라, 전통적으로 사용해왔던 스프링의 빈을 등록하는 용도의 xml을 대신하기 위한 자바 클래스이다
//특히, 이 클래스에 등록될 빈들은 비즈니스 로직을 처리하는 모델영역의 빈들이므로, 서블릿 수준의 스프링컨테이너가 사용해서는 안되며
//모든 서블릿이 접근할 수 있는 객체인 ServletContext 수준에서의 스프링컨테이너가 이 클래스를  읽어들여 빈들의 인스턴스를 관리해야 한다..
@Configuration //xml을 대신할꺼야!!
@ComponentScan(basePackages = {"com.ch.shop.model", "com.ch.shop.util"})
@EnableTransactionManagement
public class RootConfig  extends WebMvcConfigurerAdapter{

	//DispatcherServlet이 하위 컨트롤러로 부터 반환받은 결과 페이지에 대한 정보는 사실 완전한 JSP경로가 아니므로, 
	//이를 해석할 수 있는 자인 ViewResolver에게 맡겨야 하는데, 이 ViewResolver 중  유달리 접두어와 접미어 방식을 이해하는 
	//뷰리절버를 InternalResourceViewResolver라고 한다..개발자는 이 객체에게 접두어와 접미어를 사전에 등록해 놓아야 한다 
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver rv = new InternalResourceViewResolver();
		// /WEB-INF/views/    board/list     .jsp
		rv.setPrefix("/WEB-INF/views/");//접두어 등록   
		 
		rv.setSuffix(".jsp");//접미어 등록
		return rv;
	}	
	
	//DispatcherServlet은 컨트롤러에 대한 매핑만 수행하면 되며, 정적자원(css, js, html, imgage 등)에 대해서는 직접 처리하지 
	//않게 하기 
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		//registry.addResourceHandler("브라우저로 접근할 주소").addResourceLocations("웹애플리케이션을 기준으로 실제 정적자원이 잇는 우치")
		registry.addResourceHandler("/static/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/photo/**").addResourceLocations("file:/C:/shopdata/product/");
	}
	
	//Jackson 라이브러리 사용을 설정 
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new MappingJackson2HttpMessageConverter());//Jackson 객체를 넣기 }
	}
	
	/*
	  스프링이 MVC 프레임워크 중 컨트롤러 영역만을 지원하는 것이 아니라, 데이터베이스 관련 제어도 지원하므로, 
	  지금까지 순수하게 사용해왔던 mybatis를 스프링이 지원하는 mybatis로 전환해본다
	  스프링이 지원하는 데이터 연동 기술을 사용하려면, spring jdbc 라이브러리를 추가해야 한다..
	  spring jdbc 검색  
	 */
	
	/*----------------------------------------------------
	 1) 개발자가 사용하고 싶은데 데이터소스를 결정 
	 	- 톰켓이 지원하는 JNDI를 사용할 예정 
	----------------------------------------------------*/
	@Bean
	public DataSource dataSource() throws NamingException{
		JndiTemplate jndi = new JndiTemplate();
		return jndi.lookup("java:comp/env/jndi/mysql", DataSource.class);
	}

	/*----------------------------------------------------
	 2) 트랜잭션 매니저 등록 
	 	- 	스프링은 개발자가 사용하는 기술이 JDBC, Mybatis, Hibernate, JPA 이건 상관없이 
	 		일관된 방법으로 트랜잭션을 처리할 수있는 방법을 제공해주는데, 개발자는 자신이 사용하는 기술에 따라 
	 		적절할 트랜잭션 매니저를 등록해야 한다 
	 		예) JDBC 사용 시 - DataSourceTransactionManager를 빈으로 등록해야 함 
	 		예) Hibernate 사용 시 - HibernateTransactionManager 를 빈으로 등록해야 함 
	 		예) Mybatis 사용 시 - DataSourceTransactionManager를 빈으로 등록해야 함 
	 									특히 Mybatis 의 경우 JDBC와 동일한 DataSourceTransactionManager를 사용하는 이유는?
	 									사실 Mybatis는 내부적으로, JDBC를 사용하기 때문임..	 		  
	 		그리고 이 모든 트랜잭션 메니저의 최상단 객체가 바로 PlatformTransactionManager 이다
	----------------------------------------------------*/
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {		
		return new DataSourceTransactionManager(dataSource);
	}
	
	/*----------------------------------------------------
	 3) SqlSession을 관리하는 mybatis의 SqlSessionFactory를 빈으로 등록  
	----------------------------------------------------*/
	@Bean
	public SqlSessionFactory sqlSessoinFactory(DataSource dataSource) throws Exception{
		
		//순수 mybatis 프레임워크 자체에서 지원하는 객체가 아니라, mybatis-spring 에서 지원하는 객체인 
		//SqlSessionFactoryBean ( 끝에 Bean) 을 이용하여 설정xml 파일을 로드한다 
		
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		
		//패키지에 포함된 파일의 유형이 클래스가 아닌 경우 더이상 패키지로 표현하지 말고, 일반 디렉토리로 취급해야 한다..
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("com/ch/shop/config/mybatis/config.xml"));
		
		sqlSessionFactoryBean.setDataSource(dataSource);
		
		return sqlSessionFactoryBean.getObject();
	}
	
	/*----------------------------------------------------
	 4) SqlSessionTemplate 빈 등록   
	 	mybatis 사용 시 쿼리문 수행을 위해서는 SqlSession 을 이용했으나, mybatis-spring 에서는 SqlSessionTemplate 객체를 사용해야 함 
	----------------------------------------------------*/
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception{
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
