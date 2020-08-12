package com.natea.pm.persistence.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.natea.pm.dto.base.UserInfo;

@Entity
@Table(name = "Users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int userId;
	private String firstName;
	private String lastName;
	@Column(unique = true)
	private String email;
	private String password;
	private int roleId;
	private int contractId;
	private int availableSickDays;
	private int availablePaidDays;
	private boolean isActive;

	private double remainingTimeTillEndOfYear() {
		Calendar calendar1 = Calendar.getInstance();
		int currentDayOfYear = calendar1.get(Calendar.DAY_OF_YEAR);

		int year = calendar1.get(Calendar.YEAR);

		Calendar calendar2 = new GregorianCalendar(year, 11, 31);
		int totalDaysInYear = calendar2.get(Calendar.DAY_OF_YEAR);

		int daysRemaining = totalDaysInYear - currentDayOfYear;

		return (double) daysRemaining / totalDaysInYear;
	}

	public User() {
	}

	public User(String firstName, String lastName, String email, String password, Role role, Contract contract) {
		double getRemainingTimeRatio = remainingTimeTillEndOfYear();

		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.roleId = role.getRoleId();
		this.contractId = contract.getContractId();
		this.availableSickDays = contract.getYearlySickDays();
		this.availablePaidDays = (int) (getRemainingTimeRatio * contract.getYearlyPaidDays());

		this.isActive = true;
	}

	public User(UserInfo details) {
		this.firstName = details.getFirstName();
		this.lastName = details.getLastName();
		this.email = details.getEmail();
		this.password = details.getPassword();
		this.roleId = details.getRoleId();
		this.contractId = details.getContractId();
		this.availableSickDays = details.getAvailableSickDays();
		this.availablePaidDays = details.getAvailablePaidDays();

		this.isActive = true;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinTable(name = "Organization", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = {
			@JoinColumn(name = "projectId") })
	private Set<Project> projects = new HashSet<Project>();

	public Set<Project> getProjects() {
		return projects;
	}

	public void clearProjects() {
		projects.clear();
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<LeaveRequest> leaveRequests = new HashSet<LeaveRequest>();

	public Set<LeaveRequest> getLeaveRequests() {
		return leaveRequests;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<LoginInfo> currentLogins = new HashSet<LoginInfo>();

	public Set<LoginInfo> getCurrentLogins() {
		return currentLogins;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "contractId", insertable = false, updatable = false)
	private Contract contract;

	public Contract getContract() {
		return contract;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleId", insertable = false, updatable = false)
	private Role role;

	public Role getRole() {
		return role;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<TimeSheet> timesheets = new HashSet<TimeSheet>();

	public Set<TimeSheet> getTimesheets() {
		return timesheets;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
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

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getContractId() {
		return contractId;
	}

	public void setContractId(int contractId) {
		this.contractId = contractId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getAvailableSickDays() {
		return availableSickDays;
	}

	public void setAvailableSickDays(int availableSickDays) {
		this.availableSickDays = availableSickDays;
	}

	public int getAvailablePaidDays() {
		return availablePaidDays;
	}

	public void setAvailablePaidDays(int availablePaidDays) {
		this.availablePaidDays = availablePaidDays;
	}

	public UserInfo toDTO() {
		UserInfo userInfo = new UserInfo();

		userInfo.setFirstName(firstName);
		userInfo.setLastName(lastName);
		userInfo.setEmail(email);
		userInfo.setAvailablePaidDays(availablePaidDays);
		userInfo.setAvailableSickDays(availableSickDays);

		return userInfo;
	}
}