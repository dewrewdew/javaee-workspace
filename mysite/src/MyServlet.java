/*
JavaEE 기반으로 웹 어플리케이션에서 실행될 수 있는 특수한 클래스를 사용해야 하는데
이러한 서버에서만 해석 및 실행되어지는 클래스를 가리켜 서블릿(Servlet)이라 한다.
그리고 javaEE기반의 웹 애플리케이션의 구성 디렉토리는 javaEE스펙으로 정해져 있기 때문에
반드시 정해진 디렉토리에 .class, .jar등을 위치시켜야 한다.

/WEB-INF : 웹브라우저를 통해서는 절대로 접근하지 못하는 보안된 디렉토리
    -classes : 컴파일된 클래스들 보관
    -lib : .jar들 보관
*/

class MyServlet{
    String name = "puppy";
}