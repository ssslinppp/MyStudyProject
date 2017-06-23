package com.sssppp.Lucene.TextProcess.SearchIndexes.Sort.one;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 * 默认情况下，Lucene的搜索结果，是根据 Relevant score（相关性得分）的降序进行排序的；
 * Sort class有两个静态的排序方式：
 * 1. RELEVANCE ：基于Relevant Score进行排序（默认行为）；
 * 2. INDEX ORDER：基于Index顺序进行排序；
 * 
 * 本示例程序将演示自定义排序方式；
 * 共有11个Document，每个Document都包含两个Field：
 * 1. name；
 * 2. addr；
 * 我们希望搜索的结果为：
 * 1. 首先按照addr的字母顺序排序；
 * 2. 当addr相同时，在按照name的字母顺序排列
 * 
 * @author linliu
 *
 */
public class SortSearchResult {
	static Version version = Version.LUCENE_47;

	public static void main(String arg[]) throws IOException {
		testSort();
	}

	public static void testSort() throws IOException {
		StandardAnalyzer analyzer = new StandardAnalyzer(version);
		Directory directory = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(version, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, config);

		StringField stringField = new StringField("name", "", Field.Store.YES);
		StringField addrField = new StringField("addr", "", Field.Store.YES);
		String text[][] = new String[11][];
		text[0] = new String[]{"foxtrot","beijing"};
		text[1] = new String[]{"echo","xiamen"};
		text[2] = new String[]{"foxtrot","tianjin"};
		text[3] = new String[]{"delta","xuzhou"};
		text[4] = new String[]{"charlie","jiangsu"};
		text[5] = new String[]{"echo","haikou"};
		text[6] = new String[]{"echo","fujian"};
		text[7] = new String[]{"delta","haikou"};
		text[8] = new String[]{"bravo","nanjing"};
		text[9] = new String[]{"echo","shanghai"};
		text[10] = new String[]{"alpha","haikou"};
		
		Document doc = new Document();
		for (int i = 0; i < text.length; i++) {
			stringField.setStringValue(text[i][0]);
			addrField.setStringValue(text[i][1]);
			doc.removeField("name");
			doc.removeField("addr");
			doc.add(stringField);
			doc.add(addrField);
			indexWriter.addDocument(doc);
		}
		indexWriter.commit();
		
		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
	
		//通配符查询
//		WildcardQuery query = new WildcardQuery(new Term("name", "*o*"));
		WildcardQuery query = new WildcardQuery(new Term("name", "*"));
		//首先按照name字母排序，name相同时，再按照addr的字母顺序排序
		SortField[] sortFields = new SortField[2];
		sortFields[0] = new SortField("name", SortField.Type.STRING);
		sortFields[1] = new SortField("addr", SortField.Type.STRING);
		Sort sort = new Sort(sortFields);
		
		//若是设置了Filter，则搜索结果中只包含Filter中的数据
//		Filter filter = new TermFilter(new Term("name", "echo"));
		Filter filter = null;
		
		TopDocs topDocs = indexSearcher.search(query, filter, 100, sort);
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			doc = indexReader.document(scoreDoc.doc);
			System.out.printf("【score】 ：%f-->%8s, %s\n", scoreDoc.score, doc
					.getField("addr").stringValue(), doc.getField("name")
					.stringValue());
		}
	}
}
/**
 * 输出结果：
 * 1. Relevant score没有了；==>因为我们指定的sort中没有包含score；
 * 2. 输出结果首先按照addr顺序排列;
 * 3. 当addr顺序相同时，在按照name的顺序排列；
【score】 ：NaN--> beijing, foxtrot
【score】 ：NaN-->  fujian, echo
【score】 ：NaN-->  haikou, alpha
【score】 ：NaN-->  haikou, delta
【score】 ：NaN-->  haikou, echo
【score】 ：NaN--> jiangsu, charlie
【score】 ：NaN--> nanjing, bravo
【score】 ：NaN-->shanghai, echo
【score】 ：NaN--> tianjin, foxtrot
【score】 ：NaN-->  xiamen, echo
【score】 ：NaN-->  xuzhou, delta
 * 
 */






















