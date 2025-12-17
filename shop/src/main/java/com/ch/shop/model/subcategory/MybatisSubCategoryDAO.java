package com.ch.shop.model.subcategory;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ch.shop.dto.SubCategory;

@Repository
public class MybatisSubCategoryDAO implements SubCategoryDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List selectByTopCategoryId(int topcategory_id) {
		return sqlSessionTemplate.selectList("SubCategory.selectByTopCategoryId", topcategory_id);
	}
	
}
