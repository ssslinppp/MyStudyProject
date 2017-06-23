package DecoratorDP.Decorator;

import DecoratorDP.BeverageComponent;
import DecoratorDP.CondimentDecorator;

public class Mocha extends CondimentDecorator{

	BeverageComponent beverage;
	
	public Mocha(BeverageComponent beverage){
		this.beverage = beverage;  
	}
	
	@Override
	public String getDescription() {
		return beverage.getDescription()+", Mocha";
	}

	@Override
	public double cost() {
		return 0.20 + beverage.cost();
	}

}


