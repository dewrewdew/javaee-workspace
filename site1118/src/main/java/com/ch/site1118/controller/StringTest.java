package com.ch.site1118.controller;

public class StringTest {
	public static void main(String[] args) {
		String str1 = "korea";
		String str2 = "korea";
		
		
		str1+="happy";
		// String은 "불변객체" => 망치로 때려도 절대 깨지지 않는 다이아조각이라고 생각할 것.
		// 따라서 str1에다가 happy를 붙여서 koreahappy를 만들고 싶었겠지만
		// 9번 라인에서 그냥 koreahappy라는 새로운 객체를 만들어버림
		// 메모리 사용량 급증함
		// 그래서 사용하는게 뭐다? StringBuffer, StringBuilder!!
		
	}
}
