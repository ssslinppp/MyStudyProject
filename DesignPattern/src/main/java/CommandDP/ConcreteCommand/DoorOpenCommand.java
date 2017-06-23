package CommandDP.ConcreteCommand;

import CommandDP.ICommand.Command;
import CommandDP.Receiver.Door;

/**
 * 命令封装了请求的接收者
 *
 */
public class DoorOpenCommand implements Command {
	Door door;
	
	public DoorOpenCommand(Door door) {
		super();
		this.door = door;
	}

	@Override
	public void execute() {
		door.open();  //调用接收者的动作
	}

	@Override
	public void undo() {
		door.close();	//需要执行和execute()相反的操作	
	}

}
