package com.sssppp.Lucene.Demo1OfBasicUse;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

/**
 * This class is used to demonstrate the process of searching on an existing
 * Lucene index
 * 
 */
public class TxtFileSearcher {

	public static void main(String[] args) throws Exception {
		String queryStr = "ssslinppp";
		// 此目录存放Lucene的索引
		File file = new File("C:\\luceneIndex");
		FSDirectory directory = FSDirectory.open(file);
		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(indexReader);
		if (!file.exists()) {
			System.out.println("The Lucene index is not exist");
			return;
		}
		
		//在指定的Field中查询指定的token
		Term term = new Term("contents", queryStr.toLowerCase());
		TermQuery luceneQuery = new TermQuery(term);
		TopDocs topDocs = searcher.search(luceneQuery, 10);
		for (int i = 0; i < topDocs.scoreDocs.length; i++) { //遍历所有的Document
			Document document = searcher.doc(topDocs.scoreDocs[i].doc);
			for (IndexableField field : document.getFields()) {// 遍历所有的字段
				System.out.println(field.name() + ":"
						+ document.get(field.name()));
			}
		}
		
	}
}


























