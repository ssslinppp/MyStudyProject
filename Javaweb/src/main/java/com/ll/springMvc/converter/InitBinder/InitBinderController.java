package com.ll.springMvc.converter.InitBinder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ll.springMvc.json.Person;

/**
 * 参考： http://www.cnblogs.com/ssslinppp/p/4599436.html
 */
@Controller
@RequestMapping("/initBinder")
public class InitBinderController {

	/**
	 * 控制器初始化时调用
	 * 
	 * Spring MVC使用WebDataBinder处理< 请求消息, 方法入参>的绑定工作
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Person.class, new PersonEditor());
	}

	/**
	 * 示例： http://localhost:8080/initBinder/initBinderTest?person=zhangsan:666:
	 * ssss:3.1415
	 * 
	 * @param p
	 * @return
	 */
	@RequestMapping("/initBinderTest")
	@ResponseBody
	public Person converterTest(@RequestParam("person") Person p) {
		return p;
	}
}
