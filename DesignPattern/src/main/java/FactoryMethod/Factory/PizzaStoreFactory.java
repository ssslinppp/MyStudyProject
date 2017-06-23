package FactoryMethod.Factory;

import FactoryMethod.Product.PizzaProduct;

public abstract class PizzaStoreFactory {
	public PizzaProduct orderPizza(String type) {
		PizzaProduct pizza;
		pizza = createPizza(type);

		pizza.prepare();
		pizza.bake();
		pizza.cut();
		return pizza;
	}

	protected abstract PizzaProduct createPizza(String type);  //抽象工厂方法
}
