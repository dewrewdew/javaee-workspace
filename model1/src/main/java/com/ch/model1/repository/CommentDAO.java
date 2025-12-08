package com.ch.model1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ch.model1.dto.Comment;
import com.ch.model1.dto.News;
import com.ch.model1.util.PoolManager;

// 오직 comment 테이블에 대한 CRUD만을 수행하는 DAO
public class CommentDAO {
	PoolManager poolManager = PoolManager.getInstance();

	
	// 댓글 등록
	public int insert(Comment comment) {
		Connection con=poolManager.getConnection();
		PreparedStatement pstmt=null;
		int result = 0;
		
		
		String sql = "insert into comment(msg, reader, news_id) values(?, ?, ?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, comment.getMsg());
			pstmt.setString(2, comment.getReader());
			pstmt.setInt(3, comment.getNews().getNews_id()); // 객체지향이므로, 부모를 int형이 아닌 객체형태로 has a 로 보유하고 있으므로 이렇게 접근해야 함.
			result = pstmt.executeUpdate(); // DML 수행			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			poolManager.freeConnection(con, pstmt);
		}
		
		return result;
	}
	
	// 특정 뉴스기사에 딸려있는 댓글 모두 가져오기
	public List selectByNewsId(int news_id) {
		Connection con = poolManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = new ArrayList(); // comment DTO를 모아놓을 리스트(자바에서는 배열보다는 컬렉션을 선호함)
		
		try {
			String sql = "select * from comment where news_id =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,  news_id);
			rs=pstmt.executeQuery(); // 쿼리 실행 후 레코드 담기
			while(rs.next()) {
				// rs를 대신할 수 있는 데이터 담는 용도의 DTO 선언
				Comment comment = new Comment(); 
				comment.setComment_id(rs.getInt("comment_id"));
				comment.setMsg(rs.getString("msg"));
				comment.setReader(rs.getString("reader"));
				comment.setWritedate(rs.getString("writedate"));
				
				// 부모인 뉴스의 정보도 담기
				News news = new News();
				news.setNews_id(news_id);
				// 생성된 news instance를 comment안으로 밀어넣기(has a 관계 반영)
				comment.setNews(news); // 자식DTO가 부모 DTO를 보유하게 만듦
				
				list.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			poolManager.freeConnection(con, pstmt, rs);
		}
		
		return list;
	}
	
	
	
	
	
}
