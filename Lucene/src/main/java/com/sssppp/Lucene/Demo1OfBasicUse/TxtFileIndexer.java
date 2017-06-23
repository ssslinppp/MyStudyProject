package com.sssppp.Lucene.Demo1OfBasicUse;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * 为文本文件建立索引
 */
public class TxtFileIndexer {
	public static void main(String[] args) throws Exception {
		//Lucene索引存放的目录
		FSDirectory indexDir = FSDirectory.open(new File("C:\\luceneIndex"));
		Analyzer luceneAnalyzer = new StandardAnalyzer(Version.LUCENE_47);
		IndexWriterConfig iWConf = new IndexWriterConfig(Version.LUCENE_47, luceneAnalyzer);
		iWConf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		IndexWriter indexWriter = new IndexWriter(indexDir, iWConf);
		
		long startTime = new Date().getTime();
		
		//需要被索引的文件存放的位置
		File dataDir = new File("C:\\luceneData");
		File[] dataFiles = dataDir.listFiles();
		//遍历所有的txt文件，每个文件对应一个document，为每个文件都建立索引
		for (int i = 0; i < dataFiles.length; i++) {
			if (dataFiles[i].isFile() && dataFiles[i].getName().endsWith(".txt")) {
				System.out.println("Indexing file "	+ dataFiles[i].getCanonicalPath());
				Document document = new Document(); //相当于数据表中的记录
				Reader txtReader = new FileReader(dataFiles[i]);
				// Field：相当于记录的字段
				document.add(new StringField("path", dataFiles[i].getCanonicalPath(), Store.YES));
				document.add(new TextField("contents", txtReader));//整个文件的内容
				document.add(new StringField("Name", "article" + i, Store.YES));
				indexWriter.addDocument(document); //负责添加document到Index
			}
		}
		indexWriter.commit();
		indexWriter.close();
		long endTime = new Date().getTime();

		System.out.println("It takes " + (endTime - startTime)
				+ " milliseconds to create index for the files in directory "
				+ dataDir.getPath());
	}
}
























