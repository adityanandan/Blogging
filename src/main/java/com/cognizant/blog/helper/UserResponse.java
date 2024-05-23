package com.cognizant.blog.helper;

import com.cognizant.blog.models.User;

public class UserResponse {

	private User user;
	private String loginStatus;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
}
