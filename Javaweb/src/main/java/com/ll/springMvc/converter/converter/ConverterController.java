package com.ll.springMvc.converter.converter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ll.springMvc.json.Person;

/**
 * 可参考：http://www.cnblogs.com/ssslinppp/p/4598102.html
 * 
 * @author LINLIU-Serv
 * Date: 2017-5-19
 * Time: 下午05:29:51
 */
@Controller
@RequestMapping("/converter")
public class ConverterController {

	/**
	 * 示例：http://localhost:8080/converter/converterTest?person=zhangsan:666
	 * 
	 * @param p
	 * @param mm
	 * @return
	 */
	@RequestMapping("/converterTest")
	@ResponseBody
	public Person converterTest(@RequestParam("person") Person p) {
		return p;
	}
}
