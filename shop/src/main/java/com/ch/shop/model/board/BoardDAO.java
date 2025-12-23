package com.ch.shop.model.board;

import java.util.List;
import com.ch.shop.dto.Board;

public interface BoardDAO {
	public void insert(Board board);
	public List selectAll(); //모든 레코드 가져오기 
	public Board select(int board_id); //한건 가져오기
	public void update(Board board); //한건 수정 
	public void delete(int board_id); //한건 삭제
	
}
