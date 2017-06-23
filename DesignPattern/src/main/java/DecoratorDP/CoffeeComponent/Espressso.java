package DecoratorDP.CoffeeComponent;

import DecoratorDP.BeverageComponent;

public class Espressso extends BeverageComponent {
	public Espressso(){
		setDescription("Espressso");
	}

	@Override
	public double cost() {
		return 1.99;
	}

}

