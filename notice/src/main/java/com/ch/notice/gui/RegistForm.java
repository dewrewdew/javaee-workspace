package com.ch.notice.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ch.notice.domain.Notice;
import com.ch.notice.repository.NoticeDAO;

// 중요한 예시!!!
// 사실상 여기부터 model1방식 시작 DAO(Data Access Object)구현
public class RegistForm extends JFrame{
	// extends는 사실 "is a"의 의미!
	// 자바의 관계는 딱 2종류 !! is a 관계와 has a 관계
	// 클래스가 보유한 멤버변수가 객체형일경우 has a 관계
	JTextField title; // 제목 입력 텍스트 박스
	JTextField writer; // 작성자 입력 텍스트 박스
	JTextArea content; // 내용 입력
	JButton bt; // 등록 버튼
	NoticeDAO dao; // 오직 table에 대해 CRUD만을 처리하는 객체를 보유한다.
	
	// 생성자의 목적은 이 객체의 인스턴스가 생성될 때 초기화할 작업이 있을 경우
	// 초기화 작업을 지원하기 위함.
	public RegistForm() {
		title = new JTextField(30); // 텍스트 박스의 디자인 길이
		writer = new JTextField(30);
		content = new JTextArea(10, 30); // 행, 열 정의해줘야함
		bt = new JButton("등록");
		dao = new NoticeDAO();
		
		// 컴포넌트를 부착하기 전에 레이아웃을 결정하자
		// css div로 레이아웃을 적용하는것과 비슷
		setLayout(new FlowLayout()); // 수평이나 수직으로 흐르는 레이아웃
		
		this.add(title); // 윈도우인 나의 몸체에 title을 부착
		this.add(writer); // 윈도우인 나의 몸체에 writer도 부착
		this.add(content);
		this.add(bt);
		
		
		this.setSize(400, 300); // 레퍼런스 변수 this가 생략되어있음에 항상 주의!
		this.setVisible(true); // 디폴트로가 안보이므로 보이게!
		
		// 버튼에 클릭 이벤트 연결하기
		bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				regist();
			}
		});
	}
	// 게시물 등록 메서드
	public void regist() {
		Notice notice = new Notice(); // empty
		notice.setTitle(title.getText()); // DTO에 제목 주입
		notice.setWriter(writer.getText()); // DTO에 작성자 주입
		notice.setContent(content.getText()); // DTO에 내용 주입
		
		int result=dao.regist(notice); // db에 insert!!
		
		if(result < 1) {
			JOptionPane.showMessageDialog(this, "실패");
		} else {
			JOptionPane.showMessageDialog(this, "성공");
		}
	}
	
	public static void main(String[] args) {
		RegistForm win = new RegistForm();
		
	}
}
