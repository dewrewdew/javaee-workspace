package com.ch.mvcframework.emp.model;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.dto.Emp;
import com.ch.mvcframework.exception.EmpException;
import com.ch.mvcframework.mybatis.MybatisConfig;
import com.ch.mvcframework.repository.DeptDAO;
import com.ch.mvcframework.repository.EmpDAO;

/*애플리케이션의 영역 중 서비스를 정의한다.
 * Service의 정의 목적
 * 1) 서비스가 없을 경우 DAO들에게 일을 시키는 업무가 Controller에게 부담이 됨 => 사실상 sqlSession같은거 생성해서 보내주는것까지 controller가 할 필요가 없음!
 * 		따라서 이 시점부터는 컨트롤러가 아닌 모델이 되어버림
 * 
 * 2) 컨트롤러를 DAO와 분리시키로 트랜잭션을 대신 처리할 객체가 필요함.*/
public class EmpService {
	public void regist(Emp emp) throws EmpException{
		/*
		 * DeptDAO와 EmpDAO가 같은 트랜잭션으로 묶이려면 각각의 DAO는 공통의 SqlSession을 사용해야 한다.
		 * 따라서, 이 컨트롤러에서 mybatisConfig로부터 SqlSession을 하나 취득한 후 insert문 호출 시 같은 주소값을 갖는 공유된 sqlSession을 나눠주자
		 * */
		MybatisConfig mybatisConfig=MybatisConfig.getInstance();
		DeptDAO deptDAO = new DeptDAO();
		EmpDAO empDAO = new EmpDAO();
		
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		// mybatis는 디폴트가 autocommit=false로 되어있으므로 개발자가 별도로 트랜잭션 시작을 알릴 필요가 없음
		
		
		try {
			deptDAO.insert(sqlSession, emp.getDept());
			empDAO.insert(sqlSession, emp);
			sqlSession.commit(); // 트랜잭션 확정 => 사실상 이거때문에 service의 역할이 만들어졌다고해도 과언이 아님.
		} catch (Exception e) { // 여기서 try catch문이 없으면 프로그램이 비정상 종료됨. (insert int 로 일부러 오타를 냈으므로)
			// 롤백은 sqlsession이 하는 것
			e.printStackTrace();
			sqlSession.rollback(); // 둘중에 누가 잘못되었던 상관없음 무조건 롤백
			// 아래의 throw 코드에 의해 에러가 발생한다. 따라서 개발자는 두가지 중 하나를 선택해야 한다.
			// 1) 예외를 여기서 잡을지, try catch
			// 2) 예외를 여기서 잡지 않고 외부의 메서드 호출자에게 전달할 지 throws
			throw new EmpException("사원 등록 실패", e); // 얘를 e.printStackTrace(); 위로 올리면 throw가 예외를 먼저 만들어버려서 프로그램이 비정상적으로 종료됨. 그래서 아래 코드가 실행되지 않음.
		} finally {
			mybatisConfig.release(sqlSession);			
		}
	}
}
