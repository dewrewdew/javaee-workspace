package com.ch.memberapp.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*이 클래스는 평문의 비밀번호를 암호화 시켜 해시로 결과를 반환하는 역할을 함
 * Java의 암호화 처리는 javaEE, javaME 상관없이 javaSE에서 지원
 */
public class ShaManager {
	
	// 메서드 호출 시 매개변수로 평문을 넘겨주면, 암호화 알고리즘을 사용하여 그 값을 반환하는 메서드
	public static String getHash(String password) {
		// String password = "minzino";
		
		
		//  최종적으로 암호화 결과를 모아놓을 객체
		StringBuffer hexString = new StringBuffer();

		try {
			
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			
			// 이 비밀번호 평문을 잘게 쪼개자!!
			// password.getBytes("utf-8");
			
			// 아래의 메서드를 수행하면, 아직 암호화되지 않은 상태의 바이트 배열로 존재하는 데이터를, 암호화 시킴
			// 32바이트의 문자열 반환(비트 아님!)
			byte[] hash=digest.digest(password.getBytes("utf-8")); // 이 메서드는 매개변수로 바이트 배열을 원함
			
			
			for(int i = 0 ; i < hash.length ; i++) {
				// 아래의 hash[i]에 혹여나 1로 시작하는 이진수가 있다면 음수로 해석 하므로 예상치 못한 암호화 문자열이 반환
				// ex) 컴퓨터에서는 이진수에서 맨 앞이 1로 시작하면 "음수"로 생각함
				// [1000 0000] => 음수로 판단! 양수로 어떻게 바꾸지?
				// [0000 0000][0000 0000][0000 0000][1000 0000] 이런식으로 1이 맨 앞에있는애가
				// 대장노릇을 하지 못하게 함! => 이렇게 하면 맨앞은 1이 아니므로 양수가 됨.
				// int 자료형의 크기 => 4byte!
				// 따라서 hash[i]는 1byte짜리였지만 3칸이 늘어나면서 총 4칸짜리 int형이 반환됨!
			String hex=Integer.toHexString(0xff & hash[i]);
			
			// 한자릿수가 포함되면 64자 미만이 되기 때문에
			// 언제나 64자를 유지하기 위해서는 한자릿수가 나온 문자열을 2자릿수로 바꾸자!
			if(hex.length()==1) { // 2자리가 아닌 한자릿수 문자일 경우 0으로 채워넣을 예정
				hexString.append("0");
			}
			// System.out.println(hex);
				hexString.append(hex); // 누적
		}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hexString.toString(); 
		// StringBuffer 자체가 String인것은 아님
		// 따라서 toString()메서드로 변환시켜줘야 함!
	}


	public static void main(String[] args) {
		String result = getHash("dog"); 
		// static 메서드를 호출하는 main() 메서드가 같은 클래스에 존재하므로
		// 인스턴스 생성이 필요 없음
		System.out.println(result);
	}
}