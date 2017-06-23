package ll.concurrent.BlockingQueue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import ll.concurrent.BlockingQueue.Ketama.KetamaNodeLocator;

/**
 * <pre>
 * MasterThread：
 *	1. 持有一个BlockingQueue队列,用于并发接收存储MetaData对象;
 *	2. 使用Hash一致性算法ketama来选择SlaveThread节点;
 *	3. 从BlockingQueue队列中，取出MetaData对象，分配给SlaveThread节点；
 *	4. SlaveThread节点负责真正处理MetaData对象；
 *     
 * SlaveThread：
 *	1. 持有一个BlockingQueue队列,用于存储MetaData对象;
 *	2. 负责真正处理MetaData对象;
 * </pre>
 */
public class MasterThread {
	private static int SLAVE_ENGINE_NUMBER_MAX = 100;
	private static int _BLOCKSIZE = 5000;
	private static MasterThread masterThread;

	private BlockingQueue<MetaData> metaDataQueue;
	private SlaveEngineThread slaveEngineThread;

	public static synchronized MasterThread getInstance() {
		if (masterThread == null) {
			masterThread = new MasterThread();
		}
		return masterThread;
	}

	private MasterThread() {
		metaDataQueue = new LinkedBlockingQueue<MetaData>(_BLOCKSIZE);
		startSlaveThreadEngine();
	}
	
	private void startSlaveThreadEngine() {
		slaveEngineThread = new SlaveEngineThread(SLAVE_ENGINE_NUMBER_MAX);
		slaveEngineThread.start();
	}

	public synchronized void put(MetaData object) {
		if (object == null)
			return;

		if (!metaDataQueue.offer(object)) {
			System.err.println("BlockingQueue is up to max size:"
					+ metaDataQueue.size());
		}
	}

	private class SlaveEngineThread extends Thread {
		private ExecutorService executorService;
		private Map<Integer, SlaveThread> slaveThreadMap;

		//本示例采用一致性Hash算法，选择SlaveThread
		private KetamaNodeLocator<Integer> nodeLocator;

		public SlaveEngineThread(final int nThreads) {
			slaveThreadMap = new HashMap<Integer, SlaveThread>();

			// 创建线程池，并发执行SlaveThread
			executorService = Executors.newFixedThreadPool(nThreads);
			for (int i = 0; i < nThreads; i++) {
				SlaveThread command = new SlaveThread(i);
				executorService.execute(command);
				slaveThreadMap.put(i, command);
			}
			nodeLocator = new KetamaNodeLocator<Integer>(
					slaveThreadMap.keySet());
		}

		@Override
		public void run() {
			while (true) {
				MetaData metaData = null;
				try {
					// 堵塞获取
					metaData = metaDataQueue.take();

					// 通过hash算法获取slaveThread编号
					Integer nodeNumber = nodeLocator.getNode(metaData.getId());
					SlaveThread slaveThread = slaveThreadMap.get(nodeNumber);
					if (slaveThread != null) {
						// 将MetaData存入SlaveThread处理
						slaveThread.put(metaData);
					}
				} catch (InterruptedException e) {
					executorService.shutdown();
					System.err.println("SlaveEngineThread is Interrupted ... ");
					break;
				} catch (Exception e) {
					System.err.println("failed to handle meta data" + e);
				}
			}
			System.err.println("Master Thread exit...");
		}
	}

	public void exit() {
		stopSlaveEngineThread();
	}

	private void stopSlaveEngineThread() {
		slaveEngineThread.interrupt();
	}
}
