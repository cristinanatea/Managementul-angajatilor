package com.natea.pm.dto.base;

public class PassUpdateDetails {
	String oldPassword;
	String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "PassUpdateDetails [oldPassword=" + oldPassword + ", newPassword=" + newPassword + "]";
	}
}
