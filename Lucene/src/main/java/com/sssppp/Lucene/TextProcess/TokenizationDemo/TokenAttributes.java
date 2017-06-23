package com.sssppp.Lucene.TextProcess.TokenizationDemo;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.Version;

public class TokenAttributes {
	public static void main(String a[]) throws IOException {
		String text = "Lucene is mainly used for information retrieval and you can read more about it at lucene.apache.org.";
		System.out.println(textPositions(text));
		StringReader reader = new StringReader(text);
		StandardAnalyzer wa = new StandardAnalyzer(Version.LUCENE_47);
		TokenStream ts = null;
		try {
			ts = wa.tokenStream("field", reader);
			OffsetAttribute offsetAtt = ts.addAttribute(OffsetAttribute.class);
			//获取token的内容
			CharTermAttribute termAtt = ts
					.addAttribute(CharTermAttribute.class);
			System.out.println("Token 【starting offset】   【ending offset】: "
					+ offsetAtt.startOffset());
			ts.reset(); //确保从开始处迭代
			while (ts.incrementToken()) {
				String token = termAtt.toString(); 
				System.out.print("【" + token + "】");
				System.out.print("   (" + offsetAtt.startOffset() + ",");
				System.out.print(offsetAtt.endOffset() + ")");
				System.out.println("");
			}
			ts.end();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ts.close();
			wa.close();
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
