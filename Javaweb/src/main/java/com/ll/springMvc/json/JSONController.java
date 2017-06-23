package com.ll.springMvc.json;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 讲解如果返回单个对象的json,参考：http://www.cnblogs.com/ssslinppp/p/4528892.html
 * 
 * @author LINLIU-Serv Date: 2017-5-18 Time: 下午02:55:07
 */
@Controller
@RequestMapping("/kfc/brands")
public class JSONController {

	/**
	 * @ResponseBody： 作用：
	 *                该注解用于将Controller方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后
	 *                （如：json格式），写入到Response对象的body数据区。
	 *                使用时机：
	 *                返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	 * 示例：浏览器输入：http://localhost:8080/kfc/brands/sssppp
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "{name}", method = RequestMethod.GET)
	public @ResponseBody Shop getShopInJSON(@PathVariable String name) {
		Shop shop = new Shop();
		shop.setName(name);
		shop.setStaffName(new String[] { "mkyong1", "mkyong2" });

		return shop;
	}
}
