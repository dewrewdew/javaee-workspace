package com.ch.gallery.util;

// 문자열 처리와 관련되어 자주 사용되는 기능을 모아놓은 유틸 클래스
public class StringUtil {

	// 주어진 파일 경로에서 확장자만을 추출하는 메서드
	public static String getExtendFrom(String oriName) {
		int lastIndex = oriName.lastIndexOf("."); // 가장 마지막점의 위치를 반환 지난.여름.사진.jpg
		String extend = oriName.substring(lastIndex+1, oriName.length());
		return extend;
	}
}
