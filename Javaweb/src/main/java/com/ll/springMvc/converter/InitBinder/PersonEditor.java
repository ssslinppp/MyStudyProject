package com.ll.springMvc.converter.InitBinder;

import java.beans.PropertyEditorSupport;

import com.ll.springMvc.json.Person;

/**
 * 类型转换器常用于转换double、float、date等类型。
 * 
 * 功能说明： 当表单提交double、date等类型时，我们需要将其转换为java可识别的date，double等; 如在浏览器中输入：
 * http://localhost:8080/xxx/conversionTest?person=zhangsan:666:ssss:3.1415时，
 * 需要将其转化到Person对象中，此时需要对double类型进行转换。
 * 
 * 
 * @author LINLIU-Serv Date: 2017-5-19 Time: 下午05:50:09
 */
public class PersonEditor extends PropertyEditorSupport {

	/**
	 * 示例： zhangsan:666:ssss:3.1415转换为 Person对象
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Person p = new Person();
		if (text != null) {
			String[] items = text.split(":");
			p.setUsername(items[0] + "by propertyeEditor");
			p.setPasswd(items[1]);
			p.setRealName(items[2]);
			p.setPrice(Double.parseDouble(items[3]));
		}
		setValue(p);
	}

	@Override
	public String getAsText() {
		return getValue().toString();
	}

}
