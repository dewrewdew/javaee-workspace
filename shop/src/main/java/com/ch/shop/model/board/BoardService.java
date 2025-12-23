package com.ch.shop.model.board;

import java.util.List;
import com.ch.shop.dto.Board;
import com.ch.shop.exception.BoardException;

//이 객체를 보유하게될 컨트롤러가, 너무 정확하고 구체적인 서비스객체를 보유하게 되면
//추후 서비스객체가 사라지거나 변경사항이 생길경우 이를 의존하고 있었던 컨트롤러가 영향을 받으므로
//그 영향을 최소화시키기 위해 DI 를 준수하자!
public interface BoardService {
	//앞으로 이 서비스 객체를 구현할 모든 서비스들이 반드시 구현해야 할 메서드를 정의 
	public void regist(Board board);
	public List selectAll();
	public Board select(int board_id);
	public void update(Board board);
	public void delete(int board_id);
}









