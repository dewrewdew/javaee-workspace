package com.ch.shop.model.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ch.shop.dto.Board;
import com.ch.shop.exception.BoardException;

/*
 * 서비스는 모델 영역에서 여러 모델 객체들에게 일을 시키는 역할을 수행
 * 대표적 업무) 여러 DAO들에게 일을 시키고, 트랜잭션 상황에서 트랜잭션을 처리할 의무를 가진 객체
	만일 서비스의 존재가 없을 경우, 컨트롤러가 서비스의 역할을 수행하게 되므로, 이때부터는 컨트롤러의 코드에
	모델영역의 업무가 혼재되어, MVC의 원칙이깨져버린다..추후 코드의 분리가 될 수없다.즉 모델 코드를 재사용할수없게됨..
 * */
@Service //ComponentScan의 대상이 되어 자동으로 인스턴스를 올리고, 빈컨테이너에서 관리해달라는 뜻
public class BoardServiceImpl implements BoardService{
											/* is a */		
	@Autowired
	private BoardDAO boardDAO;
	
	//setter 를 정의하여 외부에서 DAO의 인스턴스를 넘겨받자
	//왜 굳이 여기서 new 를 하지 않고, 주입을 받아야 하나? DI때문에...즉 의존성 약화시키기 위해..

	//DAO에 글 등록 일시키기
	public void regist(Board board) throws BoardException{
		//에러가 났을음 유저가 알아야 하므로, 즉 예외의 전달이 목적이므로
		//여기서 서비스가 예외를 잡으면 안된다!!
		boardDAO.insert(board);
	}

	@Override
	public List selectAll() {
		return boardDAO.selectAll();
	}

	@Override
	public Board select(int board_id) {
		return boardDAO.select(board_id);
	}

	@Override
	public void update(Board board) throws BoardException{//컨트롤러까지 예외 전달..
		boardDAO.update(board);		
	}

	@Override
	public void delete(int board_id) throws BoardException {
		boardDAO.delete(board_id);		
	}

	
}











