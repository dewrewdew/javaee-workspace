package com.ch.model1.dto;

// 이 클래스는 로직을 작성하기 위함이 아니라, 데이터베이스의 board라는 테이블에 대해 CRUD에 사용하기 위한 DTO이다.
// Data Transfer Object
// ex) select board_id, title, writer, content, regdate, hit from board where board_id=5; (원래는 *은 지양함 => 과부하걸림) 
public class Board {
	// 아무생각없이 나올때까지 반복!!
	private int board_id;
	private String title;
	private String writer;
	private String content;
	private String regdate;
	private int hit;
	
	
	public int getBoard_id() {
		return board_id;
	}
	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdateString) {
		this.regdate = regdateString;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
}
