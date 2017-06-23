package CommandDP.ICommand;

/**
 * 为所有命令声明一个接口； 
 * 将“请求”封装成对象（命令对象），以便使用不同的请求、队列或者日志来参数化其他对象。
 */
public interface Command {
	public void execute();

	/**
	 *  支持撤销操作
	 *  需要提供和execute()相反的操作
	 */
	public void undo();
}
