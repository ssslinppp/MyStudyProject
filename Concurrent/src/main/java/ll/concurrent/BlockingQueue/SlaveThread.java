package ll.concurrent.BlockingQueue;

import java.text.SimpleDateFormat;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SlaveThread implements Runnable {
	private BlockingQueue<MetaData> msgQueue = new LinkedBlockingQueue<MetaData>(
			200);

	private int slaveThreadID;

	public SlaveThread() {
	}

	public SlaveThread(int slaveThreadID) {
		super();
		this.slaveThreadID = slaveThreadID;
	}

	public void run() {
		while (true) {
			MetaData metaData = null;
			try {
				// 堵塞获取
				metaData = msgQueue.take();
				handleMetaData(metaData);
			} catch (InterruptedException e) {
				System.err.println("SlaveThread[" + this.slaveThreadID + "] is Interrupted...");
				break;
			} catch (Exception e) {
				System.err.println("failed to handle meta data" + e);
			}
		}
		System.err.println("SlaveThread[" + this.slaveThreadID + "] exit...");
	}

	public void put(MetaData object) {
		if (object == null)
			return;
		if (!msgQueue.offer(object)) {
			System.err.println("SlaveThread BlockingQueue up to max size");
		}
	}

	private void handleMetaData(MetaData metaData) throws Exception {
		// 模拟处理，耗时500毫秒
		Thread.sleep(500);
		System.out.println("SlaveThread["+ this.slaveThreadID + "] "
				+ metaData.toString()
				+ new SimpleDateFormat("HH:mm:ss").format(System
						.currentTimeMillis()));
	}

}
