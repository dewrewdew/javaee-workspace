package com.ch.shop.model.subcategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // @Bean으로 등록해도 되지만 스프링이 지원하는 유명한 컴포넌트에 해당하므로 자동으로 인스턴스를 만들자!
public class SubCategoryServiceImpl implements SubCategoryService{
	
	// 느슨하게 보유하기 위해 MybatisSubCategoryDAO가 아닌, 상위 자료형을 보유한다.
	@Autowired // 스프링 컨테이너가 관리하고 있던 bean을 여기로 주입해달라고 요청하기. 만들었는지 확인하고 만들어져있으면 주입해달라고 요청하는 순서!
	private SubCategoryDAO subCategoryDAO;

	@Override
	public List getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getList(int topcategory_id) {
		
		return subCategoryDAO.selectByTopCategoryId(topcategory_id);
	}
}
