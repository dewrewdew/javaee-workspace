package notice;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DetailController extends HttpServlet{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

    // 웹 브라우저로 접근하는 유저에게 한그 메시지 출력하기
    resp.setContentType("text/html; charset=utf-8"); // 응답 객체에 인코딩 지정(한글, 일본어 등 전세계 문자 안꺠지도록)

    // 출력 스트림에 출력할 문자열 등록
    PrintWriter out = resp.getWriter();
    out.print("나의 서블릿으로 한글 출력");
    }
}
