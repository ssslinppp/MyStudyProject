package com.sssppp.Lucene.TextProcess.SearchIndexes.Pagination;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 * 搜索结果分页
 * 
 * @author linliu
 * 
 */
public class PaginationSearchResult {
	static Version version = Version.LUCENE_47;

	public static void main(String arg[]) throws IOException, ParseException {
		testPagination();
	}

	public static void testPagination() throws IOException, ParseException {
		//创建IndexWriter
		StandardAnalyzer analyzer = new StandardAnalyzer(version);
		Directory directory = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(version, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, config);
	
		//创建Index
		Document doc = new Document();
		TextField textField = new TextField("content", "", Field.Store.YES);
		String[] contents = { 
				"Humpty Dumpty sat on a wall,",
				"Humpty Dumpty had a great fall.",
				"All the king's horses and all the king's men",
				"Couldn't put Humpty together again." };
		for (String content : contents) {
			textField.setStringValue(content);
			doc.removeField("content");
			doc.add(textField);
			indexWriter.addDocument(doc);
		}
		indexWriter.commit();
		
		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		
		QueryParser queryParser = new QueryParser(version, "content", analyzer);
		Query query = queryParser.parse("humpty dumpty");
		System.out.println("query :"+query.toString());
		System.out.println("------------------------------");
		//分页，这里设置每页只有两个Document
		TopDocs topDocs = indexSearcher.search(query, 2); 
		System.out.println("Total hits: " + topDocs.totalHits);
		ScoreDoc lastScoreDoc = null;
		int page = 1;
		while (true) {
			System.out.println("==>【Page】-" + page);
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				doc = indexReader.document(scoreDoc.doc);
				System.out.println(scoreDoc.score + ": "
						+ doc.getField("content").stringValue());
				lastScoreDoc = scoreDoc;
			}
			//后续分页查询
			topDocs = indexSearcher.searchAfter(lastScoreDoc, query, 2);
			if (topDocs.scoreDocs.length == 0) {
				break;
			}
			page++;
		}
	}
}























