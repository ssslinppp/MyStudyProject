package FactoryMethod.Product;

public abstract class PizzaProduct {
	protected String name;
	protected String dough; // 面团
	protected String sause; // 酱料

	public PizzaProduct(){}
	
	public PizzaProduct(String name, String dough, String sause) {
		super();
		this.name = name;
		this.dough = dough;
		this.sause = sause;
	}

	public void prepare() {
		System.out.println("prepare " + name);
	}

	public void bake() {
		System.out.println("bake " + dough);
	}

	public void cut() {
		System.out.println("cut " + sause);
	}

	public String getName() {
		return name;
	}
}


