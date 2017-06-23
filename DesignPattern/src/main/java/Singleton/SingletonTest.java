package Singleton;


public class SingletonTest {
	public static void main(String[] args){
		Singleton s1 = Singleton.getInstance();
		s1.setName("AAAAAAAAA");
		System.out.println(s1.getName());
		
		Singleton s2 = Singleton.getInstance();
		System.out.println(s2.getName());
		System.out.println("--------------------");
		s2.setName("BBBBBBBBBBBB");
		System.out.println(s1.getName());
		System.out.println(s2.getName());
		System.out.println("--------------------");
		Singleton s3 = Singleton.getInstance();
		s3.setName("CCCCCCCCC");
		System.out.println(s1.getName());
		System.out.println(s2.getName());
		System.out.println(s3.getName());
	}
}

