<%@ page contentType="text/html; charset=UTF-8"%>
<% 
	// 하나의 페이지에 많은 양의 데이터가 출력되면 스크롤이 발생하므로,
	// 한 페이지당 보여질 레코드 수에 제한을 가하고
	// 나머지 데이터에 대해서는 여러 페이지 링크를 지원해주려면, 총 게시물 수에 대해 산수계산이 요구됨!
	
	// 기본 전제 조건 => 총 레코드 수가 있어야 한다.
	int totalRecord = 26; // 총 레코드 수
	int pageSize = 10; // 페이지당 보여질 레코드 수(임의로 결정하는것! 취향 차이)
	// int totalPage = 로직; // 총 페이지 수
	int totalPage = (int)Math.ceil((float)totalRecord/pageSize);// 총 페이지 수
	int blockSize=10; // 블럭당 보여질 페이지 수
	int currentPage=1; // 현재 유저가 보고있는 페이지
	if(request.getParameter("currentPage") != null) { // 파라미터가 존재할 때만 적용! 없어도 오류나지 않도록!
	currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	int firstPage = currentPage - (currentPage -1)%blockSize; // 블럭당 반복문의 시작 값
	int lastPage = firstPage + pageSize -1; // 블럭당 반복문의 끝 값
	int num=totalRecord-((currentPage-1)*pageSize); // 페이지당 시작 번호 ex) 1 page일때는 26부터 차감, 2 page일때는 16부터 시작 
	
%>
<%="totalRecord" + totalRecord + "<br>"%>
<%="pageSize" + pageSize + "<br>"%>
<%="totalPage" + totalPage + "<br>"%>
<%="현재 당신이 보고있는 currentPage= " + currentPage + "<br>"%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset=UTF-8>
<style>
table {
  border-collapse: collapse;
  border-spacing: 0;
  width: 100%;
  border: 1px solid #ddd;
}

th, td {
  text-align: left;
  padding: 16px;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}
a{text-decoration:none;}

/*유저가 현재 보고있는 페이지에 대한 시각적 효과를 주기 위함*/
.numStyle{
	font-size : 50px;
	font-weight:bold;
	color:red;
}
</style>
</head>
<body>
	
	<table>
	  <tr>
	    <th>No</th>
	    <th>제목</th>
	    <th>작성자</th>
	    <th>등록일</th>
	    <th>조회수</th>
	  </tr>
	  
	  <%for(int i = 1 ; i <= pageSize ; i++){ %>
	  <%if(num < 1) break; //게시물의 넘버는 1까지만 유효하므로 1보다 작아지면 반복문 빠져나오기 %> 
	  <tr>
	    <td><%=num--%></td>
	    <td>곱창</td>
	    <td>못 먹어서</td>
	    <td>속상</td>
	    <td>해유</td>
	  </tr>
	  <%} %>
	  <tr>
	  	<td colspan="5" align="center">
	  	<a href="/paging/list.jsp?currentPage=<%=firstPage - 1 %>">prev</a>
	  	<%for(int i = firstPage ; i <= lastPage ; i++){ %>
	  	<%if(i > totalPage) break; // 총 페이지 수를 넘어설 경우 더이상 반복문을 수행하면 안됨%>
	  	<a <%if(currentPage == i) { %> class = "numStyle" <%} %>href="/paging/list.jsp?currentPage=<%=i %>">[ <%=i %> ]</a>
	  	<!-- 서버에다가 다음 페이지를 요청한다고 생각해야함 내가 나를 링크건다고 생각하지 말고!! -->
	  	<!-- get 방식으로 가겠다고 결정했으면 ? 쓸 생각 바로 하고있기!! -->
	  	<%} %>
	    <a href="/paging/list.jsp?currentPage=<%=lastPage + 1 %>">next</a>
	  	</td>
  	  </tr>
	</table>

</body>
</html>
