package FactoryMethod;

import FactoryMethod.Factory.ChicagoStylePizzaStroreFactory;
import FactoryMethod.Factory.NYStylePizzaStoreFactory;
import FactoryMethod.Factory.PizzaStoreFactory;

public class PizzaTestDrive {
	public static void main(String arg[]){
		PizzaStoreFactory nyStore = new NYStylePizzaStoreFactory();
		PizzaStoreFactory chicagoStore = new ChicagoStylePizzaStroreFactory();
		
		nyStore.orderPizza("cheese");
		System.out.println("---------------------");
		chicagoStore.orderPizza(null);
	}

}
