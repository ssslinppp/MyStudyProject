package com.sssppp.Lucene.TextProcess.AnalyzerText;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.ChineseAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

/**
 * 主要功能：
 * Lucene使用Analyzer将text转换为tokens；
 * 这里使用不同的Analyzer，比较转换后的结果；
 * 
 * Analyzer：
 * 由Tokenizer和若干TokenFilter构成；
 */
public class AnalyzerCompare {

	private AnalyzerCompare() {}

	public static Version VERSION = Version.LUCENE_47;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		String text = "Lucene is mainly used for information retrieval ,http://lucene.apache.org";
		System.out.println(textPositions(text));
		
		Analyzer whitespaceAnalyzer = new WhitespaceAnalyzer(VERSION);
		Analyzer simpleAnalyzer = new SimpleAnalyzer(VERSION);
		Analyzer stopAnalyzer = new StopAnalyzer(VERSION);
		Analyzer standardAnalyzer = new StandardAnalyzer(VERSION);
		Analyzer englishAnalyzer = new EnglishAnalyzer(VERSION);
		
		System.out.println("whitespaceAnalyzer--以空格分隔文本：");
		analyerTextToTokens(text,whitespaceAnalyzer);
		System.out.println("simpleAnalyzer----文本小写，以“非字符”分隔：");
		analyerTextToTokens(text,simpleAnalyzer);
		System.out.println("stopAnalyzer-----剔除常用词：");
		analyerTextToTokens(text,stopAnalyzer);
		System.out.println("standardAnalyzer---：");
		analyerTextToTokens(text,standardAnalyzer);
		System.out.println("englishAnalyzer---：");
		analyerTextToTokens(text,englishAnalyzer);
		
		String textZH = "我爱北京天安门";
		Analyzer chineseAnalyzer = new ChineseAnalyzer();
		System.out.println("chineseAnalyzer--解析中文(解析的不理想)：");
		analyerTextToTokens(textZH,chineseAnalyzer);
	}

	private static void analyerTextToTokens(String text, Analyzer analyzer)
			throws IOException {
		Reader textReader = new StringReader(text);
		TokenStream tokenStream = analyzer.tokenStream("text", textReader);
		try {
			tokenStream.reset();
			//可用于获取token的内容
			CharTermAttribute terms = tokenStream
					.addAttribute(CharTermAttribute.class);

			while (tokenStream.incrementToken()) { //遍历所有的token
				String term = terms.toString(); // token的内容
				System.out.printf("【%s】", term);
			}
			tokenStream.end();
		} finally {
			tokenStream.close();
			textReader.close();
		}
		System.out.println();
		System.out.println();
	}

	private static String textPositions(CharSequence in) {
		StringBuilder sb = new StringBuilder();
		sb.append(in);
		for (int base = 1; base <= in.length(); base *= 10) {
			sb.append('\n');
			for (int i = 0; i < in.length(); ++i)
				sb.append(i % base == 0 ? Integer.toString((i / base) % 10)
						: " ");
		}
		return sb.toString();
	}
}
/**
Lucene is mainly used for information retrieval ,http://lucene.apache.org
0123456789012345678901234567890123456789012345678901234567890123456789012
0         1         2         3         4         5         6         7  
whitespaceAnalyzer--以空格分隔文本：
【Lucene】【is】【mainly】【used】【for】【information】【retrieval】【,http://lucene.apache.org】
simpleAnalyzer----文本小写，以“非字符”分隔：
【lucene】【is】【mainly】【used】【for】【information】【retrieval】【http】【lucene】【apache】【org】
stopAnalyzer-----剔除常用词：
【lucene】【mainly】【used】【information】【retrieval】【http】【lucene】【apache】【org】
 standardAnalyzer---：
【lucene】【mainly】【used】【information】【retrieval】【http】【lucene.apache.org】
 */

















