package com.ll.jersey.getTest.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ll.jersey.getTest.model.Student;

/**
 * -----------------------------------------------------
 * 测试方式：
 * 方式1： 使用postman插件进行测试(Chrome浏览器的插件)
 * 方式2： 使用JerseyTest.java进行测试;
 * ----------------------------------------------------
 * tomcat部署Javaweb：
 * 配置tomcat的server.xml文件，设置Javaweb为默认启动的项目:
 * 在标签： <Host name="localhost"  appBase="webapps" xxx>下添加：
 * <Context path="" docBase="C:\xxx\Javaweb\" debug="0" reloadable="true"/>
 * -----------------------------------
 * @author linliu
 *
 */

@Path("/student")
public class JerseyServer {
	@Path("get/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Student status(@PathParam("name") String name) {
		System.out.println("get Student, name: "+name);
		Student student = new Student();
		student.setAge(25);
		student.setName(name);
		student.setSchool("SuZhou");
		return student;
	}
}

