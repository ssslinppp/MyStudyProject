package com.ll.springMvc.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
	
/**
 * 将Javaweb项目放入tomcat并启动后，在浏览器中输入：
 * http://localhost:8080/hello/world
 * (or) 
 * http://localhost:8080/项目名/hello/world
 * 进行测试
 * 
 * 可参考：http://www.cnblogs.com/ssslinppp/p/4432434.html
 * 	
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

	/**
	 * 会返回 WEB-INF/views/hello.jsp；
	 * 使用InternalResourceViewResolver解析器，在springMvc-servlet.xml中进行的配置；
	 * @return
	 */
	@RequestMapping("/world")
	public String hello() {
		return "hello";
	}
}
