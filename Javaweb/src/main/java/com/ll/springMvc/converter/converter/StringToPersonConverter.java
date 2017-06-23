package com.ll.springMvc.converter.converter;

import org.springframework.core.convert.converter.Converter;

import com.ll.springMvc.json.Person;

/**
 * Spring MVC数据转换-简单示例
 * 将形如："zhangSan：888" 的字符串转换为Person对象
 * 
 * 《需要在 mvc:annotation-driven中装载》
 * 
 * @author LINLIU-Serv
 * Date: 2017-5-19
 * Time: 下午05:18:48
 */
public class StringToPersonConverter implements Converter<String,Person>{

	@Override
	public Person convert(String source) {
		Person p1 = new Person();
		if (source != null) {
			String[] items = source.split(":");
			p1.setUsername(items[0]);
			p1.setPasswd(items[1]);
		}
		return p1;
	}
}
