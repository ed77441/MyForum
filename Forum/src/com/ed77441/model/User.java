package com.ed77441.model;

public class User {
	private String username, birthday, email, phone, pfp, intro;

	private int password;
	
	public User () {}
	
	public User(String username, int password, String birthday, String email, String phone, String pfp, String intro) {
		this.username = username;
		this.password = password;
		this.birthday = birthday;
		this.email = email;
		this.phone = phone;
		this.pfp = pfp;
		this.intro = intro;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPfp() {
		return pfp;
	}

	public void setPfp(String pfp) {
		this.pfp = pfp;
	}
	
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
}
