package CommandDP.ConcreteCommand;

import CommandDP.ICommand.Command;

/**
 * 宏命令--> 一次可以执行多个命令
 *
 */
public class MacroCommand implements Command {

	Command[] commands;
	
	public MacroCommand(Command[] commands) {
		super();
		this.commands = commands;
	}

	@Override
	public void execute() {
		for (int i = 0; i < commands.length; i++) {
			commands[i].execute();
		}
	}

	@Override
	public void undo() {
		for (int i = commands.length - 1; i >= 0 ; i--) {
			commands[i].undo();
		}
	}

}
