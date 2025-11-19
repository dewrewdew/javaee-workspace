package animal;

public class Eagle extends Bird{
	String name = "나는 독수리";
	int age = 3;
	
	public void fly() {
		System.out.println("독수리가 날아요");
	}
	public static void main(String[] args) {
		Eagle e1 = new Eagle();
		System.out.println(e1.name);
		e1.fly();
		
		
	}
}
