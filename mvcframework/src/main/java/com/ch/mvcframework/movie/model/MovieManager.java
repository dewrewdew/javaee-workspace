package com.ch.mvcframework.movie.model;

/*java se건 ee건 상관없이 작동되는 기능을 만들기 위해 기능과 디자인을 분리!!
 * */
public class MovieManager {
	/*모든 플랫폼에서 재사용 가능한 객체 = Model 영역 정의*/
	public String getAdvice(String movie) {
		String msg="선택한 영화가 없음";
		if(movie!=null){ // 파라미터가 있을때만 작동하도록
			if(movie.equals("귀멸의칼날")) {
				msg="대꿀잼 영화";
			} else if(movie.equals("주토피아2")){
				msg="닉 쥬디 꿀귀";
			} else if(movie.equals("노트북")) {
				msg="인생영화";
			} else if(movie.equals("라라랜드")) {
				msg="언덕길에서 춤추는 부분이 젤 좋음";
			}
		}
		return msg;
	}
}