package Singleton;

/**
 * 线程安全+延时初始化+高性能
 * ---------------------------------
 * 优点：
 * 线程安全，并发性高，可延时初始化
 * 
 * 缺点：
 * 代码稍复杂
 * 
 */
public class Singleton {
	private String name;
	
	private Singleton() {

	}

	private static class SingletonHolder {
		/*
		 * 因为是static类成员变量，所以自始至终都只会创建一个单例
		 * 使用static初始化，不需要显示的同步
		 */
		public static Singleton singleton = new Singleton();
	}

	/**
	 * 当任何一个线程在【首次调用】getInstance()时，都会使SingletonHolder被加载和初始化
	 * @return
	 */
	public static Singleton getInstance() {
		return Singleton.SingletonHolder.singleton;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

