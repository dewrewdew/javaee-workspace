package com.ch.shop.model.color;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColorServiceImpl implements ColorService{
	
	@Autowired
	private ColorDAO colorDAO;
	
	@Override
	public List getList() {
		return colorDAO.selectAll();
	}
	
}
