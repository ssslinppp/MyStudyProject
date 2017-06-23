package com.ll.jersey.getTest.rest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.ll.jersey.getTest.model.Student;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class JerseyTest {
	private Client client;

	/**
	 * 初始化Jersey客户端
	 */
	public void init() {
		if (client == null) {
			ClientConfig config = new DefaultClientConfig();
			config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
			client = Client.create(config);
			client.setConnectTimeout(3000);
		}
	}

	/**
	 * Http的Get请求;
	 * 对参数使用Json进行格式化;
	 */
	public Student get(String url, String path,
			MultivaluedMap<String, String> queryParams) {
		try {
			return queryParams == null ? client.resource(url).path(path)
					.type(MediaType.APPLICATION_JSON).get(Student.class)
					: client.resource(url).queryParams(queryParams).path(path)
							.type(MediaType.APPLICATION_JSON)
							.get(Student.class);
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		JerseyTest dao = new JerseyTest();
		dao.init();
		Student ret = dao.get("http://127.0.0.1:8080/",
				"jersey/rest/student/get/Tom", null);
		System.out.println("请求返回：");
		System.out.println(ret);
	}
}




















