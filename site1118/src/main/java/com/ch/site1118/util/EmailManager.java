package com.ch.site1118.util;

import java.security.MessageDigest;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


// 이메일을 발송해주는 객체 정의
// javase기반에서 이미 메일을 발송할 수 있는 api가 지원됨. jar형태의 라이브러리
// activation-1.1.1 jar, javax.mail-1.5.5 (maven repository =>  매우 좋은 무료 사이트)
public class EmailManager {
	String host="smtp.gmail.com"; // 사용하고자 하는 메일 서버 주소
	String user="dewrewdew@gmail.com"; // 메일 서버의 사용자 계정
	String password="wwcu rrfn etpj gmtl"; // 앱 비밀번호 
	Properties props=new Properties(); // java.util.map의 자식 key-value 쌍을 갖는 데이터 형식
	
	// 메일 발송 메서드
	// to 매개변수의 역할 : 메일 받을 회원가입한 자
	public void send(String to) {
		// props객체에 필요한 모든 설정 정보의 쌍을 대입하자!
		// 참고로 key값은 이미 지정된것이므로 아래의 값을 따르자! 사용하려면 암기해야함!
		props.put("mail.smtp.host",host);
		props.put("mail.smtp.port", 465); // 구글의 smtp(보내는 메일 서버)의 포트 번호
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable","true");
		// props.put("mail.smtp.ssl.trust","smtp.gmail.com");
		props.put("mail.smtp.ssl.protocols","TLSv1.2");
		
		// Session 생성 javax.mail
		Session session=Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// 개발자의 구글 이메일 계정 및 앱 비밀번호 입력
				return new PasswordAuthentication(user, password);
			}
		});
		
		// 제목, 내용등의 메일 작성
		MimeMessage message=new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(user)); // 메일 발송자
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));// 메일 받을 사람
			message.setSubject("회원가입 축하드립니다.");
			message.setContent("<h1>감사합니다.</h1>회원가입 완료되었습니다.", "text/html;charset=utf-8");
			
			// 메일 발송!!
			Transport.send(message);
			
			System.out.println("이메일 발송 성공");
		} catch (Exception e) {
			System.out.println("이메일 발송 실패");
			e.printStackTrace();
		}
	}
}
