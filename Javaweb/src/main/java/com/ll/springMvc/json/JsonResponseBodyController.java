package com.ll.springMvc.json;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * SpringMVC 之@RequestBody 接收Json数组对象
 * 
 * @author LINLIU-Serv
 * Date: 2017-5-19
 * Time: 下午04:11:22
 */
@Controller
@RequestMapping("/jsonReq")
public class JsonResponseBodyController {

	/**
	 * @RequestBody: 接受ajax传递的json字符串
	 * @ResponseBody: 返回Json字符串
	 * 
	 * 参考链接：http://www.cnblogs.com/ssslinppp/p/4597251.html
	 * 
	 * 使用ajax异步发送json数据请求
	 * 1. 使用ajax技术，传递json字符串到后台；（可以使用postman插件模拟ajax发送）
	 * 2. 后台使用@RequestBody接受前台传递的json字符串，并返回新的json字符串到前台；
	 * 3. 前台接受后台传递过来的json数据，并显示。
	 * 
	 * 前台传递的json数据如：
	 * [{"username":"Tom", "passwd":"111"},{"username":"Jam", "passwd":"222"}]
	 * 
	 * 需要设置：post发送方式，Content-Type: application/json
	 * -----------HTTP请求报文：示例--------------
	 * POST /jsonReq/jsonDataReq HTTP/1.1
	 * Host: 10.200.8.155:8080
	 * Content-Type: application/json
	 * Cache-Control: no-cache
	 * Postman-Token: 42e6e656-1faa-909c-98d5-d0aaf1b1d583
	 * 
	 * [{"username":"Tom", "passwd":"111"},{"username":"Jam", "passwd":"222"}]
	 * ---------------------------------
	 * @param persons
	 * @return
	 */
	@RequestMapping(value = "/jsonDataReq", method = { RequestMethod.POST })
	@ResponseBody
	public Person jsonDataReq(@RequestBody Person[] persons) {
		for (Person person : persons) {
			System.out.println(person.getUsername() + ":" + person.getPasswd());
		}

		return new Person("Lily", "123456");

	}
	
	/**
	 * 使用ajax请求，返回Json数据
	 * 
	 * 使用MappingJacksonJsonView和bean视图解析器返回json数据
	 * 
	 * @param mm
	 *            显示的声明为模型数据
	 * @param username
	 * @param passwd
	 * @return
	 */
	@RequestMapping(value = "/respJson/{username}/{passwd}")
	public String getFusionChartsData(ModelMap mm,
			@PathVariable String username, @PathVariable String passwd) {
		System.out.println("ajax-使用MappingJacksonJsonView和bean视图解析器返回json数据==>"
				+ username + "(" + passwd + ")");

		List<Person> userList = new ArrayList<Person>();
		Person p1 = new Person(username + "_1", passwd + "_*1");
		Person p2 = new Person(username + "_2", passwd + "_*2");
		userList.add(p1);
		userList.add(p2);
		
		mm.addAttribute("userList", userList);
		mm.addAttribute("Info", "Test spring MVC with MappingJacksonJsonView");

		return "testMVC";
	}
	
}
