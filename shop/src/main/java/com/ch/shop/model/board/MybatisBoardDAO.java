package com.ch.shop.model.board;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ch.shop.dto.Board;
import com.ch.shop.exception.BoardException;

import lombok.extern.slf4j.Slf4j;

/*	board 테이블에 대한 CRUD를 수행하되, 직접 쿼리문을 작성하지 않으며
 	mybatis 를 이용하되, 순수 mybatis가 아닌 Spring 용 mybatis 를 이용하자 
 */
@Repository //@Repository를 표시해놓으면, 스프링이 자동 스캔에 의해 탐색한 후 인스턴스를 자동으로 생성해주고, 빈 컨테이너로 관리..
@Slf4j
public class MybatisBoardDAO implements BoardDAO {
	//스프링에서는 DI 를 적극 활용해야 하므로, 필요한 객체의 인스턴스를 직접 생성하면 안되고
	//스프링 컨테이너로부터 주입(Injection) 받아야 한다 
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;//우리가 재정의한 것이 아니므로, 그냥 쓰면 됨
	
	public void insert(Board board) throws BoardException{ //외부에 전가시킴,예외처리에 대한 책임을 떠넘김 
		try {
			sqlSessionTemplate.insert("Board.insert", board);
		}catch(Exception e) {
			log.error("게시물이 등록되지 않았습니다");
			//아래에서 에러를 일으킨 목적은, 예외를 처리하기 위함이 아니라, 외부에 책임을 전가시키기위함이다..
			throw new BoardException("게시물 등록 실패", e);
		}
	}

	@Override
	public List selectAll() {
		return sqlSessionTemplate.selectList("Board.selectAll");
	}

	@Override
	public Board select(int board_id) {
		return sqlSessionTemplate.selectOne("Board.select", board_id);
	}

	@Override
	public void update(Board board) throws BoardException{
		try {
			sqlSessionTemplate.update("Board.update", board);	
		} catch (Exception e) {
			e.printStackTrace();
			throw new BoardException("수정 실패", e);//일부러 에러 발생시킴 
		}
	}

	@Override
	public void delete(int board_id) throws BoardException{
		try {
			sqlSessionTemplate.delete("Board.delete", board_id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BoardException("삭제실패", e);
		}	
	}
	
}










