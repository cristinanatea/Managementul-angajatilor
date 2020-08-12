package com.natea.pm.dto.base;

public class CredentialsInfo {
	int userId;
	String token;

	public CredentialsInfo() {
	}

	CredentialsInfo(int userId, String token) {
		this.userId = userId;
		this.token = token;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "CredentialsInfo [userId=" + userId + ", token=" + token + "]";
	}
}
