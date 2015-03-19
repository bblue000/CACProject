package com.rubick.cac.server;

public class MessageBean {

	private String name;
	private String mobile;
	private int age;
	private long birthday;
	
	public MessageBean() {
		
	}
	
	public MessageBean(String name) {
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getBirthday() {
		return birthday;
	}

	public void setBirthday(long birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "MessageBean [name=" + name + ", mobile=" + mobile + ", age="
				+ age + ", birthday=" + birthday + "]";
	}

}
