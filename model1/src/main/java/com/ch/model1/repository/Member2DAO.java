package com.ch.model1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ch.model1.dto.Member2;
import com.ch.model1.util.PoolManager;

/*이 클래스는 오직 데이터베이스 관련된 로직만 담당하는 DAO 클래스임*/
public class Member2DAO {
	PoolManager pool = PoolManager.getInstance();
	// 각각의 DAO마다 new로 선언할 필요가x 수영장 역할인데 각각 수영장을 굳이 갖고있을 필요가 없다.
	// 굳이 여러개 만들 필요가 있나? 의문이 생길 때 적용할 것 => 디자인 패턴!! (여기서의 디자인은 설계 디자인을 의미)
	// spring을 함에 있어 이정돈 알아야된다. 싱글톤 패턴
	
	
	// insert 레코드 1건 => insert는 무조건 1건씩만 일어남!!
	public int insert(Member2 member2) { //자바에서는 파라미터는 DTO로 한번에 모아서 처리한다. 
		Connection con = pool.getConnection(); // connection 풀로부터 하나 대여
		PreparedStatement pstmt =null;
		int result = 0; // DML수행 후 반환 값 담을 변수
		String sql ="insert into member2(id, name, email) values(?, ?, ?)";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, member2.getId());
			pstmt.setString(2, member2.getName());
			pstmt.setString(3, member2.getEmail());
			result = pstmt.executeUpdate(); // 쿼리 실행
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return result;
	}
	// 모든 레코드 가져오기
	public List selectAll() {
		Connection con = pool.getConnection(); // 커넥션 풀로부터 대여	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member2> list = new ArrayList(); // 생성 당시에는 size가 0
		
		String sql = "select * from member2 order by member2_id asc";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery(); // select문 수행 후 결과 받기
			// rs에 들어있는 레코드 수만큼, DTO를 생성해서 레코드 안에 넣자
			// 그리고 채워진 DTO를 다시 java.util.List에 밀어넣자
			
			while(rs.next()) { // 한 칸씩 전진
			Member2 member2 = new Member2(); // DTO 생성 => 텅 빈 상태
			member2.setId(rs.getString("id"));
			member2.setName(rs.getString("name"));
			member2.setEmail(rs.getString("email"));
			list.add(member2); // 리스트에 추가
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return list;
		
	}
}
