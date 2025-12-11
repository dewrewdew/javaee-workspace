package com.ch.mvcframework.repository;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.dto.Emp;
import com.ch.mvcframework.exception.EmpException;
import com.ch.mvcframework.mybatis.MybatisConfig;

public class EmpDAO {
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();
	
	// 1명 등록
	// throws가 명시된 메서드를 호출한 사람은 throws에 명시된 예외를 처리할 책임을 전가함.
	public void insert(SqlSession sqlSession, Emp emp) throws EmpException{
		
		
		try {
			sqlSession.insert("Emp.insert", emp); // 여기서 오류가 있음을 알려야 rollback을 할지말지 결정을 하니까 예외처리를 이용한다!!! 프로그램이 강요하지 않았지만 내가 필요해서 하는 것!
			// 근데 여기서만 알면 안되고 이 메서드를 호출하는 controller에게 알려야 그 컨트롤러가 rollback이나 commit을 할 지 결정할 수 있음.
		} catch (Exception e) {
			e.printStackTrace();
			
			// throw는 예외를 일으키는 코드! => 이기 때문에 개발자 다음의 2가지 중 하나를 선택해야 한다.
			// 1) try catch로 받기
			// 2) 여기서 발생한 예외를 이 메서드 호출자에게 책임 전가 => throws
			throw new EmpException("사원 등록 실패", e); // throw : 예외를 일부러 일으키는것. 예외를 커스텀하는것! => 감기 예방을 위해 오히려 바이러스를 주입하는것과 같음
		}
		
		// 강요된 예외(try~catch) : 개발자가 예외처리를 하지 않으면 빨간줄이 생기면서 컴파일이 불가능
		// 런타임 예외
	
	}
}
