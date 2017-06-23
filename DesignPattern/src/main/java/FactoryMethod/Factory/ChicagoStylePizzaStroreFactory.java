package FactoryMethod.Factory;

import FactoryMethod.Product.ChicagoStyleCheesePizza;
import FactoryMethod.Product.ChicagoStyleClamPizza;
import FactoryMethod.Product.PizzaProduct;

public class ChicagoStylePizzaStroreFactory extends PizzaStoreFactory {

	@Override
	protected PizzaProduct createPizza(String type) {
		PizzaProduct pizza = null;

		if ("cheese".equals(type)) {
			pizza = new ChicagoStyleCheesePizza();
		} else {
			pizza = new ChicagoStyleClamPizza();
		}

		return pizza;
	}

}
