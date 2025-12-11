package com.ch.mvcframework.emp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.controller.Controller;
import com.ch.mvcframework.dto.Dept;
import com.ch.mvcframework.dto.Emp;
import com.ch.mvcframework.exception.EmpException;
import com.ch.mvcframework.mybatis.MybatisConfig;
import com.ch.mvcframework.repository.DeptDAO;
import com.ch.mvcframework.repository.EmpDAO;

/*사원 등록 요청을 처리하는 하위 컨트롤러
 * 3단계 : 일 시키기
 * 4단계 : 등록이니까 생략
 * */
public class RegistController implements Controller{
	/*
	 * DeptDAO와 EmpDAO가 같은 트랜잭션으로 묶이려면 각각의 DAO는 공통의 SqlSession을 사용해야 한다.
	 * 따라서, 이 컨트롤러에서 mybatisConfig로부터 SqlSession을 하나 취득한 후 insert문 호출 시 같은 주소값을 갖는 공유된 sqlSession을 나눠주자
	 * */
	MybatisConfig mybatisConfig=MybatisConfig.getInstance();
	DeptDAO deptDAO = new DeptDAO();
	EmpDAO empDAO = new EmpDAO();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 부서관련 정보 => Dept2 테이블에 등록
		String deptno=request.getParameter("deptno");
		String dname=request.getParameter("dname");
		String loc=request.getParameter("loc");
		
		Dept dept = new Dept(); // empty
		dept.setDeptno(Integer.parseInt(deptno));
		dept.setDname(dname);
		dept.setLoc(loc);
		
		
		// 사원관련 정보 => Emp2 테이블에 등록
		String empno=request.getParameter("empno");
		String ename=request.getParameter("ename");
		String sal=request.getParameter("sal");
		
		Emp emp = new Emp(); // empty
		emp.setEmpno(Integer.parseInt(empno)); // 부서번호
		emp.setEname(ename); // 사원명
		emp.setSal(Integer.parseInt(sal)); // 급여
		
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		// mybatis는 디폴트가 autocommit=false로 되어있으므로 개발자가 별도로 트랜잭션 시작을 알릴 필요가 없음
		
		
		try {
			deptDAO.insert(sqlSession, dept);
			empDAO.insert(sqlSession, emp);
			sqlSession.commit(); // 트랜잭션 확정
		} catch (Exception e) { // 여기서 try catch문이 없으면 프로그램이 비정상 종료됨. (insert int 로 일부러 오타를 냈으므로)
			// 롤백은 sqlsession이 하는 것
			e.printStackTrace();
			sqlSession.rollback(); // 둘중에 누가 잘못되었던 상관없음 무조건 롤백
		} finally {
			mybatisConfig.release(sqlSession);			
		}
	}

	@Override
	public String getViewName() {
		return null;
	}

	@Override
	public boolean isForward() {
		return false;
	}

}
