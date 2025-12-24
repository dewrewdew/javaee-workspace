package com.ch.shop.dto;

import lombok.Data;
import lombok.experimental.PackagePrivate;

/* 구글 뿐만 아니라, 네이버, 카카오 등 IDP 연동 시 필요한 정보를 담는 객체*/
@Data
public class OAuthClient {
	private String provider; // google, naver, kakao 구분값
	private String clientId; // 개발자 콘솔에서 앱 등록 시 발급 받은 클라이언트 id
	private String clientSecret; // 개발자 콘솔에서 앱 등록 시 발급 받은 클라이언트 비밀번호 - 비공개
	private String authorizeUrl; // 클라이언트가 sns 로그인 버튼 누를 때 요청 대상 URL
	private String tokenUrl; // 리소스 오너의 정보를 조회할 때 사용할 요청 주소 URL
	private String userInfoUrl; // 구글에 등록된 사용자 정보를 조회할 때 사용할 URL
	private String scope; // 허용 범위
	private String redirectUri; // Provider로부터 콜백받을 주소
}
