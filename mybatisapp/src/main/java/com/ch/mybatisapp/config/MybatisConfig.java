package com.ch.mybatisapp.config;
import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


/*
 * Mybatis 설정 파일은 프로그래밍 언어가 아닌, 단순한 설정 정보를 가진 리소스이므로, 이 리소스를 읽어들여 해석해줄 객체가 필요함
 * */
public class MybatisConfig {
	// Mybatis가 올라오면 sqlsessionfactory도 같이 올라오게 세팅하기!
	private static MybatisConfig instance;
	private SqlSessionFactory sqlSessionFactory;
	
	private MybatisConfig() {
		
			try {
				String resource = "com/ch/mybatisapp/config/config.xml"; // 패키지 밑에 클래스만 올 수 있는게 아님!!! 자바가 아닌게 들어올 때 /를 넣어 디렉토리취급을 한다!!!
				InputStream inputStream = Resources.getResourceAsStream(resource);
				/*
				 * Mybatis를 이용하면 개발자는 더이상 JDBC를 직접 사용하여 데이터베이스 연동 코드를 작성할 필요가 없다.
				 * 이 때 개발자가 쿼리문을 수행하기 위해서는 Mybatis가 제공해주는 sqlsession 객체를 이용해야 하는데
				 * 이 SqlSession 객체는 SqlSessionFactory로부터 얻고 사용이 끝난 후엔 그냥 닫으면 된다.(close)
				 * 
				 * */
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public static MybatisConfig getInstance() {
		if(instance==null) {
			instance=new MybatisConfig();
		}
		return instance;	
	}
	
	// 팩토리로부터 쿼리문 실행에 필요한 SqlSession 객체를 가져갈 수 있도록 메서드를 정의
	// 외부의 객체는 이 메서드 호출만으로 팩토리로부터 SqlSession을 얻어갈 수 있다.
	// 참고로 SqlSession은 이미 접속 정보를 가지고 있으며 쿼리문도 실행할 수 있는 객체이므로
	// 자바 개발자는 기존의 JDBC코드에서 Connection, PreparedStatement를 직접 다루었던 비효율적 코드에서 벗어날 수 있다.
	// 이젠 JDBC의 상투적 코드와 안녕~~~~~
	public SqlSession getSqlSession() {
		return sqlSessionFactory.openSession();
	}
	
	// sqlSession은 쿼리문 수행 후 닫아야 하므로, 아래의 메서드에서 대신 닫아주는 기능을 구현해주자
	public void release(SqlSession sqlSession) {
		if(sqlSession !=null) {
			sqlSession.close();
		}
	}
	
	
	
	
	
	

}
