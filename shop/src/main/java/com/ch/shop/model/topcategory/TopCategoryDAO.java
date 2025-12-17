package com.ch.shop.model.topcategory;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ch.shop.dto.TopCategory;

// 서비스가 이 객체를 보유할 때 느슨하게 보유하려면 인터페이스로 선언하기!
@Repository
public interface TopCategoryDAO {
	public List selectAll();
}