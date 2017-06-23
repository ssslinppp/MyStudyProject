package com.sssppp.Lucene.TextProcess.AnalyzerText.CustomAttribute;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class GenderFilter extends TokenFilter {

	GenderAttribute genderAtt = addAttribute(GenderAttribute.class);
	CharTermAttribute charTermAtt = addAttribute(CharTermAttribute.class);

	protected GenderFilter(TokenStream input) {
		super(input);
	}

	/**
	 * 作用：
	 * 1. 修改GenderAttribute值：转换mr，mister-->Male等；
	 * 2. 修改CharTermAttribute值； ===>显示token的内容
	 */
	@Override
	public boolean incrementToken() throws IOException {
		if (!input.incrementToken()) {
			return false;
		}
		genderAtt.setGender(determineGender(charTermAtt.toString()));
		
		//修改charTermAtt值==>这一步可以不添加
		if (!genderAtt.getGender().equals(GenderAttribute.Gender.Undefined)) {
			charTermAtt.setEmpty().append(genderAtt.getGender().toString());
		}
		return true;
	}

	/**
	 * 判断性别
	 * @param term
	 * @return
	 */
	protected GenderAttribute.Gender determineGender(String term) {
		if (term.equalsIgnoreCase("mr") || term.equalsIgnoreCase("mister")) {
			return GenderAttribute.Gender.Male;
		} else if (term.equalsIgnoreCase("mrs")
				|| term.equalsIgnoreCase("misters")) {
			return GenderAttribute.Gender.Female;
		}
		return GenderAttribute.Gender.Undefined;
	}

}
