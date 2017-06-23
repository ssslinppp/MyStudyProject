package com.ll.springMvc.json;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author LINLIU-Serv Date: 2017-5-19 Time: 下午02:21:11
 */
@Controller
@RequestMapping(value = "/json")
public class JSONController2 {
	/**
	 * 讲解如果返回多个对象的json,参考：http://www.cnblogs.com/ssslinppp/p/4675495.html
	 * 
	 * 浏览器输入：http://localhost:8080/json/list
	 * 
	 * 输出结果： {"School":"Suzhou","Work":"研发"
	 * ,"userList":[{"username":"Tom","passwd":"66666"
	 * },{"username":"Jone","passwd":"88888"}]}
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserList() {
		List<Person> list = new ArrayList<Person>();
		Person user1 = new Person();
		user1.setUsername("Tom");
		user1.setPasswd("66666");
		list.add(user1);

		Person user2 = new Person();
		user2.setUsername("Jone");
		user2.setPasswd("54321");
		list.add(user2);

		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		modelMap.put("Work", "研发");
		modelMap.put("School", "Suzhou");
		modelMap.put("userList", list);
		return modelMap;
	}

	/**
	 * 讲解如果返回多个对象的json,参考：http://www.cnblogs.com/ssslinppp/p/4530002.html
	 * 
	 * 使用BeanNameViewResolver进行解析
	 * 
	 * 浏览器输入：http://localhost:8080/json/showUserListByJson
	 * 
	 * @param mm
	 *            显示的声明为ModelMap，这样所有的模型数据都可以从mm中读取；
	 *            同样，向mm中添加属性时，实际也是向模型数据列表中添加。
	 * 
	 * @return
	 */
	@RequestMapping(value = "/showUserListByJson")
	public String showUserListInJson(ModelMap mm) {
		List<Person> userList = new ArrayList<Person>();

		Person user1 = new Person();
		user1.setUsername("tom");
		user1.setPasswd("汤姆");

		Person user2 = new Person();
		user2.setUsername("john");
		user2.setPasswd("约翰");

		userList.add(user1);
		userList.add(user2);

		//向mm中添加属性时，实际也是向模型数据列表中添加
		mm.addAttribute("userList", userList);
		mm.addAttribute("School", "SuZhou");
		mm.addAttribute("Work", "YanFa");

		return "userListJson";
	}
}
