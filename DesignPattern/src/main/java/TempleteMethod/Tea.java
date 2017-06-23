package TempleteMethod;

public class Tea extends AbstractClass {
	@Override
	protected void primitiveOperation1() {
		System.out.println("brew tea");
	}

	/**
	 * 覆盖hook()方法
	 */
	public boolean hook() {
		Boolean rt = false;
		//TODO 其他logic，用于决定rt值
		return rt;
	}
}
