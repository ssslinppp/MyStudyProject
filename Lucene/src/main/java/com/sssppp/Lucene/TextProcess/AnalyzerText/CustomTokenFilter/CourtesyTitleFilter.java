package com.sssppp.Lucene.TextProcess.AnalyzerText.CustomTokenFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * 实现word扩展功能；比如：Dr扩展为Doctor
 * 
 * 测试类：
 * 1. TestCourtesyTitleFilter；
 * 2. 使用CourtesyTitleAnalyzer进行测试；
 * @author linliu
 * 
 */
public class CourtesyTitleFilter extends TokenFilter {
	Map<String, String> courtesyTitleMap = new HashMap<String, String>();

	//用于读取token的值
	private CharTermAttribute termAttr;

	public CourtesyTitleFilter(TokenStream input) {
		super(input);
		termAttr = addAttribute(CharTermAttribute.class);
		courtesyTitleMap.put("Dr", "doctor");
		courtesyTitleMap.put("Mr", "mister");
		courtesyTitleMap.put("Mrs", "miss");
	}

	@Override
	public boolean incrementToken() throws IOException {
		if (!input.incrementToken())
			return false;
		String small = termAttr.toString();
		//将单词的缩写转换为全写
		if (courtesyTitleMap.containsKey(small)) {
			termAttr.setEmpty().append(courtesyTitleMap.get(small));
		}
		return true;
	}
}





















