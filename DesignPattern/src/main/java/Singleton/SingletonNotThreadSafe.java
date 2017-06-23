package Singleton;

/**
 * 非线程安全
 *
 */
public class SingletonNotThreadSafe {
	private static SingletonNotThreadSafe instance;

	private SingletonNotThreadSafe() {}

	public static synchronized SingletonNotThreadSafe getInstance() {
		if (instance == null) {
			instance = new SingletonNotThreadSafe();
		}
		return instance;
	}
}

