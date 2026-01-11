package com.healthcare.dto;

import com.healthcare.entity.Role;

public class LoginRequestDto {

    private String mobileNumber;
    private String password;
    public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	private Role role;

    // getters & setters
}
