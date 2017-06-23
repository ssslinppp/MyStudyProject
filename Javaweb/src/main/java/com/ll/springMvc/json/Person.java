package com.ll.springMvc.json;

public class Person {
	private String username;
	private String passwd;
	private String realName;
	private double price;

	public Person() {
		super();
	}

	public Person(String username, String passwd) {
		super();
		this.username = username;
		this.passwd = passwd;
	}

	public Person(String username, String passwd, String realName, double price) {
		super();
		this.username = username;
		this.passwd = passwd;
		this.realName = realName;
		this.price = price;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}
