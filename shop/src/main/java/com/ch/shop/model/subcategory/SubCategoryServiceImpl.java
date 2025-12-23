package com.ch.shop.model.subcategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service //@Bean으로 등록해도 되지만, 스프링이 지원하는 유명한 컴포넌트에 해당하므로, 자동으로 인스턴스를 생성하여 사용하자
public class SubCategoryServiceImpl implements SubCategoryService{
	
	@Autowired //스프링 컨테이너가 관리하고 있던 빈을 여기로 주입해달라고 요청!!
	private SubCategoryDAO subCategoryDAO;//느슨하게 보유하기 위해 MybatisSubCategoryDAO가 아닌, 상위 자료형을 보유한다
	
	@Override
	public List getList() {
		return null;
	}

	@Override
	public List getList(int topcategory_id) {
		return subCategoryDAO.selectByTopCategoryId(topcategory_id);
	}

}
