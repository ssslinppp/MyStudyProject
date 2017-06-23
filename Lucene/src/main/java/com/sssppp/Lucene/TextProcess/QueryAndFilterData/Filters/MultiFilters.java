package com.sssppp.Lucene.TextProcess.QueryAndFilterData.Filters;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.FieldCacheRangeFilter;
import org.apache.lucene.search.FieldCacheTermsFilter;
import org.apache.lucene.search.FieldValueFilter;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeFilter;
import org.apache.lucene.search.PrefixFilter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeFilter;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 * 
 * @author linliu
 *
 */
public class MultiFilters {
	static Version version = Version.LUCENE_47;

	public static void main(String arg[]) throws IOException {
		System.out.println("====Filter[null]--------------------");
		testFilter(null);
		System.out.println("\n====TermRangeFilter-->name[A,G]---------");
		// 若name的首字母不是[A,G]范围内，则过滤掉
		TermRangeFilter termRangeFilter = TermRangeFilter.newStringRange(
				"name", "A", "G", true, true);
		testFilter(termRangeFilter);

		System.out.println("\n====NumericRangeFilter-->num[200,400]---------");
		// 若num的不是[200,400]范围内，则过滤掉
		NumericRangeFilter<?> numericRangeFilter = NumericRangeFilter
				.newIntRange("num", 200, 400, true, true);
		testFilter(numericRangeFilter);

		System.out.println("\n====fieldCacheTermRangeFilter-->name[A,G]---");
		FieldCacheRangeFilter<?> fieldCacheTermRangeFilter = FieldCacheRangeFilter
				.newStringRange("name", "A", "G", true, true);
		testFilter(fieldCacheTermRangeFilter);

		// 对查询结果进行再一次的查询过滤，过滤content中包含together的Document
		System.out.println("\n====QueryWrapperFilter-->Term(content,together)");
		QueryWrapperFilter queryWrapperFilter = new QueryWrapperFilter(
				new TermQuery(new Term("content", "together")));
		testFilter(queryWrapperFilter);

		// 过滤“name=F*”的Document
		System.out.println("\n====PrefixFilter-->Term(name, F)---------");
		PrefixFilter prefixFilter = new PrefixFilter(new Term("name", "F"));
		testFilter(prefixFilter);

		//如果指定Field的值，包含在FieldCacheTermsFilter给出的string set中，则匹配
		System.out.println("\n====FieldCacheTermsFilter-->{ First,Fourth}-");
		FieldCacheTermsFilter fieldCacheTermsFilter = new FieldCacheTermsFilter(
				"name", new String[] { "First", "Fourth" });
		testFilter(fieldCacheTermsFilter);

		//只要在指定的Field中有值（即存在该FieldName），则匹配
		System.out.println("\n====FieldValueFilter-->{ FieldName1}-");
		FieldValueFilter fieldValueFilter = new FieldValueFilter("FieldName1");
		testFilter(fieldValueFilter);

		
		System.out.println("\n====CachingWrapperFilter(TermRangeFilter)-");
		TermRangeFilter termRangeFilter2 = TermRangeFilter.newStringRange(
				"name", "A", "G", true, true);
		CachingWrapperFilter cachingWrapperFilter = new CachingWrapperFilter(
				termRangeFilter2);
		testFilter(cachingWrapperFilter);

	}

	/**
	 * @param filter
	 * @throws IOException
	 */
	public static void testFilter(Filter filter) throws IOException {
		Analyzer analyzer = new StandardAnalyzer(version);
		Directory directory = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(version, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, config);

		Document doc = new Document();
		StringField stringField = new StringField("name", "", Field.Store.YES);
		TextField textField = new TextField("content", "", Field.Store.YES);
		IntField intField = new IntField("num", 0, Field.Store.YES);

		doc.removeField("name");
		doc.removeField("content");
		doc.removeField("num");
		stringField.setStringValue("First");
		textField.setStringValue("Humpty Dumpty sat on a wall,");
		intField.setIntValue(100);
		doc.add(stringField);
		doc.add(textField);
		doc.add(intField);
		indexWriter.addDocument(doc);

		doc.removeField("name");
		doc.removeField("content");
		doc.removeField("num");
		stringField.setStringValue("Second");
		textField.setStringValue("Humpty Dumpty had a great fall.");
		intField.setIntValue(200);
		doc.add(stringField);
		doc.add(textField);
		doc.add(intField);
		indexWriter.addDocument(doc);

		doc.removeField("name");
		doc.removeField("content");
		doc.removeField("num");
		stringField.setStringValue("Third");
		textField
				.setStringValue("All the king's horses and all the king's men");
		intField.setIntValue(300);
		doc.add(stringField);
		doc.add(textField);
		doc.add(intField);
		indexWriter.addDocument(doc);

		doc.removeField("name");
		doc.removeField("content");
		doc.removeField("num");
		stringField.setStringValue("Fourth");
		textField.setStringValue("Couldn't put Humpty together again.");
		intField.setIntValue(400);
		doc.add(stringField);
		doc.add(textField);
		doc.add(intField);
		indexWriter.addDocument(doc);

		indexWriter.commit();
		indexWriter.close();

		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		Query query = new TermQuery(new Term("content", "humpty"));
		TopDocs topDocs = indexSearcher.search(query, filter, 100);
		System.out.println("Searching 'humpty'");
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			doc = indexReader.document(scoreDoc.doc);
			System.out.println("name: " + doc.getField("name").stringValue()
					+ " - content: " + doc.getField("content").stringValue()
					+ " - num: " + doc.getField("num").stringValue());
		}
		indexReader.close();
	}
}
