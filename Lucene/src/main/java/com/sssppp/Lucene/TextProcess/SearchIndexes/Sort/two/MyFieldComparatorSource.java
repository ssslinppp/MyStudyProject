package com.sssppp.Lucene.TextProcess.SearchIndexes.Sort.two;

import org.apache.lucene.search.FieldComparator;
import org.apache.lucene.search.FieldComparatorSource;

public class MyFieldComparatorSource extends FieldComparatorSource {
	public FieldComparator newComparator(String fieldname, int numHits,
			int sortPos, boolean reversed) {
		return new MyFieldComparator(fieldname, numHits);
	}
}