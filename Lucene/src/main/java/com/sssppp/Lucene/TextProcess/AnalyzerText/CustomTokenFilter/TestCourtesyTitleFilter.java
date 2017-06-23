package com.sssppp.Lucene.TextProcess.AnalyzerText.CustomTokenFilter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

/**
 * 测试自定义的：CourtesyTitleFilter
 * 
 * @author linliu
 * 
 */
public class TestCourtesyTitleFilter {
	public static void main(String a[]) throws IOException {
		Version version = Version.LUCENE_47;
		String text = "Dr Watson";
		Reader reader = new StringReader(text);

		// 按照 空格 分割，得到： 【Dr】【 Watson】
		WhitespaceTokenizer whitespaceTokenizer = new WhitespaceTokenizer(
				version, reader);
		// 会将单词扩展，“Dr” --> "doctor"，得到 【doctor】【 Watson】
		CourtesyTitleFilter courtesyTitleFilter = new CourtesyTitleFilter(
				whitespaceTokenizer);
		// 转换为小写： 【doctor】【 watson】
		LowerCaseFilter lowerCaseFilter = new LowerCaseFilter(version,
				courtesyTitleFilter);

		CharTermAttribute terms = lowerCaseFilter
				.addAttribute(CharTermAttribute.class);
		try {
			lowerCaseFilter.reset();
			while (lowerCaseFilter.incrementToken()) {
				String context = terms.toString();
				System.out.println(context);
			}
			lowerCaseFilter.end();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			lowerCaseFilter.close();
			reader.close();
		}
	}
}
