package com.sssppp.Lucene.TextProcess.IndexAndSearchDemo;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneSearch {

	public static void main(String[] args) throws ParseException,
			CorruptIndexException, IOException {

		File indexDir = new File("C:\\luceneIndex3");
		String query = "Powers of the Judiciary";
//		String query = "我爱北京天安门";
//		String query = "ASSUMING it therefore as an established truth that the several ";
//		String query = "title:Constitution";
		int maxHits = 10;

		System.out.println("index=" + indexDir.getName());
		System.out.println("query=" + query);
		System.out.println("max hits=" + maxHits);

		Directory fsDir = FSDirectory.open(indexDir);
		DirectoryReader reader = DirectoryReader.open(fsDir);
		IndexSearcher searcher = new IndexSearcher(reader);
		
		//应该和建立index使用的Analyzer保持相同
		Analyzer stdAn = new StandardAnalyzer(Version.LUCENE_47);
		QueryParser parser = new QueryParser(Version.LUCENE_47, "text", stdAn);
		Query q = parser.parse(query);
		System.out.println("-------Query Info start-----");
		System.out.println("parsed query=" + q.toString());
		System.out.println("q is BooleanQuery :"+((q instanceof BooleanQuery)?"True":"false"));
		if (q instanceof BooleanQuery) {
			//遍历每个BooleanQuery的BooleanClause，然后输出信息
			for (BooleanClause booleanClause : ((BooleanQuery) q).getClauses()) {
				System.out.println(booleanClause.getQuery());
			}
		}
		System.out.println("QueryParser DefaultOp is :"+ parser.getDefaultOperator().toString());
		System.out.println("-------Query Info end-----");
		
		TopDocs hits = searcher.search(q, maxHits);
		ScoreDoc[] scoreDocs = hits.scoreDocs;
		System.out.println("hits=" + scoreDocs.length);
		System.out.println("Hits (rank,score,paper)");
		for (int n = 0; n < scoreDocs.length; ++n) {
			ScoreDoc sd = scoreDocs[n];
			float score = sd.score;
			int docId = sd.doc;
			Document d = searcher.doc(docId);
			String number = d.get("number");
			System.out.printf("%3d %4.2f  %s\n", n, score, number);
//			System.out.println("Title: "+d.get("title"));
			for (String t : d.getValues("text")) {
//				System.out.println("Text: " + t);
			}
		}
		reader.close();
	}
}
/**
 * index=luceneIndex3
 * query=Powers of the Judiciary
 * max hits=10
 * q is BooleanQuery :True
 * QueryParser DefaultOp is :OR
 * parsed query=text:powers text:judiciary
 * hits=10
 * Hits (rank,score,paper)
 *  0 0.29  47
 *  1 0.23  48
 *  2 0.19  78
 *  3 0.17  80
 *  4 0.14  44
 *  5 0.14  45
 *  6 0.14  49
 *  7 0.14  71
 *  8 0.11  66
 *  9 0.11  38
 */

















