package FactoryMethod.Product;

public class ChicagoStyleClamPizza extends PizzaProduct {

	public ChicagoStyleClamPizza() {
		super("ChicagoClamStyle","ChicagoClamDough","ChicagoClamSause");
	}

	public ChicagoStyleClamPizza(String name, String dough, String sause) {
		super(name, dough, sause);
	}

}
