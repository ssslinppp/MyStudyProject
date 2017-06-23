package com.ll.springMvc.validate;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 参考： http://www.cnblogs.com/ssslinppp/p/4601894.html
 * 
 * @author LINLIU-Serv
 * Date: 2017-5-26
 * Time: 上午10:32:00
 */
@Controller
@RequestMapping(value = "/validate")
public class ValidateController {

	/**
	 * @ModelAttribute 注解方法:用于添加一个或多个属性到model上，其返回值会被放入Model对象中供视图使用。
	 *                 在同一个控制器中，标注了@ModelAttribute的方法实际上会在@RequestMapping方法之前被调用
	 *                 必须要添加这个，不然会报错
	 * 
	 * @return
	 */
	@ModelAttribute("stuff")
	public Stuff initModelAttr() {
		Stuff stuff = new Stuff();
		return stuff;
	}

	@RequestMapping("/regedit")
	public String index(){
		return "regedit";
	}
	
	/**
	 * @ModelAttribute 标注方法参数：说明了该方法参数的值将由model中取得。
	 *                 如果model中找不到，那么该参数会先被实例化，然后被添加到model中
	 *                 。在model中存在以后，请求中所有名称匹配的参数都会填充到该参数中。
	 * 
	 * 需要开启注解 <mvc:annotation-driven/>才能启用验证。否则@valid不管用
	 * 
	 * <pre>
	 * 1. 在入参对象前添加@Valid注解，同时在其后声明一个BindingResult的入参（必须紧随其后），
	 *    入参可以包含多个BindingResult参数；
	 * 2. 入参对象前添加@Valid注解：请求数据-->入参数据 ===>执行校验；
	 * 3. 这里@Valid在Stuff对象前声明，只会校验Stuff入参，不会校验其他入参;
	 * 4. 概括起来：@Valid与BindingResult必须成对出现，且他们之间不允许声明其他入参；
	 * @param bindingResult ：可判断是否存在错误
	 * </pre>
	 */
	@RequestMapping(value = "/validateTest")
	public String validTest(@Valid @ModelAttribute("stuff") Stuff stuff,
			BindingResult bindingResult) throws NoSuchAlgorithmException {
		// 输出信息
		System.out.println(stuff.getUsername() + "  " + stuff.getPasswd() + " "
				+ stuff.getRealName() + " " + "  " + stuff.getBirthday() + "  "
				+ stuff.getSalary());
		System.out.println("#### "+bindingResult.toString());

		// 判断校验结果
		if (bindingResult.hasErrors()) {
			System.out.println("数据校验有误");
			
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				System.out.println(fieldError.getField()
						+ fieldError.getDefaultMessage());
			}
			return "regedit";
		} else {
			System.out.println("数据校验都正确");
			return "success";
		}
	}
}










