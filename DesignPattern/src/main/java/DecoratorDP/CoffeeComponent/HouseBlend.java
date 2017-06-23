package DecoratorDP.CoffeeComponent;

import DecoratorDP.BeverageComponent;

public class HouseBlend extends BeverageComponent{

	public HouseBlend() {
		setDescription("HouseBlend");
	}

	@Override
	public double cost() {
		return 0.89;
	}

}
