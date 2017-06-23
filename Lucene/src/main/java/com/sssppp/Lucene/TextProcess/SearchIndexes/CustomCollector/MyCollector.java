package com.sssppp.Lucene.TextProcess.SearchIndexes.CustomCollector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Scorer;

public class MyCollector extends Collector {
	private int totalHits = 0;
	private int docBase;
	private Scorer scorer;
	private List<ScoreDoc> topDocs = new ArrayList();
	private ScoreDoc[] scoreDocs;

	public MyCollector() {
	}

	@Override
	public void setScorer(Scorer scorer) {
		this.scorer = scorer;
	}

	@Override
	public boolean acceptsDocsOutOfOrder() {
		return false;
	}

	/**
	 * This is called once per matched document. This is where we can score the
	 * document and even filter out unwanted documents
	 */
	@Override
	public void collect(int doc) throws IOException {
		float score = scorer.score();
		if (score > 0) {
			score += (1 / (doc + 1));
		}
		ScoreDoc scoreDoc = new ScoreDoc(doc + docBase, score);
		topDocs.add(scoreDoc);
		totalHits++;
	}

	/**
	 * This is called before the Collector begins to collect from an
	 * AtomicReader.
	 */
	@Override
	public void setNextReader(AtomicReaderContext context) {
		this.docBase = context.docBase;
	}

	public int getTotalHits() {
		return totalHits;
	}

	public ScoreDoc[] getScoreDocs() {
		if (scoreDocs != null) {
			return scoreDocs;
		}
		Collections.sort(topDocs, new Comparator<ScoreDoc>() {
			public int compare(ScoreDoc d1, ScoreDoc d2) {
				if (d1.score > d2.score) {
					return -1;
				} else if (d1.score == d2.score) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		scoreDocs = topDocs.toArray(new ScoreDoc[topDocs.size()]);
		return scoreDocs;
	}
}