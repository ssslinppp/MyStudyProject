package FactoryMethod.Product;

public class NYStyleCheesePizza extends PizzaProduct{

	public NYStyleCheesePizza() {
		super("NYStyle","NYDough","NYSause");
	}

	public NYStyleCheesePizza(String name, String dough, String sause) {
		super(name, dough, sause);
	}
}
