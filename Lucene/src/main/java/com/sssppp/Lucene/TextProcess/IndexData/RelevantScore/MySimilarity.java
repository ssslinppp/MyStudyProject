package com.sssppp.Lucene.TextProcess.IndexData.RelevantScore;

import java.io.IOException;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.index.NumericDocValues;
import org.apache.lucene.search.CollectionStatistics;
import org.apache.lucene.search.TermStatistics;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.util.BytesRef;

/**
 * Similarity是用于计算score的底层的API
 * 【相关性score】 排名
 * Norm：是计算【相关性分数】的一部分；
 * 相关性得分越高，搜索结果越靠前；
 * 
 * 有两种方式提高搜索结果的相关性：
 * 1. 在索引时提高；==>通过设置boost属性的值来提高
 * 2. 在查询时提高；
 * 
 * 我们使用【列式存储】方式DocValues，来构建一个名为“ranking”的Field，搜索的结果排名根据ranking的值类进行
 * @author linliu
 * 
 */
public class MySimilarity extends Similarity {
	private Similarity sim = null;

	public MySimilarity(Similarity sim) {
		this.sim = sim;
	}

	@Override
	public long computeNorm(FieldInvertState state) {
		return sim.computeNorm(state);
	}

	@Override
	public Similarity.SimWeight computeWeight(float queryBoost,
			CollectionStatistics collectionStats, TermStatistics... termStats) {
		return sim.computeWeight(queryBoost, collectionStats, termStats);
	}

	@Override
	public Similarity.SimScorer simScorer(Similarity.SimWeight weight,
			AtomicReaderContext context) throws IOException {
		final Similarity.SimScorer scorer = sim.simScorer(weight, context);
		
		final NumericDocValues values = context.reader().getNumericDocValues(
				"ranking");
		return new SimScorer() {
			@Override
			public float score(int doc, float freq) {
				/*
				 * 这里用来计算score的值；
				 * 计算score的值时，我们使用了Field（ranking）中的值；
				 * 即：ranking中的值越大，score越大，排名越靠前
				 */
				return values.get(doc) * scorer.score(doc, freq);
			}

			@Override
			public float computePayloadFactor(int doc, int start, int end,
					BytesRef payload) {
				return scorer.computePayloadFactor(doc, start, end, payload);
			}

			@Override
			public float computeSlopFactor(int distance) {
				return scorer.computeSlopFactor(distance);
			}

		};
	}
}

















