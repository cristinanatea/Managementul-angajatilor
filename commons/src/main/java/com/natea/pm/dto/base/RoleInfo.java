package com.natea.pm.dto.base;

public class RoleInfo {
	private Integer roleId;
	private String roleName;
	private Boolean viewAllProjects;
	private Boolean manageProject;
	private Boolean manageUsers;
	private Boolean manageHolidays;
	private Boolean manageLeaveRequests;
	private Boolean fullAdministration;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Boolean getViewAllProjects() {
		return viewAllProjects;
	}

	public void setViewAllProjects(Boolean viewAllProjects) {
		this.viewAllProjects = viewAllProjects;
	}

	public Boolean getManageProject() {
		return manageProject;
	}

	public void setManageProject(Boolean manageProject) {
		this.manageProject = manageProject;
	}

	public Boolean getManageUsers() {
		return manageUsers;
	}

	public void setManageUsers(Boolean manageUsers) {
		this.manageUsers = manageUsers;
	}

	public Boolean getManageHolidays() {
		return manageHolidays;
	}

	public void setManageHolidays(Boolean manageHolidays) {
		this.manageHolidays = manageHolidays;
	}

	public Boolean getManageLeaveRequests() {
		return manageLeaveRequests;
	}

	public void setManageLeaveRequests(Boolean manageLeaveRequests) {
		this.manageLeaveRequests = manageLeaveRequests;
	}

	public Boolean getFullAdministration() {
		return fullAdministration;
	}

	public void setFullAdministration(Boolean fullAdministration) {
		this.fullAdministration = fullAdministration;
	}

	@Override
	public String toString() {
		return "RoleInfo [roleId=" + roleId + ", roleName=" + roleName + ", viewAllProjects=" + viewAllProjects
				+ ", manageProject=" + manageProject + ", mangeUsers=" + manageUsers + ", manageHolidays="
				+ manageHolidays + ", manageLeaveRequests=" + manageLeaveRequests + ", fullAdministration="
				+ fullAdministration + "]";
	}
}