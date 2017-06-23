package com.sssppp.Lucene.Demo2;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
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
		String query = "mkentry"; 
		int maxHits = 20;

		File indexDir = new File("C:\\luceneIndex2");
		Directory fsDir = FSDirectory.open(indexDir);
		DirectoryReader reader = DirectoryReader.open(fsDir);
		IndexSearcher searcher = new IndexSearcher(reader);

		// QueryParser使用的Analyzer，应该和创建Index的Analyzer保持一致
//		Analyzer stdAn = new StandardAnalyzer(Version.LUCENE_47);
		Analyzer stdAn = new MyAnalyzer();
		QueryParser parser = new QueryParser(Version.LUCENE_47, "text", stdAn);
		Query q = parser.parse(query);

		TopDocs hits = searcher.search(q, maxHits);
		ScoreDoc[] scoreDocs = hits.scoreDocs;
		System.out.println("hits=" + scoreDocs.length);
		System.out.println("Hits (rank, score, docId)");
		for (int n = 0; n < scoreDocs.length; ++n) {
			ScoreDoc sd = scoreDocs[n];
			float score = sd.score; // score越高，代表匹配度越高
			int docId = sd.doc; //这是Lucene对doc的内部编号，每次创建索引时都可能会有变化
			System.out.printf("%3d %4.2f %d\n", n, score, docId);

			Document document = searcher.doc(docId);
			for (IndexableField field : document.getFields()) {
				System.out.println(field.name() + "  :"
						+ document.get(field.name()));
			}
		}
		reader.close();
	}
}













