package com.ch.mvcframework.repository;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.dto.Dept;
import com.ch.mvcframework.exception.DeptException;
import com.ch.mvcframework.mybatis.MybatisConfig;

public class DeptDAO{
	MybatisConfig mybatisConfig=MybatisConfig.getInstance();
	
	
	//1건 등록
	public void insert(SqlSession sqlSession, Dept dept) throws DeptException{ // 어차피 예외처리할거라 return값을 int로 줘도 의미가 없어서 void로 하는 것!
		try {
			sqlSession.insert("Dept.insert",dept);
		} catch (Exception e) {
			throw new DeptException("부서 등록 실패", e);
		}
	}

}
