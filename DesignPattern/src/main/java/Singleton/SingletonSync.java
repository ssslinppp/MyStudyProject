package Singleton;

/**
 * 线程安全+延时初始化（性能受影响）
 * 使用synchronized同步，在高并发时，有性能问题
 * 
 * 优点：
 * 简单，有效
 * 缺点：
 * 并发性能不好
 */
public class SingletonSync {
	private static SingletonSync instance ;
	
	private SingletonSync(){}

	public static synchronized SingletonSync getInstance(){
		if(instance == null){
			instance = new SingletonSync();
		}
		return instance;
	}
}

