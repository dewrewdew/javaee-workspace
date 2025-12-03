package com.ch.model1.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

// DAO의 각 메서드마다 커넥션 풀로부터 Connection을 얻어오는 코드를 중복 작성할 경우 유지보수성이 떨어짐
// ex) JNDI명칭이 바뀌거나, 연동할 db의 종류가 바뀌는 등 외부의 어떤 변화 원인에 의해 코드가 영향을 많이 받으면 안됨
// 따라서 앞으로는 커넥션 풀로부터 Connection을 얻거나 반납하는 중복된 코드는 아래의 클래스로 처리하면 유지 보수성이 올라감
// DAO가 풀매니저를 사용!
public class PoolManager {
	private static PoolManager instance; 
	// instance라는 변수명은 공식 명칭은 아니지만 개발자들 사이에서는
	// 인스턴스를 얻어갈 수 있다는 직관성때문에 많이 사용함.
	DataSource ds;
	
	// 외부에서 아무도 직접 new하지 못하게 막자.
	private PoolManager() {
		try {
			InitialContext context = new InitialContext();
			ds = (DataSource)context.lookup("java:comp/env/jndi/mysql");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	// 싱글톤 패턴에서 메서드명은 getInstance로 고정되어 있음!
	public static PoolManager getInstance() {
		// 클래스 변수인 instance 변수에 아무것도 존재하지 않을때는 아직 인스턴스가 없는것으로 간주하므로
		// => 왜냐면 instance가 생성이 돼야 생기는게 instance변수이기 때문에!		
		// 그때 단 한번만 직접 new 해주자
		// PoolManager를 싱글턴으로 선언하면, 자바 엔터프라이즈 개발에서
		// 수많은 DAO들이 PoolManager를 매번 인스턴스로 생성하는 낭비를 방지할 수 있다.
		if(instance==null) {
			instance = new PoolManager();
		}
		
		return instance;
	}
	
	
	// 외부의 DAO들이 직접 Connection을 얻는 코드를 작성하게 하지 않으려면,
	// 이 PoolManager클래스에서 DAO 대신 Connection을 얻어와서 반환해주자!
	public Connection getConnection() {
		Connection con=null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
	// 빌려간 커넥션을 반납하기
	public void freeConnection(Connection con) {
		if(con !=null) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 주의!! 기존 JDBC코드는 다 사용한 커넥션을 닫았지만, 풀로부터 얻어온 커넥션을 닫으면 안됨!!!
		// 데이터 소스에서 얻어왔기 때문에 원래 내가 알던 그 con이 아니고 connection pool에 있는 con이 됨. 그래서 여기선 close 반환의 의미!
		// 애초에 lookup으로 가져왔기 때문에 동일한 close()메서드지만 이때는 pool로 돌려주는 역할을 함!
		// 이 객체는 DataSource 구현체로부터 얻어온 Connection이기 때문에 일반적 JDBC의 닫는 close()가 아님(반납!!)

	}
	}
	// 아래의 오버로딩된 메서드는 DML 수행 후 반납할 때 사용하기
	public void freeConnection(Connection con, PreparedStatement pstmt) {
		try {
			if(pstmt != null) {
				pstmt.close();
			}
			if(con !=null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void freeConnection(Connection con, PreparedStatement pstmt, ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
			if(con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}