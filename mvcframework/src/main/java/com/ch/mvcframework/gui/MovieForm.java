package com.ch.mvcframework.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.ch.mvcframework.movie.model.MovieManager;

/*영화와 관련된 판단을 해주는 공통 로직을 javaSE의 GUI 프로그래밍에서도 사용할 수 있는지 테스트 해보자
 * 테스트 성공한다면, 공통 로직의 작성을 성공한 것이고 재사용 가능성을 극대화한 것임*/
public class MovieForm extends JFrame {
	
	JButton bt;
	JComboBox box;
	String[] movies= {"귀멸의칼날", "주토피아2", "노트북", "라라랜드"};
	MovieManager manager = new MovieManager();
	
	public MovieForm() {
		bt = new JButton("피드백 요청");
		box = new JComboBox();
		
		for(String movie : movies) { // jdk 1.5부터 지원되는 improved for 구문 => 프레임워크와 친함!
			box.addItem(movie);
		}
		
		// 부착전에 html처럼 레이아웃을 먼저 설정
		setLayout(new FlowLayout()); 
		// FlowLayout이란 수평, 수직의 직선으로 컴포넌트를 배치함 => 윈도우 창에 따라 내용물들이 흘러다님
		
		// 버튼을 윈도우에 부착
		// this는 나(movieform)고 나는 윈도우야
		// 부착 순서도 신경쓰기!
		this.add(box); // 콤보박스를 윈도우에 부착
		this.add(bt); // 버튼을 윈도우에 부착
		
		// 버튼에 리스너 연결
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.out.println("나 눌렀어?");
				// 공통 로직인 MovieManager 에게 일 시키기
				
				String item=(String)box.getSelectedItem(); // 유저가 선택한 영화
				System.out.println(item);
				
				String msg = manager.getAdvice(item);
				
				JOptionPane.showMessageDialog(MovieForm.this, msg);
			}
		});
		
		setSize(300, 200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		// MovieForm이 곧 윈도우이므로 MovieForm을 new하면 윈도우창이 메모리에 생성됨 왜? is a 관계이므로
		MovieForm movieForm = new MovieForm();
	}
}
