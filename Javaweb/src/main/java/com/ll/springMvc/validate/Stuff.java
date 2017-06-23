package com.ll.springMvc.validate;

import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

public class Stuff {
	/**
	 * message: 指定国际化资源名称
	 */
	@Pattern(regexp = "W{4,30}", message = "{Pattern.stuff.username}")
	private String username;

	@Pattern(regexp = "S{6,30}", message = "{Pattern.stuff.passwd}")
	private String passwd;

	@Length(min = 2, max = 100, message = "{Pattern.stuff.realName}")
	private String realName;

	@Past(message = "{Pattern.stuff.birthday}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	@DecimalMin(value = "1000.00", message = "{DecimalMin.stuff.salary}")
	@DecimalMax(value = "2500.00", message = "{DecimalMax.stuff.salary}")
	@NumberFormat(pattern = "#,###.##")
	private long salary;

	public Stuff() {
		super();
	}

	public Stuff(String username, String passwd, String realName) {
		super();
		this.username = username;
		this.passwd = passwd;
		this.realName = realName;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Date getBirthday() {
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// return df.format(birthday);
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public long getSalary() {
		return salary;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Person [username=" + username + ", passwd=" + passwd + "]";
	}
}
