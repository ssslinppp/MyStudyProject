package DecoratorDP.Decorator;

import DecoratorDP.BeverageComponent;
import DecoratorDP.CondimentDecorator;

public class Whip extends CondimentDecorator{
	BeverageComponent beverage;
	
	public Whip(BeverageComponent beverage){
		this.beverage = beverage;
	}

	@Override
	public String getDescription() {
		return beverage.getDescription() + ", Whip";
	}

	@Override
	public double cost() {
		return 1.25 + beverage.cost();
	}

}

