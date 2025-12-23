package com.ch.shop.model.product;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ch.shop.dto.Product;
import com.ch.shop.exception.ProductException;

@Repository
public class MybatisProductDAO implements ProductDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	//개발자 뿐만 아니라, 일반 사용자가 에러의 원인을 알려면, 이 메서드를 호출한 외부 호출자에게
	//에러를 전달해야 하니깐...
	public void insert(Product product) throws ProductException{
		try {
			sqlSessionTemplate.insert("Product.insert", product);
		} catch (Exception e) {
			e.printStackTrace();//개발자가 에러의 원인을 알기 위해 서버의 콘솔에 출력...
			throw new ProductException("상품 insert 실패", e);
		}
	}

	@Override
	public List selectAll() {
		return sqlSessionTemplate.selectList("Product.selectAll");
	}

}








