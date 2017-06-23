package com.sssppp.Lucene.TextProcess.IndexAndSearchDemo;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneDelete {

	private LuceneDelete() { /* no instances */
	}

	public static void main(String[] args) throws CorruptIndexException,
			IOException {

		File indexDir = new File("C:\\luceneIndex3");
		String field = "author";
		String token = "JAY";

		System.out.println("index=" + indexDir.getName());
		System.out.println("field=" + field);
		System.out.println("token=" + token);

		Directory fsDir = FSDirectory.open(indexDir);
		Analyzer stdAn = new StandardAnalyzer(Version.LUCENE_47);
		IndexWriterConfig iwConf = new IndexWriterConfig(Version.LUCENE_47,stdAn);
		iwConf.setOpenMode(IndexWriterConfig.OpenMode.APPEND);
		IndexWriter indexWriter = new IndexWriter(fsDir, iwConf);
		int numDocsBefore = indexWriter.numDocs();

		//删除操作
		Term term = new Term(field, token);
		indexWriter.deleteDocuments(term);

		int numDocsAfterDeleteBeforeCommit = indexWriter.numDocs();
		indexWriter.commit();
		indexWriter.close();

		IndexWriterConfig iwConf2 = new IndexWriterConfig(Version.LUCENE_47,stdAn);
		iwConf2.setOpenMode(IndexWriterConfig.OpenMode.APPEND);
		IndexWriter indexWriter2 = new IndexWriter(fsDir, iwConf2);
		int numDocsAfter = indexWriter2.numDocs();
		indexWriter2.close();

		System.out.println("num docs before delete=" + numDocsBefore);
		System.out.println("num docs after delete before commit="
				+ numDocsAfterDeleteBeforeCommit);
		System.out.println("num docs after commit=" + numDocsAfter);
	}
}
















