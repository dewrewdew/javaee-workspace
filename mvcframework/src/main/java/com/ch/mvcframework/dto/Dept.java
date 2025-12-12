package com.ch.mvcframework.dto;

import java.util.List;

import lombok.Data;

@Data
public class Dept {
	private int deptno;
	private String dname;
	private String loc;
	// 부서의 관점에서 본다면 하나의 부서에는 여러명의 사원이 있을 수 있다.
	private List<Emp> empList;
}
