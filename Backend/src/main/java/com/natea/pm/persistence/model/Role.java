package com.natea.pm.persistence.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.natea.pm.dto.base.RoleInfo;

@Entity
@Table(name = "Roles")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int roleId;
	@Column(unique = true)
	private String roleName;
	private boolean viewAllProjects;
	private boolean manageProject;
	private boolean manageUsers;
	private boolean manageHolidays;
	private boolean manageLeaveRequests;
	private boolean fullAdministration;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleId")
	private Set<User> users = new HashSet<User>();

	public Role() {
	}

	public Role(String roleName) {
		this.roleName = roleName;
		this.setFullAdministration(false);
		this.setManageHolidays(false);
		this.setManageLeaveRequests(false);
		this.setManageProject(false);
		this.setManageUsers(false);
		this.setViewAllProjects(false);
	}

	public Role(RoleInfo roleInfo) {
		setRoleName(roleInfo.getRoleName());
		setManageHolidays(roleInfo.getManageHolidays());
		setManageLeaveRequests(roleInfo.getManageLeaveRequests());
		setManageProject(roleInfo.getManageProject());
		setManageUsers(roleInfo.getManageUsers());
		setViewAllProjects(roleInfo.getViewAllProjects());
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isViewAllProjects() {
		return viewAllProjects;
	}

	public void setViewAllProjects(boolean viewAllProjects) {
		this.viewAllProjects = viewAllProjects;
	}

	public boolean isManageProject() {
		return manageProject;
	}

	public void setManageProject(boolean manageProject) {
		this.manageProject = manageProject;
	}

	public boolean isManageUsers() {
		return manageUsers;
	}

	public void setManageUsers(boolean manageUsers) {
		this.manageUsers = manageUsers;
	}

	public boolean isManageHolidays() {
		return manageHolidays;
	}

	public void setManageHolidays(boolean manageHolidays) {
		this.manageHolidays = manageHolidays;
	}

	public boolean isManageLeaveRequests() {
		return manageLeaveRequests;
	}

	public void setManageLeaveRequests(boolean manageLeaveRequests) {
		this.manageLeaveRequests = manageLeaveRequests;
	}

	public boolean isFullAdministration() {
		return fullAdministration;
	}

	public void setFullAdministration(boolean fullAdministration) {
		this.fullAdministration = fullAdministration;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public RoleInfo toDTO() {
		RoleInfo info = new RoleInfo();

		info.setRoleId(roleId);
		info.setRoleName(roleName);
		info.setFullAdministration(fullAdministration);
		info.setManageHolidays(manageHolidays);
		info.setManageLeaveRequests(manageLeaveRequests);
		info.setManageProject(manageProject);
		info.setManageUsers(manageUsers);
		info.setViewAllProjects(viewAllProjects);

		return info;
	}
}