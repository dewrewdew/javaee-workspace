package animal;

public class Duck extends Bird {
	// class = 거푸집 instance = 실제로 만들어진 것
	String name = "오리";
	int age = 3;
	static int wing = 2;
	
	public void fly() {
		System.out.println("새가 날아요");
	}
	
	public static void main(String[] args) {
		// age = 4; => 이거 가능? 불가능!! => 왜? 뭘 수정할 인스턴스를 생성하고나서 그 값을 바꾸던가 해야지!
		int x = 6;
		Duck d1 = new Duck();
		Duck d2 = new Duck();
		d1.age=5;
		System.out.println(d1.wing);
		Duck.wing=4;
		System.out.println(d2.wing);
	}
}
