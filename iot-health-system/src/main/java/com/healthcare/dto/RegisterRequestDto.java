package com.healthcare.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String mobileNumber;
    private String fullName;
    private String password;
    public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getTsChannelId() {
		return tsChannelId;
	}
	public void setTsChannelId(Long tsChannelId) {
		this.tsChannelId = tsChannelId;
	}
	public String getTsReadKey() {
		return tsReadKey;
	}
	public void setTsReadKey(String tsReadKey) {
		this.tsReadKey = tsReadKey;
	}
	private Long tsChannelId;
    private String tsReadKey;
}

