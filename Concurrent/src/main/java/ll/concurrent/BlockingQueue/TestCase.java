package ll.concurrent.BlockingQueue;

import java.text.SimpleDateFormat;

public class TestCase {
	public static void main(String[] args) throws InterruptedException {
		MasterThread masterThread = MasterThread.getInstance();

		System.out.println("Start time:"
				+ new SimpleDateFormat("HH:mm:ss").format(System
						.currentTimeMillis()));
		/**
		 * 每个MetaData都需要0.5S的处理时间，如果串行执行，则需要500*0.5=250; 
		 * 现采用并行处理，只需要很短的时间即可执行完；
		 */
		for (int i = 0; i < 500; i++) {
			MetaData metaData = new MetaData(i + "");
			masterThread.put(metaData);
		}
	}

}
