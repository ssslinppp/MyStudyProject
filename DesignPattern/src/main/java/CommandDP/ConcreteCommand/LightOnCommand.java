package CommandDP.ConcreteCommand;

import CommandDP.ICommand.Command;
import CommandDP.Receiver.Light;

/**
 * 实现打开电灯的命令
 * 命令封装了请求的接收者
 * 1. 定义了动作和接收者之间的绑定关系；
 */
public class LightOnCommand implements Command {
	Light light;
	
	public LightOnCommand(Light light) {
		super();
		this.light = light;
	}

	@Override
	public void execute() {
		light.on();  //调用接收者的动作
	}

	@Override
	public void undo() {
		light.off();   //需要执行和execute()相反的操作	
	}

}
