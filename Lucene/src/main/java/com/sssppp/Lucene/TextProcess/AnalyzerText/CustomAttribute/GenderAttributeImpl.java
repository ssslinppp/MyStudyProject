package com.sssppp.Lucene.TextProcess.AnalyzerText.CustomAttribute;

import org.apache.lucene.util.AttributeImpl;

/**
 * 这个类名称很重要，Lucene使用后缀“Impl”来定位Attribute接口的实现类
 * @author linliu
 *
 */
public class GenderAttributeImpl extends AttributeImpl implements
		GenderAttribute {
	private Gender gender = Gender.Undefined;

	@Override
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public Gender getGender() {
		return gender;
	}

	@Override
	public void clear() {
		gender = Gender.Undefined;
	}

	@Override
	public void copyTo(AttributeImpl target) {
		((GenderAttribute) target).setGender(gender);
	}

}
