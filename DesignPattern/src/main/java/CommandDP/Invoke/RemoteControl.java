package CommandDP.Invoke;

import CommandDP.ConcreteCommand.noCommand;
import CommandDP.ICommand.Command;

/**
 * 遥控器--调用者角色
 * 调用者：只和【命令对象】打交道
 * 1. 持有命令对象的实例；
 * 2. 设置命令对象的方法；
 * 3. 调用命令对象的execute()
 *
 */
public class RemoteControl {
	Command slot;            //命令对象
	Command undoCommand;     //取消命令
	
	public RemoteControl(){ 
		//都初始化为noCommand：不执行任何请求操作
		Command noCommand = new noCommand();
		slot = noCommand; 
		undoCommand = noCommand; 
	}
	
	/**
	 * 设置命令对象
	 * @param slot
	 */
	public void setCommand(Command slot) {
		this.slot = slot;
	}
	
	/**
	 * 调用命令对象的execute()方法
	 */
	public void buttonWasPressed(){
		slot.execute();
		undoCommand = slot;  //记录上一次的Command，以便实现“取消操作”
	}
	
	public void undo(){
		undoCommand.undo();
	}

}
