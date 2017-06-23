package com.sssppp.Lucene.TextProcess.SearchIndexes;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.BinaryDocValues;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.SlowCompositeReaderWrapper;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

public class UseFieldCache {
	static Version version = Version.LUCENE_47;
	
	public static void main(String arg[]) throws Exception {
		testFieldCache();
	}

	public static void testFieldCache() throws IOException {
		StandardAnalyzer analyzer = new StandardAnalyzer(version);
		Directory directory = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(version, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, config);

		Document doc = new Document();
		StringField stringField = new StringField("name", "", Store.YES);
		String[] contents = { "alpha", "bravo", "charlie", "delta", "echo",
				"foxtrot" };
		for (String content : contents) {
			stringField.setStringValue(content);
			doc.removeField("name");//重复利用Document和Field
			doc.add(stringField); 
			indexWriter.addDocument(doc);
		}
		indexWriter.commit();

		IndexReader indexReader = DirectoryReader.open(directory);
		//SlowCompositeReaderWrapper：用于将CompositeReader转换为AtomicReader
		AtomicReader atomicReader = SlowCompositeReaderWrapper
				.wrap(indexReader);
		//DocValue是一个列式存储结构,FieldCache在memory中存储数据
		BinaryDocValues cache = FieldCache.DEFAULT.getTerms(atomicReader,
				"name", true);
		BytesRef result = new BytesRef();
		for (int docID = 0; docID < indexReader.maxDoc(); docID++) {
			cache.get(docID, result);
			System.out.println(docID + ": " + result.utf8ToString());
		}
	}

}


























