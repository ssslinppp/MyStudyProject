package com.sssppp.Lucene.TextProcess.AnalyzerText.CustomAttribute;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class Test {
	public static void main(String a[]) {
		String text = "mr zhang, mrs wang";

		Analyzer genderAnalyzer = new Analyzer() {
			Version version = Version.LUCENE_47;

			@Override
			protected TokenStreamComponents createComponents(String fieldName,
					Reader reader) {
				Tokenizer tokenizer = new StandardTokenizer(version, reader);
				//【Male】【 zhang】 【Female】【 wang】
				GenderFilter genderFilter = new GenderFilter(tokenizer);
				//【male】【 zhang】 【female】【 wang】
				LowerCaseFilter lowerCaseFilter = new LowerCaseFilter(version, genderFilter);
				return new TokenStreamComponents(tokenizer, lowerCaseFilter);
			}
		};

		try {
			TokenStream tokenStream = genderAnalyzer.tokenStream("myField",
					text);
			CharTermAttribute charTermAttribute = tokenStream
					.addAttribute(CharTermAttribute.class);
			GenderAttribute genderAttribute = tokenStream
					.addAttribute(GenderAttribute.class);
			tokenStream.reset();
			while (tokenStream.incrementToken()) {
				System.out.print("Token's content :【" + charTermAttribute.toString() + "】--->");
				System.out.println("【" + genderAttribute.getGender() + "】");
			}
			tokenStream.end();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			genderAnalyzer.close();
		}

	}
}
