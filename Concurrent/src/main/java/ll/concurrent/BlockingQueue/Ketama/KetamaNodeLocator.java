package ll.concurrent.BlockingQueue.Ketama;

import java.util.Collection;
import java.util.TreeMap;

/**
 * 一致性Hash算法：是一种分布式算法，常用于负载均衡;
 *
 * @param <T>
 */
public final class KetamaNodeLocator<T> {
	private TreeMap<Long, T> ketamaNodes;
	private HashAlgorithm hashAlg;
	private int numReps = 160;

	public KetamaNodeLocator(Collection<T> nodes) {
		this(nodes, HashAlgorithm.KETAMA_HASH);
	}

	public KetamaNodeLocator(Collection<T> nodes, HashAlgorithm alg) {
		this(nodes, HashAlgorithm.KETAMA_HASH, 160);
	}

	public KetamaNodeLocator(Collection<T> nodes, HashAlgorithm alg,
			int nodeCopies) {
		hashAlg = alg;
		ketamaNodes = new TreeMap<Long, T>();
		numReps = nodeCopies;
		for (T node : nodes) {
			for (int i = 0; i < numReps / 4; i++) {
				byte[] digest = hashAlg.computeMd5(node.toString() + i);
				for (int h = 0; h < 4; h++) {
					long m = hashAlg.hash(digest, h);
					ketamaNodes.put(m, node);
				}
			}
		}
	}

	public T getNode(final String k) {
		byte[] digest = hashAlg.computeMd5(k);
		T rv = getNodeForKey(hashAlg.hash(digest, 0));
		return rv;
	}

	T getNodeForKey(long hash) {
		if (ketamaNodes.isEmpty()) {
			return null;
		}
		if (!ketamaNodes.containsKey(hash)) {
			Object ceilValue = ((TreeMap<Long, T>) ketamaNodes)
					.ceilingKey(hash);
			if (ceilValue != null) {
				try {
					hash = Long.valueOf(ceilValue.toString());
				} catch (NumberFormatException e) {
					e.printStackTrace();
					hash = 0;
				}
			}
			if (ceilValue == null || hash == 0) {
				hash = ketamaNodes.firstKey();
			}
		}
		return ketamaNodes.get(hash);
	}

}
