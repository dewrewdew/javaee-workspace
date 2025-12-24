package com.ch.shop.config.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.ch.shop.model.board.MybatisBoardDAO;
import com.ch.shop.dto.OAuthClient;
import com.ch.shop.model.board.BoardServiceImpl;

/*
 이 클래스는 로직을 작성하기 위함이 아니라, 애플리케이션에서 사용할 빈(객체)들 및 그들간의 관계(weaving)를 명시하기 위한
 설정 목적의 클래스이며, 쇼핑몰의 일반 유저들이 보게 되는 애플리케이션쪽 빈들을 관리한다 
*/
@Configuration //단지 xml 을 대신한 설정용 클래스에 불과해!!
@EnableWebMvc //필수 설정( 스프링이 지원하는 MVC 프레임워크를 사용하기 위한 어노테이션) 
//일일이 빈으로 등록할 필요가 없는 많이 알려진 빈들을 가리켜
//스프링에서는 컴포넌트라  부른다. 또한 이 컴포넌트들은 
//패키지 위치만 설정해놓으면 스프링이 알아서 찾아내서 (검색)
//인스턴스를 자동으로 만들어준다
//MVC에서의 Controller는 @Controller 를 붙임 
//MVC에서의 DAO 는 @Repository 를 붙임 
//MVC에서의 DAO 는 @Service 를 붙임 
//MVC에서의 특정 분류가 딱히 없음에도 자동으로 올리고 싶다면 @Component
@ComponentScan(basePackages = {"com.ch.shop.controller.shop"})
public class ShopWebConfig extends WebMvcConfigurerAdapter{
	
	 /*context.xml등에 명시된 외부 자원을 JNDI방식으로 읽어들일 수 있는 스프링의 객체*/
	@Bean
	public JndiTemplate jndiTemplate() {
		return new JndiTemplate();
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	
	/*Google*/
	@Bean
	public String googleClientId(JndiTemplate jndiTemplate) throws Exception {
		return (String)jndiTemplate.lookup("java:comp/env/google/client/id");
	}
	
	@Bean
	public String googleClientSecret(JndiTemplate jndiTemplate) throws Exception {
		return (String)jndiTemplate.lookup("java:comp/env/google/client/secret");
	}
	
	/*Oauth 로그인 시 사용되는 환경 변수(요청주소, 콜백주소 등등 변수는 객체로 담아서 관리하면 유지보수하기 좋다
	 * 우리의 경우 여러 프로바이더를 연동할 것이므로, OAuthClient 객체를 여러개 메모리에 보관해놓자
	 * */
	
	@Bean
	public Map<String, OAuthClient> oauthClients(
			@Qualifier("googleClientId") String googleClientId,
			@Qualifier("googleClientSecret") String googleClientSecret
			){
		
		// 구글, 네이버, 카카오를 각각 OAuthClient 인스턴스에 담은 후 다시 Map에 모아두자
		Map<String, OAuthClient> map = new HashMap<>();
		
		
		// 구글 등록
		OAuthClient google = new OAuthClient();
		google.setProvider("google");
		google.setClientId(googleClientId);
		google.setClientSecret(googleClientSecret);
		google.setAuthorizeUrl("https://accounts.google.com/o/oauth2/v2/auth"); // google api문서에 나와있음
		google.setTokenUrl("https://oauth2.googleapis.com/token"); // 토큰을 요청할 주소
		google.setUserInfoUrl("https://openidconnect.googleapis.com/v1/userinfo");
		google.setScope("openid email profile"); // 사용자의 정보에 대한 허용 범위
		google.setRedirectUri("http://localhost:8888/login/callback/google");
		
		map.put("google", google);
		
		// 네이버 등록
		
		// 카카오 등록
		
		return map;
	}
	
}



















