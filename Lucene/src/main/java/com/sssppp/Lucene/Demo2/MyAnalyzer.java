package com.sssppp.Lucene.Demo2;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;

/**
 * 【Analyzer】：将text转换为tokens
 * 
 * createComponents()的作用：（个人理解）
 * 在Analyzer将text转换为tokens之前，对text进行一些处理，如：大写转小写；
 * 
 * @author linliu
 */
public class MyAnalyzer extends Analyzer {
	/**
	 * @----------TokenStream解析-------------------
	 * @ TokenStream ：将输入的text转换为一系列的tokens
	 * @ TokenStream的设计采用了“装饰者模式”；
	 * @ TokenStream是最顶层的【超类】；
	 * @    Tokenizer和TokenFilter都是TokenStream的【子类】；
	 * @    1. Tokenizer   :充当了被装饰的类，其输入是一个Reader; 
	 * @    2. TokenFilter ： 充当了装饰类，其输入是一个另一个TokenStream
	 * @ 可以和InputStream做【类比】；
	 * @ TokenStream <==> InputStream（超类）
	 * @ Tokenizer   <==> 各种InputSteam，如：FileInputStream  ==>用于获取输入源
	 * @ TokenFilter <==> 各种FilterInputStream，如：BufferedInputStream
	 * @ ==>这样可以形成一个chain，Tokenizer用于获取输入源，然后用多个TokenFilter进行装饰
	 * @--------------------------------
	 * @ -------------TokenStreamComponents解析
	 * @ TokenStreamComponents由2部分组成：
	 * @    1. Tokenizer  ： 表示text的source
	 * @	2. TokenStream：表示一系列TokenFilter组成的链chain
	 * @-------------------------------------------
	 * @
	 */
	@Override
	protected TokenStreamComponents createComponents(String fieldName,
			Reader reader) {
		Version version = Version.LUCENE_47;
		final Tokenizer source = new StandardTokenizer(version, reader);// 获取text输入源
		TokenStream filter = new LowerCaseFilter(version, source); // 转换为小写
		return new TokenStreamComponents(source, filter);
	}

}






















