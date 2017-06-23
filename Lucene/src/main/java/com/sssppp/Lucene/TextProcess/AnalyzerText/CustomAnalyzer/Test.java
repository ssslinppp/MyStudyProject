package com.sssppp.Lucene.TextProcess.AnalyzerText.CustomAnalyzer;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * 测试自定义的：CourtesyTitleAnalyzer
 * 
 * @author linliu
 * 
 */
public class Test {
	public static void main(String a[]) throws IOException {
		String text = "Dr Watson";

		Analyzer analyzer = new CourtesyTitleAnalyzer();
		// 会返回Analyzer.createComponents中最后定义的TokenFilter，这里将返回CourtesyTitleFilter
		TokenStream tokenStream = analyzer.tokenStream("myField", text);

		// 用于获取token的内容
		CharTermAttribute terms = tokenStream
				.addAttribute(CharTermAttribute.class);
		try {
			tokenStream.reset();
			while (tokenStream.incrementToken()) {
				String context = terms.toString();
				System.out.println(context);
			}
			tokenStream.end();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			tokenStream.close();
		}
	}
}
