package com.sssppp.Lucene.TextProcess.AnalyzerText.CustomTokenizers;

import java.io.Reader;

import org.apache.lucene.analysis.util.CharTokenizer;
import org.apache.lucene.util.Version;

public class MyTokenizer extends CharTokenizer {

	public MyTokenizer(Version matchVersion,Reader reader) {
		super(matchVersion, reader);
	}

	/**
	 * 从输入流Reader中，读取一个接一个的character，并判断该character是token
	 * character还是“分割character”
	 */
	@Override
	protected boolean isTokenChar(int c) {
		/*
		 * 这是设置仅仅通过空格分隔文本；
		 * 当c为空格时，返回false，意味着这是一个tokenChar
		 */
		return !Character.isSpaceChar(c);
	}

}
