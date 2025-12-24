package com.ch.shop.dto;

import java.awt.peer.ListPeer;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Product {
	
	private int product_id;//0
	private String product_name;
	private String brand;
	private int price;
	private int discount;
	private List<Color> colorList;//색상
	private List<Size> sizeList;//사이즈
	private List<ProductImg> productImgList;
	private String introduce;
	private String detail;
	private SubCategory subCategory;//하위카테고리
	
	//MultipartFile 은 업로드된 이미지 1개에 대한 정보를 가진 객체..
	//사용 시 주의할 점 : 반드시 <input type="file"> 의 이름과 일치해야  한다. 즉 파라미터 명과 MultipartFile의 변수명이 일치해야 한다
	//아래의 멤버변수에  html컴포넌트와 일치하는 이름으로, 객체를 선언하면 자동으로 업로드된 파일이 매핑이 된다..
	//하지만, 아직 하드디스크에 저장된 상태는 아니면, 메모리에 보관된 상태임..
	//그럼 언제 저장되나?  개발자가 transferTo()메서드를 호출할때, 임시디렉토리나 메모리에 존재하던 파일이 실제 개발자가 지정한 
	//디렉토리와 파일명으로 존재하게 됨..이때 임시디렉토리 안에 있는 파일은 개발자가 제어안해도됨..
	private MultipartFile[] photo;
}







