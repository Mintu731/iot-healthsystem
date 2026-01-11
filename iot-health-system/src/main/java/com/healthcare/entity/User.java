package com.healthcare.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String mobileNumber;

    private String fullName;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Long tsChannelId;
    private String tsReadKey;

    private Long lastEntryId; // ThingSpeak pointer

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getTsReadKey() {
		return tsReadKey;
	}

	public void setTsReadKey(String tsReadKey) {
		this.tsReadKey = tsReadKey;
	}

	public Long getLastEntryId() {
		return lastEntryId;
	}

	public void setLastEntryId(Long lastEntryId) {
		this.lastEntryId = lastEntryId;
	}
}
