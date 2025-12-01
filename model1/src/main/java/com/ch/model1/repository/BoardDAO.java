package com.ch.model1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ch.model1.dto.Board;
import com.ch.model1.util.PoolManager;

// 데이터베이스의 Board table에 대한 CRUD를 수행하는 객체
public class BoardDAO {
	
	PoolManager pool = new PoolManager();
	
	// Create(=insert)
	// 글 1건을 등록하는 메서드
	public int insert(Board board) { // 개발 시 파라미터의 수가 많을때는 낱개로 처리하지 않음.
												// 특히 데이터베이스 연동 로직에서는 DTO를 이용한다.
		// 이 메서드 호출 시 마다 접속을 일으키는 것이 아니라, Tomcat이 접속자가 없더라도
		// 미리 Connection들을 확보해 놓은 커넥션풀(Connection Pool)로부터 대여해보자
		// 또한 쿼리문 수행이 완료되더라도 얻어온 Connection은 절대로 닫지 말아야 한다.
		Connection con = null;
		PreparedStatement pstmt = null;
		int result=0; // return할 예정이므로 try문 밖에 선언! 
		
		try {
			InitialContext context = new InitialContext();
			DataSource pool = (DataSource)context.lookup("java:comp/env/jndi/mysql");
			con=pool.getConnection();
			
			// 쿼리 수행
			//String sql = "insert into board(title, writer, content) values(?, ?, ?)";
			String sql = "insert into board(title, writer, content) values(?,?,?)";
			pstmt=con.prepareStatement(sql);
			
			// System.out.println(sql); => 안될때 sql 찍어서 확인해보기!!!
		
			// DB의 레코드 한 건이 자바로 보면 new로 생성한 인스턴스와 같음!
			// DB의 테이블 = java의 class
			// 따라서 DB에서 테이블을 만들었다? java 개발자라면 바로 Class 만들 준비 하고있어야 함!! 무조건!!!
			
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContent());
			
			result = pstmt.executeUpdate(); // 쿼리 수행
			
			/*아래의 코드를 작성하면 안되는 이유? out을 쓰려고 하는 순간부터 DAO의 중립성이 사라져버림!!!!
			 * 해결책 : DAO는 디자인 영역과는 분리된 오직 DB만을 전담하므로 절대로 디자인 코드를 넣어선 안됨
			 * 따라서 디자인 처리는 이 메서드를 호출한자가 처리하도록 여기서는 결과만 반환하고 끝내자.
			 * if(result <1) {
			 * 		out.print("접속 실패")
			 * } else {
			 * 		out.print("접속 성공")
			 * }
			 */
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt !=null) {
				try {
					pstmt.close(); // 중요한건 con! pstmt는 닫아버리면 됨!
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// 주의!! 기존 JDBC코드는 다 사용한 커넥션을 닫았지만, 풀로부터 얻어온 커넥션을 닫으면 안됨!!!
			if(con != null) {
				try {
					con.close();
					// 애초에 lookup으로 가져왔기 때문에 동일한 close()메서드지만 이때는 pool로 돌려주는 역할을 함!
					// 이 객체는 DataSource 구현체로부터 얻어온 Connection이기 때문에 일반적 JDBC의 닫는 close()가 아님(반납!!)
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			} 
			
		}
		return result;
	}
	
	// Read(=select) : 모든 데이터 가져오기
	public List selectAll() {
		
		//Class.forName("com.mysql.cj.jdbc.Driver"); // 드라이버 로드
		//Connection con=DriverManager.getConnection("jdbc:mysql://localhost", "servlet", "1234");
		// con을 미리 잔뜩 확보해놓고 (이런걸 pool이라 함) 필요할때마다 사용 => connection pooling 기법 (필수!!!)
		// 다 쓴 con을 끊어버리지 않고 다시 pool에 넣음!
		// 커넥션 얻는 코드를 이 메서드에서 손수 하지 말자!! PoolManager가 대신 해주므로!
		// con을 PoolManager한테 달라하자!!!
		Connection con=pool.getConnection(); 
		// 풀매니저로부터 커넥션 객체를 얻어옴!!
		// 여기서 직접 검색하면 jndi 검색코드가 중복되므로
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		List<Board> list=new ArrayList(); // 모든 게시물을 모아놓을 리스트! 여기에 들어가는 객체는 바로 DTO 인스턴스들이다.
		// Board 객체로 구성된 List형으로 보면 됨! => 현재까지는 아무것도 채워넣지 않았기 때문에 size가 0이다.("길이"라는 단어 쓰지 x)
		
		String sql = "select board_id, title, writer, content, regdate, hit from board";
		try {
			pstmt=con.prepareStatement(sql);
			rs = pstmt.executeQuery(); // select문의 반환값은 ResultSet!
			
			// rs는 무조건 이 메서드에서 닫아야 하므로(외부의 jsp는 디자인을 담당하는 코드이지, ResultSet의 존재를 알 필요도 없고,
			// 또한 ResultSet은 db연동 기술이므로, 오직 DAO에서만 제어해야 한다.
			// 따라서 finally에서 rs를 닫는것은 DAO의 의무다!!!!
			// 근데 rs를 반환해야되는데 죽여버리면서 죽은 rs를 넘겨주는게 됨(rs를 닫아버린 상태에서 외부 객체에게 전달해주면 외부객체는 이 rs를 사용할 수 없다 closed되어있으므로)
			// 해결 방법 => DB입장 table = java입장 class, DB입장 record = java instance 즉, rs는 어따 넘겨주고 죽이면됨!
			// 이 문제를 해결하기 위해 필요한 객체들의 조건
			// 1. 현실에 존재하는 사물을 표현할 수 있는 객체가 필요(ex) 게시물 1건을 담을 수 있는 존재 : Board DTO)
			// 2. Board DTO로부터 생성된 게시물을 표현한 인스턴스들을 모아놓을 객체가 필요하다(순서 O, 객체를 담을 수 있어야 함)
			// => 이 조건을 만족하는 객체는? 자바의 컬렉션 프레임워크중 List이다.
			// collection framework란? java.util에서 지원하는 라이브러리로 오직 "객체"만을 모아서 처리할 때 유용한 api(List, Set, Map)
			
			
//			while(rs.next()) { 레코드 있는만큼 반복문??? 절대안됨!!! 디자인은 다른게 담당해야함 얘는 중립적 존재
//				Out.print("<table>");
//				Out.print("</table>");
			while(rs.next()) {// rs.next()가 true인 동안, 즉, 모든 레코드만큼 반복!
			
			Board board = new Board(); // 게시물 한 건을 담을 수 있는  Board DTO 클래스의 인스턴스 1개 준비
			board.setBoard_id(rs.getInt("board_id")); // pk담기
			board.setTitle(rs.getString("title")); // title담기
			board.setWriter(rs.getString("writer")); // writer담기
			// board.setContent(rs.getString("content")); // 이건 게시판 상세보기에서 필요하지 목록에서는 필요 x
			board.setRegdate(rs.getString("regdate"));
			board.setHit(rs.getInt("hit"));
			
			list.add(board); // List에 인스턴스 1개 추가됨
			}
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) {
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
		return list;
		
	}
	
}

// U(=update)
// D(=delete)