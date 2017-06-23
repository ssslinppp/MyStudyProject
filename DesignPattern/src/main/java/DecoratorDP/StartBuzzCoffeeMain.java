package DecoratorDP;

import DecoratorDP.CoffeeComponent.Espressso;
import DecoratorDP.CoffeeComponent.HouseBlend;
import DecoratorDP.Decorator.Milk;
import DecoratorDP.Decorator.Mocha;
import DecoratorDP.Decorator.Whip;

public class StartBuzzCoffeeMain {
	public static void main(String[] arg){
		BeverageComponent beverage = new Espressso();  //被装饰者
		System.out.println(beverage.getDescription()+" $"+beverage.cost());
		
		BeverageComponent beverage2 = new HouseBlend();
		beverage2 = new Mocha(beverage2);
		beverage2 = new Milk(beverage2);
		beverage2 = new Whip(beverage2);
		System.out.println(beverage2.getDescription()+" $"+beverage2.cost());
		
		BeverageComponent beverage3 = new Espressso();
		beverage3 = new Mocha(beverage3);
		beverage3 = new Milk(beverage3);
		beverage3 = new Whip(beverage3);
		System.out.println(beverage3.getDescription()+" $"+beverage3.cost());
	}

}

