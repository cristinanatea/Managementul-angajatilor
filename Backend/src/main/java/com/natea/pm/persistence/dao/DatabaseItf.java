package com.natea.pm.persistence.dao;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import com.natea.pm.dto.base.CredentialsInfo;
import com.natea.pm.persistence.model.Contract;
import com.natea.pm.persistence.model.Holiday;
import com.natea.pm.persistence.model.LeaveRequest;
import com.natea.pm.persistence.model.LoginInfo;
import com.natea.pm.persistence.model.Project;
import com.natea.pm.persistence.model.Role;
import com.natea.pm.persistence.model.TimeSheet;
import com.natea.pm.persistence.model.User;

public interface DatabaseItf {
	public Role getAuthorization(CredentialsInfo credentials);

	public Role addRole(Role role);

	public Role updateRole(Role role);

	public Role getRoleByName(String Name);

	public Role getRoleById(int roleId);

	public boolean removeRole(Role role);

	public List<Role> getRoles();

	public Contract addContract(Contract contract);

	public Contract getContractById(int id);

	public Contract getContractByName(String Name);

	public boolean removeContract(Contract contract);

	public List<Contract> getContracts();

	public Project addProject(Project project);

	public Project updateProject(Project project);

	public boolean removeProject(int projectId);

	public Project getProjectById(int projectId);

	public List<Project> getProjects(Integer userId);

	public Holiday addHoliday(Date date, String name);

	public boolean removeHoliday(Date date, String name);

	public List<Holiday> getHolidays(Date dateStart, Date dateEnd);

	public User addUser(User user);

	public User updateUser(User user);

	public boolean removeUser(int userId);

	public User getUserById(int userId);

	public User getUserByEmail(String email, String password);

	public List<User> getUsers(Integer userId, Integer roleId, Integer contractId);

	public boolean assignUsersToProject(int projectId, Set<Integer> userIds);

	public boolean assignUserToProject(int userId, int projectId);

	public boolean removeUserFromProject(int userId, int projectId);

	public LeaveRequest addLeaveRequest(LeaveRequest lr);

	public LeaveRequest updateLeaveRequest(LeaveRequest lr);

	public boolean removeLeaveRequest(LeaveRequest lr);

	public LeaveRequest getLeaveRequesById(int id);

	public List<LeaveRequest> getLeaveRequests(Integer requestType, Integer userId, Integer projectId, Date dateStart,
			Date dateEnd, Boolean approved);

	public LoginInfo addLoginInfo(LoginInfo info);

	public LoginInfo updateLoginInfo(LoginInfo info);

	public LoginInfo getLoginInfoByToken(String token);

	public List<LoginInfo> getLoginInfoByUser(int userId);

	public boolean purgeLoginInfo(Date referenceDate);

	public boolean removeLoginInfo(int userId, String token);

	public TimeSheet addTimeSheet(TimeSheet ts);

	public boolean addTimeSheet(List<TimeSheet> ts);

	public TimeSheet updateTimeSheet(TimeSheet ts);

	public TimeSheet getTimeSheetById(int timeSheetId);

	public List<TimeSheet> getTimeSheets(Integer userId, Integer projectId, Date dateStart, Date dateEnd);

	public void truncateData();
}
