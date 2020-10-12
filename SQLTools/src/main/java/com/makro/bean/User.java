package com.makro.bean;

import javax.validation.constraints.NotNull;

public class User {
	private int id;
	@NotNull(message = "用户名不允许为空")
	private String username;
	@NotNull(message = "密码不允许为空")
	private String password;
	
	public User(int id, @NotNull(message = "用户名不允许为空") String username, @NotNull(message = "密码不允许为空") String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	public User(@NotNull(message = "用户名不允许为空") String username, @NotNull(message = "密码不允许为空") String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User() {
		super();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
	}
}
