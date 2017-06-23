package DecoratorDP.Decorator;

import DecoratorDP.BeverageComponent;
import DecoratorDP.CondimentDecorator;

public class Milk extends CondimentDecorator{
	BeverageComponent beverage;
	
	public Milk(BeverageComponent beverage){
		this.beverage = beverage;
	}

	@Override
	public String getDescription() {
		return beverage.getDescription() + ", Milk";
	}

	@Override
	public double cost() {
		return 0.15 + beverage.cost();
	}

}

