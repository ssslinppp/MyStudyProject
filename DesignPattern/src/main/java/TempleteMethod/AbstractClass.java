package TempleteMethod;

public abstract class AbstractClass {

	/**
	 * 模板方法
	 * 在一个方法中定义了一个【算法】的骨架，而将一些步骤延时到子类中。模板方法使得子类可以在不改变
	 * 算法结构的情况下，重新定义算法中的某些步骤。
	 */
	public final void templeteMethod() {
		primitiveOperation1();    //具体步骤
		concreteOperation1();     //具体步骤
		if(hook()){  //这里使用hook()作为条件控制
			concreteOperation2(); //或者其他方法primitiveOperation2()
		}
	}

	protected abstract void primitiveOperation1();

	/**
	 * 定义为final； 防止子类覆盖
	 */
	public final void concreteOperation1() {
		System.out.println("concreteOperation1");
	}
	
	public final void concreteOperation2() {
		System.out.println("concreteOperation2");
	}

	/**
	 * 钩子方法
	 * 声明在抽象类中，可以为【空】，或者有【默认实现】。
	 * 可以让子类有能力对算法的不同点进行挂钩。
	 * 常见用法：
	 * 1. 作为条件控制
	 * 2. 可以让子类能够有机会对模板方法中某些即将发生（或刚刚发生的）步骤做出反应。
	 */
	public boolean hook() {
		return true;
	}
}























