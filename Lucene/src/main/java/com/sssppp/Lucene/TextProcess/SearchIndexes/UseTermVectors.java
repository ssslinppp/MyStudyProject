package com.sssppp.Lucene.TextProcess.SearchIndexes;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.DocsAndPositionsEnum;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

/**
 * 通过设置setStoreTermVectors(true);
 * 可以保存Field的Index到TermVectors中，从而可以获取到每个Term的Frequency、Position、Offset等信息；
 * 
 * Here is a piece of text to be added to a document
 * 【humpty dumpty sat on a wall】
 * Here is what you will retrieve from TermVectors
 * |----------------------------------------
 * |Term    | Frequency |Position | Offset |
 * |----------------------------------------
 * |dumpty  |    1      |   1     |[7,13]  |
 * |----------------------------------------
 * |humpty  |    1      |   0     |[0,6]   | 
 * |----------------------------------------
 * |sat     |    1      |   2     |[14,17] |
 * |----------------------------------------
 * |wall    | 	 1      |   5     |[23,27] |
 * |----------------------------------------
 * @author linliu
 *
 */
public class UseTermVectors {
	static Version version = Version.LUCENE_47;

	public static void main(String arg[]) throws IOException {
		testTermVectors();
	}
	
	public static void testTermVectors() throws IOException {
		StandardAnalyzer analyzer = new StandardAnalyzer(version);
		Directory directory = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(version, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, config);

		//自定义FieldType
		FieldType textFieldType = new FieldType();
		textFieldType.setIndexed(true);
		textFieldType.setTokenized(true);
		textFieldType.setStored(true);
		textFieldType.setStoreTermVectors(true);
		textFieldType.setStoreTermVectorPositions(true);
		textFieldType.setStoreTermVectorOffsets(true);

		Document doc = new Document();
		Field textField = new Field("content", "", textFieldType);

		String[] contents = { 
				"Humpty Dumpty sat on a wall,",
				"Humpty Dumpty had a great fall.",
				"All the king's horses and all the king's men",
				"Couldn't put Humpty together again." 
				};
		for (String content : contents) {
			textField.setStringValue(content);
			doc.removeField("content");
			doc.add(textField);
			indexWriter.addDocument(doc);
		}
		indexWriter.commit();
		
		//搜索
		IndexReader indexReader = DirectoryReader.open(directory);
		DocsAndPositionsEnum docsAndPositionsEnum = null;
		Terms termsVector = null;
		TermsEnum termsEnum = null;
		BytesRef term = null;
		String val = null;

		for (int docId = 0; docId < indexReader.maxDoc(); docId++) {
			termsVector = indexReader.getTermVector(docId, "content");
			termsEnum = termsVector.iterator(termsEnum);
			while ((term = termsEnum.next()) != null) {
				val = term.utf8ToString();
				System.out.println("DocId: " + docId);
				System.out.println("  term: " + val);
				System.out.println("  length: " + term.length);
				docsAndPositionsEnum = termsEnum.docsAndPositions(null,
						docsAndPositionsEnum);
				if (docsAndPositionsEnum.nextDoc() >= 0) {
					int freq = docsAndPositionsEnum.freq();
					System.out
							.println("  freq: " + docsAndPositionsEnum.freq());
					for (int j = 0; j < freq; j++) {
						System.out.println("    [");
						System.out.println("      position: "
								+ docsAndPositionsEnum.nextPosition());
						System.out.println("      offset start: "
								+ docsAndPositionsEnum.startOffset());
						System.out.println("      offset end: "
								+ docsAndPositionsEnum.endOffset());
						System.out.println("    ]");
					}
				}
			}
		}
	}
}








