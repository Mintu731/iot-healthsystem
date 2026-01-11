package com.healthcare.dto;

import com.healthcare.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {

    private Long id;
    private String fullName;
    private String mobileNumber;
    private Role role;

    private Long tsChannelId;
    private Long lastEntryId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Long getTsChannelId() {
		return tsChannelId;
	}
	public void setTsChannelId(Long tsChannelId) {
		this.tsChannelId = tsChannelId;
	}
	public Long getLastEntryId() {
		return lastEntryId;
	}
	public void setLastEntryId(Long lastEntryId) {
		this.lastEntryId = lastEntryId;
	}
}
