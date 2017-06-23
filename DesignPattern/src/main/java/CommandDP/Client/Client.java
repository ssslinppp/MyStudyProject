package CommandDP.Client;

import CommandDP.ConcreteCommand.DoorOpenCommand;
import CommandDP.ConcreteCommand.LightOnCommand;
import CommandDP.ConcreteCommand.MacroCommand;
import CommandDP.ICommand.Command;
import CommandDP.Invoke.RemoteControl;
import CommandDP.Receiver.Door;
import CommandDP.Receiver.Light;

/**
 * 命令行模式的Client
 * 1. 创建具体的Command；
 * 2. 设置Command的接收者；
 *
 */
public class Client {
	public static void main(String arg[]){
		/**
		 *    通过Invoke实现对所有的Command的调用
		 * 1. 先设置命令对象（请求）：setCommand()
		 * 2. 调用execute()，执行具体的请求；
		 * 3. 可以执行undo()，来执行取消操作；		
		 */
		RemoteControl remote = new RemoteControl();
		
		//创建 开灯和 开门请求
		Light light = new Light();
		LightOnCommand lightOn = new LightOnCommand(light);
		Door door = new Door();
		DoorOpenCommand doorOpen = new DoorOpenCommand(door);
		
		System.out.println("-------测试 常规命令---------");
		remote.setCommand(lightOn);  //设置命令
		remote.buttonWasPressed();   //调用命令
		remote.setCommand(doorOpen); //设置命令
		remote.buttonWasPressed();   //调用命令
		System.out.println("----测试取消命令-------");
		remote.undo();
		
		System.out.println("----测试【 宏命令】-------");
		Command[] cmds = { lightOn, doorOpen };
		MacroCommand macroCommand = new MacroCommand(cmds);
		remote.setCommand(macroCommand);
		remote.buttonWasPressed();
		System.out.println("----测试 宏命令的取消-------");
		remote.undo();
	}
}



