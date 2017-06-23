package com.sssppp.Lucene.Demo2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * 参考： https://lingpipe-blog.com/2014/03/08/lucene-4-essentials-for-text-search-
 * and-indexing/ 
 * 索引的所有文档来源于：http://qwone.com/~jason/20Newsgroups/
 * 
 * @author linliu
 */
public class BuildIndexer {
	public static void main(String a[]) {
		File indexDir = new File("C:\\luceneIndex2");
		File dataDir = new File("C:\\luceneData2");
		try {
			buildIndex(indexDir, dataDir);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void buildIndex(File indexDir, File dataDir)
			throws IOException, FileNotFoundException {
		// Index索引目录
		Directory fsDir = FSDirectory.open(indexDir);
		
		Analyzer mAnalyzer = new MyAnalyzer();
//		Analyzer mAnalyzer = new StandardAnalyzer(Version.LUCENE_47);

		IndexWriterConfig iwConf = new IndexWriterConfig(Version.LUCENE_47,mAnalyzer);
		iwConf.setOpenMode(IndexWriterConfig.OpenMode.CREATE); // 每次都会删除之前的
		IndexWriter indexWriter = new IndexWriter(fsDir, iwConf);

		long startTime = new Date().getTime();
		File[] groupsDir = dataDir.listFiles();
		for (File group : groupsDir) { // 第1级目录
			String groupName = group.getName();
			System.out.println("Indexing group ：" + groupName);
			File[] posts = group.listFiles();
			for (File postFile : posts) {// 将每个post都当做一个Document
				String number = postFile.getName();
				NewsPost post = parse(postFile, groupName, number);
				Document d = new Document();
				d.add(new StringField("category", post.getGroup(), Store.YES));
				d.add(new TextField("text", post.getSubject(), Store.NO));
				d.add(new TextField("text", post.getBody()));
				indexWriter.addDocument(d);
			}

		}
		indexWriter.commit();
		indexWriter.close();

		long endTime = new Date().getTime();
		System.out.println("It takes " + (endTime - startTime)
				+ " milliseconds to create index for the files in directory "
				+ dataDir.getPath());
	}

	/**
	 * 对File文件的信息进行提取整理
	 * @param postFile
	 * @param groupName
	 * @param number
	 * @return
	 */
	private static NewsPost parse(File postFile, String groupName, String number) {
		NewsPost newsPost = new NewsPost();
		newsPost.setGroup(groupName + "-" + number);
		// 这个主题应该是从postFile中提取，这里简化了，不进行提取了
		newsPost.setSubject(number + "-" + "Subject");
		try {
			newsPost.setBody(new FileReader(postFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return newsPost;
	}
}
