package board;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter; // 문자 기반의 출력 스트림
import java.io.IOException;
import javax.servlet.ServletException;


// extends를 하지 않으면 그냥 java SE 개발! extends를 해야 비로소 EE시작!
public class ListServlet extends HttpServlet{
    
    
    // 웹브라우저로 접근하는 유저들에게 메시지 출력하기
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        // javaSE의 스트림 객체를 사용한다.
        PrintWriter out = response.getWriter();
        // 응답 객체로부터 스트림을 얻는다. 왜? 클라이언트에게 문자열 출력하려고
        out.print("this is my response data");
    }
}