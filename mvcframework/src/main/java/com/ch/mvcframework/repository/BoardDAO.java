package com.ch.mvcframework.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.dto.Board;
import com.ch.mvcframework.mybatis.MybatisConfig;

// MVC에서 M에 해당!
public class BoardDAO {
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();
	
	// 글 한건 등록
	public int insert(Board board) {
		int result = 0;
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		result = sqlSession.insert("Board.insert",board);
		// SqlSession은 디폴트로 autocommit 속성이 false로 되어있음
		// 즉 commit하지 않으면 insert가 db에 확정되지 않음
		// commit은 DML에서만 사용!!
		sqlSession.commit();
		mybatisConfig.release(sqlSession);
		return result;
	}
	
	// 모든 글 가져오기
	public List selectAll() {
		List list = null;
		SqlSession sqlSession=mybatisConfig.getSqlSession();
		list = sqlSession.selectList("Board.selectAll");
		mybatisConfig.release(sqlSession);
		return list;
	}
	
	// 게시물 1건 가져오기
	public Board select(int board_id) {
		Board board = null;
		SqlSession sqlSession=mybatisConfig.getSqlSession();
		board = sqlSession.selectOne("Board.select", board_id);
		mybatisConfig.release(sqlSession);
		return board;
	}
	
	// 게시물 1건 삭제
	public int delete(int board_id) {
		int result = 0;
		//SqlSession = Connection + PreparedStatement
		SqlSession sqlSession=mybatisConfig.getSqlSession();
		sqlSession.delete("Board.delete", board_id); // 앞에는 네임스페이스(BoardMapper id) 뒤에는 매개변수
		sqlSession.commit(); // 트랜잭션 확정
		mybatisConfig.release(sqlSession);
		return result;
	}
	
	// 게시물 1건 수정
	public int update(Board board) {
		int result = 0;
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		sqlSession.update("Board.update", board);
		// DML은 commit 대상!
		sqlSession.commit(); // 메모리가 아닌 디스크에 확정짓는 작업
		mybatisConfig.release(sqlSession);
		return result;
	}
	
	
}
