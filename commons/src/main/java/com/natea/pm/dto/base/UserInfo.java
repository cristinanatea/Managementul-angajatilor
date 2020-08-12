package com.natea.pm.dto.base;

public class UserInfo {
	private Integer userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Integer roleId;
	private Integer contractId;
	private Integer availableSickDays;
	private Integer availablePaidDays;
	private Boolean isActive;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Integer getAvailableSickDays() {
		return availableSickDays;
	}

	public void setAvailableSickDays(Integer availableSickDays) {
		this.availableSickDays = availableSickDays;
	}

	public Integer getAvailablePaidDays() {
		return availablePaidDays;
	}

	public void setAvailablePaidDays(Integer availablePaidDays) {
		this.availablePaidDays = availablePaidDays;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", roleId=" + roleId + ", contractId=" + contractId
				+ ", availableSickDays=" + availableSickDays + ", availablePaidDays=" + availablePaidDays
				+ ", isActive=" + isActive + "]";
	}
}