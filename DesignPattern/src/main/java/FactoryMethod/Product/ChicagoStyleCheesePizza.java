package FactoryMethod.Product;

public class ChicagoStyleCheesePizza extends PizzaProduct {

	public ChicagoStyleCheesePizza() {
		super("ChicagoStyle","ChicagoDough","ChicagoSause");
	}

	public ChicagoStyleCheesePizza(String name, String dough, String sause) {
		super(name, dough, sause);
	}

}
