package com.ch.model1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ch.model1.dto.News;
import com.ch.model1.util.PoolManager;

/*News 테이블에 대한 CRUD만을 수행하는 DAO*/
public class NewsDAO {
	PoolManager poolManager = PoolManager.getInstance();
	// 게시물 한 건 넣기
	public int insert(News news) {
		Connection con = poolManager.getConnection();
		PreparedStatement pstmt=null;
		int result = 0;
		String sql = "insert into news(title, writer, content) values(?, ?, ?)";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, news.getTitle());
			pstmt.setString(2, news.getWriter());
			pstmt.setString(3, news.getContent());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			poolManager.freeConnection(con, pstmt);
		}
		
		return result;
		
	}
	
	// 모든 레코드 가져오기
	public List selectAll(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = new ArrayList();
		
		try {
			con=poolManager.getConnection();
			String sql = "select * from news order by news_id desc";// 내림차순
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery(); // rs생성
			while(rs.next()) {
				News dto= new News();
				dto.setNews_id(rs.getInt("news_id"));
				dto.setTitle(rs.getString("title"));
				
				
				dto.setWriter(rs.getString("writer"));
				dto.setContent(rs.getString("content"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setHit(rs.getInt("hit"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			poolManager.freeConnection(con, pstmt, rs);
		}
			
			return list;
	}
	// 레코드 한 건 가져오기
	public News select(int news_id){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		News dto=null; // 한 건 이므로 뉴스 1개를 반환
		
		con=poolManager.getConnection();
		String sql = "select * from news where news_id=?";// 내림차순
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, news_id);
			rs=pstmt.executeQuery(); // rs생성
			if(rs.next()) {
				dto= new News();
				dto.setNews_id(rs.getInt("news_id"));
				dto.setTitle(rs.getString("title"));
				dto.setWriter(rs.getString("writer"));
				dto.setContent(rs.getString("content"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setHit(rs.getInt("hit"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			poolManager.freeConnection(con, pstmt, rs);
		}
			
			return dto;
	}		
}
