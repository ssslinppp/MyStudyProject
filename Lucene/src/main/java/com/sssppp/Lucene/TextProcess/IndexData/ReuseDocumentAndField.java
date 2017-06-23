package com.sssppp.Lucene.TextProcess.IndexData;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 * 为了拥有更高的性能，可以重复利用Document和Field
 * 
 * Document的重复利用，需要确保：
 * 1. Document中的所有域都已经删除后，再添加新的域；
 * 
 * @author linliu
 *
 */
public class ReuseDocumentAndField {
	static Version version = Version.LUCENE_47;

	public static void main(String a[]) throws IOException {
		reuseDocAndField();
	}

	public static void reuseDocAndField() throws IOException {
		Analyzer analyzer = new StandardAnalyzer(version);
		Directory directory = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(version, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, config);
		Document doc = new Document();
		StringField stringField = new StringField("name", "", Store.YES);
		String[] names = { "John", "Mary", "Peter" };
		for (String name : names) {
			stringField.setStringValue(name);
			doc.removeField("name"); // 先删除指定域
			doc.add(stringField); // 再重新添加指定域
			indexWriter.addDocument(doc);
		}
		indexWriter.commit();

		IndexReader reader = DirectoryReader.open(directory);
		for (int i = 0; i < 3; i++) {
			doc = reader.document(i);
			System.out.println("DocId: " + i + ", name: "
					+ doc.getField("name").stringValue());
		}
	}
}
