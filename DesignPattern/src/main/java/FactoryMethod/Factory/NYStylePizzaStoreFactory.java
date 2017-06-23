package FactoryMethod.Factory;

import FactoryMethod.Product.NYStyleCheesePizza;
import FactoryMethod.Product.NYStyleClamPizza;
import FactoryMethod.Product.PizzaProduct;

public class NYStylePizzaStoreFactory extends PizzaStoreFactory {

	@Override
	protected PizzaProduct createPizza(String type) {
		PizzaProduct pizza = null;

		if ("cheese".equals(type)) {
			pizza = new NYStyleCheesePizza();
		} else {
			pizza = new NYStyleClamPizza();
		}

		return pizza;
	}

}
