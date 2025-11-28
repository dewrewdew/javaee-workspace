package com.ch.notice.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ch.notice.domain.Notice;

/*
 * 이 클래스의 목적은?
 * javaEE기반의 애플리케이션이던 javaSE기반의 애플리케이션 데이터베이스를 연동하는 비즈니스 로직은 동일하다.
 * 따라서 유지보수성을 고려하여 여러 플랫폼에서 재사용할 수 있는 객체를 정의
 * 특히 로직 객체중 오직 데이터베이스 연동을 전담하는 역할을 하는 객체를 가리켜 애플리케이션 설계 분야에서는
 * DAO(Data Access Object) - DB에 테이블이 만약 5개라면 DAO로 1:1 대응하여 총 5개를 만들어야 한다.
 * 특히 데이터베이스의 테이블에 데이터를 처리하는 업무를 가리켜 CRUD라고 한다. Create, Read, Update, Delete
 * 
 * 아래와 같은 메서드에서 매개변수의 수가 많아질 경우, 코드가 복잡해진다.
 * 따라서 매개변수를 각각 낱개로 전달하는 것이 아니라, 객체안에 모두 넣어서 객체 자체를 전달
 * DTO(Data Transfer Object) - 오직 데이터만을 보유한 전달 객체를 의미함. 따라서 로직은 없다.(Dummy Object)
 * */

public class NoticeDAO {
	// 게시물 등록 메서드
	public int regist(Notice notice) {
		int result=0; // insert 후 성공/실패 여부를 판단할 수 있는 반환값
		Connection con=null; // 지역변수는 컴파일러가 자동으로 초기화하지 않기 때문에 반드시 초기화해야 사용할 수 있다.
		PreparedStatement pstmt=null;
		
		try {
			// 드라이버 로드
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println("드라이버 로드 성공"); // 지금은 web이 아니니까 이렇게 출력!
			// 접속
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/java","servlet", "1234");
				System.out.println(con);
			
			// 쿼리 실행
			String sql = "insert into notice(title, writer, content) values(?, ?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, notice.getTitle()); // 제목
			pstmt.setString(2, notice.getWriter()); // 작성자
			pstmt.setString(3, notice.getContent()); // 내용
			
			result = pstmt.executeUpdate();
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
