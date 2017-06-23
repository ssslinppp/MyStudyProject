package com.sssppp.Lucene.TextProcess.AnalyzerText.CustomAnalyzer;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LetterTokenizer;
import org.apache.lucene.util.Version;

import com.sssppp.Lucene.TextProcess.AnalyzerText.CustomTokenFilter.CourtesyTitleFilter;

public class CourtesyTitleAnalyzer extends Analyzer {

	Version version = Version.LUCENE_47;

	@Override
	protected TokenStreamComponents createComponents(String fieldName,
			Reader reader) {
		//以非字母作为分隔符
		Tokenizer letterTokenizer = new LetterTokenizer(version, reader);
		//使用自定义的CourtesyTitleFilter，将缩写转换为全写
		TokenStream filter = new CourtesyTitleFilter(letterTokenizer);
		return new TokenStreamComponents(letterTokenizer, filter);
	}
}