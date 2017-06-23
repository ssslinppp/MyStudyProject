package com.sssppp.Lucene.Lucene4CookBook;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class LuceneTest {
	public static void main(String[] args) throws IOException {

		final Version version = Version.LUCENE_47;
		Analyzer analyzer = new WhitespaceAnalyzer(version);

		Directory directory = new RAMDirectory();

		IndexWriterConfig config = new IndexWriterConfig(version, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, config);
		Document doc = new Document();
		String text = "Lucene is an Information Retrieval library written in Java";
		doc.add(new TextField("fieldname", text, Store.YES));

		indexWriter.addDocument(doc);
		indexWriter.close();
	}
}














