package com.sssppp.Lucene.TextProcess.AnalyzerText;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.Version;

/**
 * 通过PerFieldAnalyzerWrapper，可以控制不同的Field使用不同的Analyzer来进行text-->tokens
 * 不同的Field，使用不同的Analyzer，比较输出结果
 * 
 * @author linliu
 * 
 */
public class UsePerFieldAnalyzerWrapper {
	public static void main(String a[]) throws IOException {
		testPerFieldAnalyzerWrapper();
	}

	private static void testPerFieldAnalyzerWrapper() throws IOException {
		Version version = Version.LUCENE_47;

		// myfield:使用WhitespaceAnalyzer，其他Field：使用默认的Analyzer
		Map<String, Analyzer> analyzerPerField = new HashMap<String, Analyzer>();
		analyzerPerField.put("myfield", new WhitespaceAnalyzer(version));
		PerFieldAnalyzerWrapper defanalyzer = new PerFieldAnalyzerWrapper(
				new StandardAnalyzer(version), analyzerPerField);

		TokenStream ts = null;
		OffsetAttribute offsetAtt = null;
		CharTermAttribute charAtt = null;
		try {
			String text = "lucene.apache.org AB-978";
			ts = defanalyzer.tokenStream("myfield", new StringReader(text));
			System.out.println(textPositions(text));
			offsetAtt = ts.addAttribute(OffsetAttribute.class);
			charAtt = ts.addAttribute(CharTermAttribute.class);

			ts.reset();
			System.out
					.println("Processing field 'myfield' using WhitespaceAnalyzer");
			while (ts.incrementToken()) {
				System.out.println("【" + charAtt.toString() + "】");
				System.out.println("token start offset: "
						+ offsetAtt.startOffset());
				System.out.println("  token end offset: "
						+ offsetAtt.endOffset());
			}
			ts.end();
			System.out.println("================================");
			ts = defanalyzer.tokenStream("content", new StringReader(text));
			offsetAtt = ts.addAttribute(OffsetAttribute.class);
			charAtt = ts.addAttribute(CharTermAttribute.class);
			ts.reset();
			System.out
					.println("Processing field 'content' using StandardAnalyzer");
			while (ts.incrementToken()) {
				System.out.println("【" + charAtt.toString() + "】");
				System.out.println("token start offset: "
						+ offsetAtt.startOffset());
				System.out.println("  token end offset: "
						+ offsetAtt.endOffset());
			}
			ts.end();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ts.close();
		}
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
lucene.apache.org AB-978
012345678901234567890123
0         1         2   
Processing field 'myfield' using WhitespaceAnalyzer
【lucene.apache.org】
token start offset: 0
  token end offset: 17
【AB-978】
token start offset: 18
  token end offset: 24
================================
Processing field 'content' using StandardAnalyzer
【lucene.apache.org】
token start offset: 0
  token end offset: 17
【ab】
token start offset: 18
  token end offset: 20
【978】
token start offset: 21
  token end offset: 24
 */
  
