package com.ll.rpc;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;

public class Client {
	public static void main(String[] args) {
		// 远程Hessian服务
		String url = "http://localhost:8080/helloService";
		try {
			// 利用hessianProxyFactory.create()方法创建一个代理对象
			HelloService helloService = (HelloService) (new HessianProxyFactory()
					.create(HelloService.class, url));

			// 调用服务方法
			System.out.println(helloService.sayHello("sssppp"));
		} catch (MalformedURLException e) {

		}
	}
}
