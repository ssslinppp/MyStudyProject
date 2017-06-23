package Singleton;

/**
 * ThreadSafe
 * 线程安全+没有延时初始化
 * 适合于在创建和运行时负担不太繁重的情况
 *
 */
public class SingletonWithoutLazy {
	//在static initialize中创建单件，可以保证线程的安全性
	private static SingletonWithoutLazy instance = new SingletonWithoutLazy();
	
	private SingletonWithoutLazy(){}

	public static SingletonWithoutLazy getInstance(){
		return instance;
		
	}
}

