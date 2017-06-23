package com.ll.springMvc.i18n;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ll.springMvc.json.Person;

/**
 * 参考链接：http://www.cnblogs.com/ssslinppp/p/4606729.html
 * 
 * @author LINLIU-Serv
 * Date: 2017-5-26
 * Time: 下午04:20:47
 */
@Controller
@RequestMapping("/i18n")
public class I18nController {

	/**
	 * 对应的配置文件：springMvc-i18n.xml
	 * 
	 * 当控制器返回一个名为“diffi18n”的视图时，ResourceBundleViewResolver将在“spring-views.properties”文件中
	 * 查找以“diffi18n”起始的键，并返回相对应的视图URL
	 * 给DispatcherServlet。
	 * 
	 * @param mmMap
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(ModelMap mmMap) {
		Person person = new Person();
		person.setUsername("Tommy");
		person.setPasswd("666666");
		
		mmMap.addAttribute("person", person);

		return "diffi18n";
	}
	
	/**
	 * 返回Json数据（一般情况下不会使用这种方式）
	 * @param mmMap
	 * @return
	 */
	@RequestMapping(value = "/json")
	public String jsonTest(ModelMap mmMap) {
		Person person = new Person();
		person.setUsername("Tom-Json");
		person.setPasswd("666666");
		
		mmMap.addAttribute("person", person);

		return "jsonView";
	}

}
