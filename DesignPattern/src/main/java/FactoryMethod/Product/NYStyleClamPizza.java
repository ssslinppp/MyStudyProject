package FactoryMethod.Product;

public class NYStyleClamPizza extends PizzaProduct {

	public NYStyleClamPizza() {
		super("NYClamStyle","NYClamDough","NYClamSause");
	}

	public NYStyleClamPizza(String name, String dough, String sause) {
		super(name, dough, sause);
	}
}

