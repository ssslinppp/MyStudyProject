package com.sssppp.Lucene.TextProcess.TokenizationDemo;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

/**
 * 主要功能：
 * 将输入的string转换为一系列的tokens，并获取存储在token中的属性信息；
 * 
 * 主要介绍了：
 * 1. TokenStream:介绍了Tokenizer和TokenFilter的使用；
 * 2. TokenFilter：介绍了3种TokenFilter的使用LowerCaseFilter/StopFilter/PorterStemFilter;
 * 3. 介绍了常用的TokenAttribute的使用；
 * @author linliu
 *
 */
public class LuceneTokenize {

	private LuceneTokenize() {}

	public static Version VERSION = Version.LUCENE_47;

	public static ArrayList<String> STOPLIST;
	static {
		STOPLIST = new ArrayList<String>();
		STOPLIST.add("a");
		STOPLIST.add("of");
		STOPLIST.add("the");
		STOPLIST.add("to");
	}
	public static CharArraySet STOPWORDS = new CharArraySet(VERSION, STOPLIST, true);

	public static void main(String[] args) throws IOException {
		String text = "This method is called by the consumer after the last token has been consumed";
		System.out.println(textPositions(text));

		/**
		 * TokenStream：装饰者模式
		 * StandardTokenizer:用于获取text输入源，将text转换为words流，并去除空格以及标点符号等；
		 * LowerCaseFilter/StopFilter/PorterStemFilter：为3种不同的tokenization策略
		 */
		Reader textReader = new StringReader(text);
		StandardTokenizer standardTokenizer = new StandardTokenizer(VERSION, textReader);
		LowerCaseFilter lowercaseFilter = new LowerCaseFilter(VERSION, standardTokenizer);
		StopFilter stopFilter = new StopFilter(VERSION, lowercaseFilter, STOPWORDS);
		PorterStemFilter stemFilter = new PorterStemFilter(stopFilter);

		System.out.printf("\n%s %s (%s, %s)\n", "TERM", "POSITION", "START", "END");
		try {
			//Lucene4中，TokenStream被实例化后，reset()必须在使用其他方法之前调用
			stemFilter.reset();
			//保存token的内容
			CharTermAttribute terms = stemFilter.addAttribute(CharTermAttribute.class);
			//holds the token’s start and end character offset in the input text
			OffsetAttribute offsets = stemFilter.addAttribute(OffsetAttribute.class);
			//stores the position of the token relative to the previous tokens in the token stream
			PositionIncrementAttribute positions = stemFilter.addAttribute(PositionIncrementAttribute.class);
			
			int position = 0;
			while (stemFilter.incrementToken()) {
				String term = terms.toString(); //token的内容
				position += positions.getPositionIncrement();
				int start = offsets.startOffset();
				int end = offsets.endOffset();
				System.out.printf("%s \t%d (%2d, %2d)\n", term, position, start, end);
			}
			stemFilter.end();
			/* x */
		} finally {
			stemFilter.close();
			textReader.close();
		}
	}

	public static String textPositions(CharSequence in) {
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

















