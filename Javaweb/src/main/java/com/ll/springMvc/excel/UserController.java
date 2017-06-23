package com.ll.springMvc.excel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 将Javaweb项目放入tomcat并启动后，在浏览器中输入：
 * http://localhost:8080/user/showUserListByXls
 * (or) 
 * http://localhost:8080/项目名/user/showUserListByXls
 * 就会返回Excel
 * 
 * 可参考：http://www.cnblogs.com/ssslinppp/p/4432434.html
 * 
 * @author LINLIU-Serv
 * Date: 2017-5-17
 * Time: 下午06:23:49
 */
@Controller
@RequestMapping("/user")
public class UserController {
	/**
	 * 使用BeanNameViewResolver进行解析
	 * 
	 * @param mm
	 *            显示的声明为ModelMap，这样所有的模型数据都可以从mm中读取；
	 *            同样，向mm中添加属性时，实际也是向模型数据列表中添加。
	 * @return
	 */
	@RequestMapping(value = "/showUserListByXls")
	public String showUserListInExcel(ModelMap mm) {
		List<User> userList = new ArrayList<User>();
		User user1 = new User();
		user1.setUserName("tom");
		user1.setRealName("汤姆");

		User user2 = new User();
		user2.setUserName("john");
		user2.setRealName("约翰");
		
		userList.add(user1);
		userList.add(user2);
		
		mm.addAttribute("userList", userList);
		return "userListExcel";
	}
}