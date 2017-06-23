package com.sssppp.Lucene.TextProcess.SearchIndexes.Sort.two;

import java.io.IOException;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.BinaryDocValues;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.search.FieldComparator;
import org.apache.lucene.util.BytesRef;

/**
 * 没有细研究--不是很清楚
 * @author linliu
 *
 */
public class MyFieldComparator extends FieldComparator<String> {
	private String field;
	private String bottom;
	private String topValue;
	private BinaryDocValues cache;
	private String[] values;

	public MyFieldComparator(String field, int numHits) {
		this.field = field;
		this.values = new String[numHits];
	}

	public int compare(int slot1, int slot2) {
		return compareValues(values[slot1], values[slot2]);
	}

	public int compareBottom(int doc) {
		BytesRef result = new BytesRef();
		cache.get(doc, result);
		return compareValues(bottom, result.utf8ToString());
	}

	public int compareTop(int doc) {
		BytesRef result = new BytesRef();
		cache.get(doc, result);
		return compareValues(topValue, result.utf8ToString());
	}

	public int compareValues(String first, String second) {
		int val = first.length() - second.length();
		return val == 0 ? first.compareTo(second) : val;
	}

	public void copy(int slot, int doc) {
		BytesRef result = new BytesRef();
		cache.get(doc, result);
		values[slot] = result.utf8ToString();
	}

	public void setBottom(int slot) {
		this.bottom = values[slot];
	}

	public void setTopValue(String value) {
		this.topValue = value;
	}

	public String value(int slot) {
		return values[slot];
	}

	public FieldComparator<String> setNextReader(AtomicReaderContext context)
			throws IOException {
		this.cache = FieldCache.DEFAULT.getTerms(context.reader(), field, true);
		return this;
	}
}
