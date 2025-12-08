package com.ch.mybatisapp.repository;

import org.apache.ibatis.session.SqlSession;

import com.ch.mybatisapp.config.MybatisConfig;
import com.ch.mybatisapp.dto.Board;

public class BoardDAO {
	MybatisConfig mybatisConfig = MybatisConfig.getInstance(); // SqlSessionFactory가 들어있는 싱글턴 객체 생성

	// 글쓰기
	public int insert(Board board) {
		int result = 0;
		// 상투적 JDBC code를 사용하지 말자!!!
		// Mybatis에게 맡기자!!!
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		result = sqlSession.insert("com.ch.mybatisapp.dto.Board.insert", board);
		// DML은 트랜잭션 확정지어야 한다.
		sqlSession.commit();
		mybatisConfig.release(sqlSession);
		
		return result;
	}
}
