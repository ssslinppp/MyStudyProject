package com.sssppp.Lucene.TextProcess.IndexData.RelevantScore;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 * 【相关性score】 排名
 * Norm：是计算【相关性分数】的一部分；
 * 相关性得分越高，搜索结果越靠前；
 * 
 * 有两种方式提高搜索结果的相关性：
 * 1. 在索引时提高；==>通过设置boost属性的值来提高（本示例程序使用的方式）
 * 2. 在查询时提高；
 * 
 * @author linliu
 * 
 */
public class BoostRelevantScore {
	static Version version = Version.LUCENE_47;

	public static void main(String a[]) throws IOException {
		testBoost();
	}

	public static void testBoost() throws IOException {
		Analyzer analyzer = new StandardAnalyzer(version);
		Directory directory = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(version, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, config);
		
		Document doc = new Document();
		TextField textField = new TextField("name", "", Store.YES);
		float boost = 1f;
		String[] names = { "John R Smith", "Mary Smith", "Peter Smith" };
		for (String name : names) {
			boost *= 1.1;
			textField.setStringValue(name);
			
			//注释掉下面的语句，来对比输出结果
			textField.setBoost(boost); //该值设置的越大，越能提高相关性得分
			
			doc.removeField("name"); //对Document和Field的重复使用
			doc.add(textField);
			indexWriter.addDocument(doc);
		}
		indexWriter.commit();
		
		//查询
		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		Query query = new TermQuery(new Term("name", "smith"));
		TopDocs topDocs = indexSearcher.search(query, 100);
		System.out.println("=======> Searching 'smith'");
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			doc = indexReader.document(scoreDoc.doc);
			System.out.println(doc.getField("name").stringValue());
		}
	}
}
























