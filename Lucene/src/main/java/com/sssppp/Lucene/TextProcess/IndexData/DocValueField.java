package com.sssppp.Lucene.TextProcess.IndexData;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.SortedDocValues;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

/**
 * 【行式存储】
 * StringField/TextField/IntField等都是采用的行式存储；
 * 即：可以一次获取到同一个document的所有Field的数据，但是不能一次获取到多个document的同一个Field的值；
 * 
 * 【列式存储】
 * DocValueField
 * 即：可以一次获取到多个document的同一个Field的值，但是不能一次获取到同一个document的所有Field的数据；
 * 示例：下面是FieldName_xx的列式存储方式
 * |--------------------
 * | FieldName_xx
 * ---------------------
 * | docId | fieldValue |
 * |--------------------
 * |   0   |  value0    |
 * |   1   |  value1    |
 * |   2   |  value2    |
 * |   3   |  value3    |
 * |  ...  |   ...      |
 * |   n   |  valuen    |
 * |---------------------
 * @author linliu
 *
 */
public class DocValueField {
	public static void main(String a[]) throws IOException {
		Version version = Version.LUCENE_47;
		Analyzer analyzer = new StandardAnalyzer(version);
		Directory directory = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(version, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, config);
		
		//SortedDocValuesField采用的是【列式存储】
		Document document = new Document();
		document.add(new SortedDocValuesField("sorted_string", new BytesRef(
				"hello")));
		indexWriter.addDocument(document);
		//创建新的document
		document = new Document();
		document.add(new SortedDocValuesField("sorted_string", new BytesRef(
				"world")));
		indexWriter.addDocument(document);
		
		indexWriter.commit();
		indexWriter.close();
		
		/**
		 * IndexReader有两个实现类
		 * 1. AtomicReader ：原子的方式去操作segment
		 * 2. CompositeReader：由若干个AtomicReader组成
		 *    DirectoryReader属于CompositeReader
		 */
		IndexReader reader = DirectoryReader.open(directory);
		document = reader.document(0);
		System.out.println("doc 0: " + document.toString());
		document = reader.document(1);
		System.out.println("doc 1: " + document.toString());
		
		BytesRef result = new BytesRef();
		//一次性查询多个document中的同一个Field
		for (AtomicReaderContext context : reader.leaves()) {
			AtomicReader atomicReader = context.reader();
			// 返回指定Field的所有docValues ==>一个列式的存储结构
			SortedDocValues sortedDocValues = atomicReader
					.getSortedDocValues("sorted_string");
			System.out.println("Value count: "
					+ sortedDocValues.getValueCount());

			sortedDocValues.get(0, result); //根据docId获取对应的Field
			System.out.println("doc 0 sorted_string: " + result.utf8ToString());

			sortedDocValues.get(1, result);
			System.out.println("doc 1 sorted_string: " + result.utf8ToString());
		}
		reader.close();
	}
}




















