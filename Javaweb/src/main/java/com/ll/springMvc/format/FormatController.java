package com.ll.springMvc.format;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ll.springMvc.json.Person;

/**
 * 参考链接： http://www.cnblogs.com/ssslinppp/p/4600043.html
 * 没有测试成功
 * 
 * @author LINLIU-Serv
 * Date: 2017-5-23
 * Time: 上午09:12:13
 */
@Controller
@RequestMapping("/formatConvert")
public class FormatController {

	@RequestMapping("/formatTest")
	@ResponseBody
	public Person formatConvertTest(Person p) {
		System.out.println(p.toString());
		return p;
	}

}
