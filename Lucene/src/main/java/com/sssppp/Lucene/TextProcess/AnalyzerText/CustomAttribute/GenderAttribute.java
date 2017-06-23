package com.sssppp.Lucene.TextProcess.AnalyzerText.CustomAttribute;

import org.apache.lucene.util.Attribute;

public interface GenderAttribute extends Attribute {

	public static enum Gender {
		Male, Female, Undefined
	};

	public void setGender(Gender gender);

	public Gender getGender();
}
