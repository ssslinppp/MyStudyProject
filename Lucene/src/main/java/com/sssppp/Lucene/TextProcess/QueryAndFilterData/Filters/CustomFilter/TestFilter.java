package com.sssppp.Lucene.TextProcess.QueryAndFilterData.Filters.CustomFilter;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 * 10 – admin
 * 20 – manager
 * 30 – user
 * 40 – guest
 * @author linliu
 *
 */
public class TestFilter {
	static Version version = Version.LUCENE_47;
	
	public static void main(String arg[]) throws IOException {
		Directory directory = new RAMDirectory();
		addIndex(directory);
		testFilter(directory);
	}
	
	public static void testFilter(Directory directory) throws IOException {
		UserSecurityFilter userSecurityFilter = new UserSecurityFilter(
				"userId", "groupId", "1001", "40");
		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		
		Query query = new MatchAllDocsQuery();
		TopDocs topDocs = indexSearcher.search(query, userSecurityFilter, 100);
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			Document doc = indexReader.document(scoreDoc.doc);
			System.out.println("file: " + doc.getField("file").stringValue()
					+ " - userId: " + doc.getField("userId").stringValue()
					+ " - groupId: " + doc.getField("groupId").stringValue());
		}
		indexReader.close();
	}

	public static void addIndex(Directory directory) throws IOException {
		Analyzer analyzer = new StandardAnalyzer(version);
		IndexWriterConfig config = new IndexWriterConfig(version, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, config);

		Document doc = new Document();
		StringField stringFieldFile = new StringField("file", "",
				Field.Store.YES);
		StringField stringFieldUserId = new StringField("userId", "",
				Field.Store.YES);
		StringField stringFieldGroupId = new StringField("groupId", "",
				Field.Store.YES);
		// Document-1
		doc.removeField("file");
		doc.removeField("userId");
		doc.removeField("groupId");
		stringFieldFile.setStringValue("Z:\\shared\\finance\\2014-sales.xls");
		stringFieldUserId.setStringValue("1001");
		stringFieldGroupId.setStringValue("20");
		doc.add(stringFieldFile);
		doc.add(stringFieldUserId);
		doc.add(stringFieldGroupId);
		indexWriter.addDocument(doc);

		// Document-2
		doc.removeField("file");
		doc.removeField("userId");
		doc.removeField("groupId");
		stringFieldFile.setStringValue("Z:\\shared\\company\\2014-policy.doc");
		stringFieldUserId.setStringValue("1101");
		stringFieldGroupId.setStringValue("30");
		doc.add(stringFieldFile);
		doc.add(stringFieldUserId);
		doc.add(stringFieldGroupId);
		indexWriter.addDocument(doc);

		// Document-3
		doc.removeField("file");
		doc.removeField("userId");
		doc.removeField("groupId");
		stringFieldFile
				.setStringValue("Z:\\shared\\company\\2014-terms-and-conditions.doc");
		stringFieldUserId.setStringValue("1205");
		stringFieldGroupId.setStringValue("40");
		doc.add(stringFieldFile);
		doc.add(stringFieldUserId);
		doc.add(stringFieldGroupId);
		indexWriter.addDocument(doc);

		indexWriter.commit();
		indexWriter.close();
	}
}
