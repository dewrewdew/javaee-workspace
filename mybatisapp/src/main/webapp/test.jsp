<%@page import="com.ch.mybatisapp.dto.News"%>
<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@page import="com.ch.mybatisapp.config.MybatisConfig"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%
	MybatisConfig mybatisConfig=MybatisConfig.getInstance();

	SqlSession sqlSession = mybatisConfig.getSqlSession();
	
	// Mybatis는 개발자가 자바 소스안에 쿼리문을 작성하도록 하지 않음
	// 즉, 개발자는 쿼리문을 xml에 작성함으로써, 잡다한 jdbc 주변 코드를 작성하지 않고
	// 오직 쿼리에 집중할 수 있도록 지원
	News news = new News(); // 
	news.setTitle("마이바티스 연습");
	news.setWriter("월욜");
	news.setContent("연습내용");
	int result = sqlSession.insert("com.ch.mybatisapp.dto.News.insert", news);
	
	// mybatis의 SqlSession은 DML 수행 시 트랜잭션을 commit해야 함
	sqlSession.commit();
	
	if(result <1) {
		out.print("등록 실패");
	} else {
		out.print("등록 성공");
	}
	
	mybatisConfig.release(sqlSession);

%>