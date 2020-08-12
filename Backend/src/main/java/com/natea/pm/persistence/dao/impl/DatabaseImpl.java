package com.natea.pm.persistence.dao.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.natea.pm.dto.base.CredentialsInfo;
import com.natea.pm.persistence.dao.DatabaseItf;
import com.natea.pm.persistence.model.Contract;
import com.natea.pm.persistence.model.Holiday;
import com.natea.pm.persistence.model.LeaveRequest;
import com.natea.pm.persistence.model.LoginInfo;
import com.natea.pm.persistence.model.Project;
import com.natea.pm.persistence.model.Role;
import com.natea.pm.persistence.model.TimeSheet;
import com.natea.pm.persistence.model.User;

public class DatabaseImpl implements DatabaseItf {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public void truncateData() {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		session.createSQLQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();
		@SuppressWarnings("unchecked")
		List<String> tables = session
				.createSQLQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = SCHEMA()")
				.list();

		for (String table : tables) {
			session.createSQLQuery("TRUNCATE TABLE " + table).executeUpdate();
		}

		session.createSQLQuery("SET FOREIGN_KEY_CHECKS=1").executeUpdate();
		tx1.commit();
	}

	/**********************************************************************/
	/****************************** ADD ***********************************/
	/**********************************************************************/

	@Override
	public User addUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		try {
			session.save(user);
			tx1.commit();
		} catch (ConstraintViolationException e) {
			user = null;
			tx1.rollback();
		}

		return user;
	}

	@Override
	public Role addRole(Role role) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		try {
			session.save(role);
			tx1.commit();
		} catch (ConstraintViolationException e) {
			role = null;
			tx1.rollback();
		}

		return role;
	}

	@Override
	public Contract addContract(Contract contract) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		try {
			session.save(contract);
			tx1.commit();
		} catch (ConstraintViolationException e) {
			contract = null;
			tx1.rollback();
		}

		return contract;
	}

	@Override
	public Project addProject(Project project) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		try {
			session.save(project);
			tx1.commit();
		} catch (ConstraintViolationException e) {
			project = null;
			tx1.rollback();
		}

		return project;
	}

	@Override
	public Holiday addHoliday(Date date, String name) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		Holiday holiday = new Holiday();
		holiday.setDate(date);
		holiday.setName(name);

		try {
			session.save(holiday);
			tx1.commit();
		} catch (ConstraintViolationException e) {
			holiday = null;
			tx1.rollback();
		}

		return holiday;
	}

	@Override
	public LeaveRequest addLeaveRequest(LeaveRequest lr) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		try {
			session.save(lr);
			tx1.commit();
		} catch (ConstraintViolationException e) {
			lr = null;
			tx1.rollback();
		}

		return lr;
	}

	@Override
	public LoginInfo addLoginInfo(LoginInfo li) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		try {
			session.save(li);
			tx1.commit();
		} catch (ConstraintViolationException e) {
			li = null;
			tx1.rollback();
		}

		return li;
	}

	@Override
	public TimeSheet addTimeSheet(TimeSheet ts) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		try {
			session.save(ts);
			tx1.commit();
		} catch (ConstraintViolationException e) {
			ts = null;
			tx1.rollback();
		}

		return ts;
	}

	public boolean addTimeSheet(List<TimeSheet> timesheets) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		try {
			for (TimeSheet ts : timesheets) {
				session.save(ts);
			}
			tx1.commit();
			return true;
		} catch (ConstraintViolationException e) {
			tx1.rollback();
		}

		return false;
	}

	@Override
	public boolean assignUserToProject(int userId, int projectId) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		User user = (User) session.get(User.class, userId);
		if (null != user) {
			Project project = (Project) session.get(Project.class, projectId);

			if (null != project) {
				user.getProjects().add(project);
				tx1.commit();
				return true;
			} else {
				tx1.rollback();
			}
		} else {
			tx1.rollback();
		}

		return false;
	}

	@Override
	public boolean assignUsersToProject(int projectId, Set<Integer> userIds) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();

		try {
			Project project = (Project) session.get(Project.class, projectId);
			if (project != null) {
				Set<User> users = project.getUsers();

				Set<Integer> toKeep = new HashSet<Integer>();

				for (User user : users) {
					if (userIds.contains(user.getUserId())) {
						toKeep.add(user.getUserId());
					} else {
						user.getProjects().remove(project);
						session.update(user);
					}
				}

				for (Integer userId : userIds) {
					if (!toKeep.contains(userId)) {
						User user = (User) session.get(User.class, userId);
						if (user != null) {
							user.getProjects().add(project);
							session.update(user);
						}
					}
				}
			} else {
				tx1.commit();
				return false;
			}

			tx1.commit();
			return true;
		} catch (ConstraintViolationException e) {
			tx1.rollback();
			return false;
		}
	}

	/**********************************************************************/
	/**************************** UPDATE **********************************/
	/**********************************************************************/

	@Override
	public User updateUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		try {
			session.update(user);
			tx1.commit();
		} catch (ConstraintViolationException e) {
			user = null;
			tx1.rollback();
		}

		return user;
	}

	@Override
	public Role updateRole(Role role) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		try {
			session.update(role);
			tx1.commit();
		} catch (ConstraintViolationException e) {
			role = null;
			tx1.rollback();
		}

		return role;
	}

	@Override
	public Project updateProject(Project project) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		try {
			session.update(project);
			tx1.commit();
		} catch (ConstraintViolationException e) {
			project = null;
			tx1.rollback();
		}

		return project;
	}

	@Override
	public LeaveRequest updateLeaveRequest(LeaveRequest lr) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		try {
			session.update(lr);
			tx1.commit();
		} catch (ConstraintViolationException e) {
			lr = null;
			tx1.rollback();
		}

		return lr;
	}

	@Override
	public LoginInfo updateLoginInfo(LoginInfo info) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		try {
			session.update(info);
			tx1.commit();
		} catch (ConstraintViolationException e) {
			info = null;
			tx1.rollback();
		}

		return info;
	}

	@Override
	public TimeSheet updateTimeSheet(TimeSheet ts) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		try {
			session.update(ts);
			tx1.commit();
		} catch (ConstraintViolationException e) {
			ts = null;
			tx1.rollback();
		}

		return ts;
	}

	/**********************************************************************/
	/**************************** REMOVE **********************************/
	/**********************************************************************/

	@Override
	public boolean removeProject(int projectId) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		try {
			Query query = (Query) session.createQuery("delete Project where projectId = :projectId");
			query.setParameter("projectId", projectId);
			query.executeUpdate();
			tx1.commit();
		} catch (ConstraintViolationException e) {
			tx1.rollback();
			return false;
		}

		return true;
	}

	@Override
	public boolean removeUser(int userId) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		try {
			// Delete any active tokens
			Query query = (Query) session.createQuery("delete LoginInfo where userId = :userId");
			query.setParameter("userId", userId);
			query.executeUpdate();
			// Delete the user
			query = (Query) session.createQuery("delete User where userId = :userId");
			query.setParameter("userId", userId);
			query.executeUpdate();
			tx1.commit();
		} catch (ConstraintViolationException e) {
			tx1.rollback();
			return false;
		}

		return true;
	}

	@Override
	public boolean removeHoliday(Date date, String name) {
		if (null != date) {
			Session session = this.sessionFactory.getCurrentSession();
			Transaction tx1 = session.beginTransaction();
			Query query = (Query) session.createQuery("delete Holiday where date = :date and name = :name");
			query.setParameter("date", date);
			query.setParameter("name", name);
			query.executeUpdate();
			tx1.commit();

			return true;
		}
		return false;
	}

	@Override
	public boolean removeLeaveRequest(LeaveRequest lr) {
		if (null != lr) {
			Session session = this.sessionFactory.getCurrentSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(lr);
			tx1.commit();

			return true;
		}
		return false;
	}

	@Override
	public boolean removeContract(Contract contract) {
		if (null != contract) {
			Session session = this.sessionFactory.getCurrentSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(contract);
			tx1.commit();

			return true;
		}
		return false;
	}

	@Override
	public boolean removeRole(Role role) {
		if (null != role) {
			Session session = this.sessionFactory.getCurrentSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(role);
			tx1.commit();

			return true;
		}
		return false;
	}

	@Override
	public boolean removeUserFromProject(int userId, int projectId) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		User user = (User) session.get(User.class, userId);
		if (null != user) {
			for (Project item : user.getProjects())
				if (projectId == item.getProjectId()) {
					user.getProjects().remove(item);
					break;
				}

			tx1.commit();
			return true;
		}
		return false;
	}

	@Override
	public boolean purgeLoginInfo(Date referenceDate) {
		if (null != referenceDate) {
			Session session = this.sessionFactory.getCurrentSession();
			Transaction tx1 = session.beginTransaction();

			String hqlQuery = "delete LoginInfo";

			if (referenceDate != null) {
				hqlQuery += " where lastAction = :lastAction";
			}

			Query query = (Query) session.createQuery(hqlQuery);

			if (referenceDate != null) {
				query.setParameter("lastAction", referenceDate);
			}

			query.executeUpdate();

			tx1.commit();

			return true;
		}
		return false;
	}

	public boolean removeLoginInfo(int userId, String token) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();

		Query query = (Query) session.createQuery("delete LoginInfo where userId = :userId and token = :token");
		query.setParameter("userId", userId);
		query.setParameter("token", token);
		query.executeUpdate();

		tx1.commit();

		return true;
	}

	/**********************************************************************/
	/****************************** GET ***********************************/
	/**********************************************************************/

	@Override
	public User getUserById(int userId) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();

		User user = (User) session.get(User.class, userId);
		tx1.commit();

		return user;
	}

	@Override
	public User getUserByEmail(String email, String password) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();

		String hqlQuery = "from User WHERE email = :email";

		if (password != null) {
			hqlQuery += " and password = :password";
		}

		Query query = (Query) session.createQuery(hqlQuery);
		query.setParameter("email", email);

		if (password != null) {
			query.setParameter("password", password);
		}

		User user = (User) query.uniqueResult();
		tx1.commit();

		return user;
	}

	@Override
	public List<User> getUsers(Integer userId, Integer roleId, Integer contractId) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();

		int whereClauses = 0;
		String hqlQuery = "from User";

		if (userId != null) {
			hqlQuery += ((whereClauses++ == 0) ? " where " : " and ") + "userId = :userId";
		}

		if (roleId != null) {
			hqlQuery += ((whereClauses++ == 0) ? " where " : " and ") + "roleId = :roleId";
		}

		if (contractId != null) {
			hqlQuery += ((whereClauses++ == 0) ? " where " : " and ") + "contractId = :contractId";
		}

		Query query = (Query) session.createQuery(hqlQuery);

		if (userId != null) {
			query.setParameter("userId", userId);
		}

		if (roleId != null) {
			query.setParameter("roleId", roleId);
		}

		if (contractId != null) {
			query.setParameter("contractId", contractId);
		}

		@SuppressWarnings("unchecked")
		List<User> users = query.list();
		tx1.commit();

		return users;
	}

	@Override
	public List<Role> getRoles() {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();

		@SuppressWarnings("unchecked")
		List<Role> roles = session.createQuery("from Role").list();
		tx1.commit();

		return roles;
	}

	@Override
	public Role getRoleById(int roleId) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();

		Role role = (Role) session.get(Role.class, roleId);
		tx1.commit();

		return role;
	}

	@Override
	public Role getRoleByName(String roleName) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();

		Query query = (Query) session.createQuery("from Role WHERE roleName = :roleName");
		query.setParameter("roleName", roleName);

		Role role = (Role) query.uniqueResult();
		tx1.commit();

		return role;
	}

	@Override
	public Contract getContractById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();

		Contract contract = (Contract) session.get(Contract.class, id);
		tx1.commit();

		return contract;
	}

	@Override
	public Contract getContractByName(String contractName) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();

		Query query = (Query) session.createQuery("from Contract WHERE contractName = :contractName");
		query.setParameter("contractName", contractName);

		Contract contract = (Contract) query.uniqueResult();
		tx1.commit();

		return contract;
	}

	@Override
	public List<Contract> getContracts() {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();

		@SuppressWarnings("unchecked")
		List<Contract> contracts = session.createQuery("from Contract").list();
		tx1.commit();

		return contracts;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Project> getProjects(Integer userId) {
		List<Project> resultSet;

		if (userId == null) {
			Session session = this.sessionFactory.getCurrentSession();
			Transaction tx1 = session.beginTransaction();

			Query query = (Query) session.createQuery("from Project");

			resultSet = query.list();
			tx1.commit();
		} else {
			resultSet = new ArrayList<Project>();

			User user = getUserById(userId);

			if (user != null) {
				resultSet.addAll(user.getProjects());
			}
		}

		return resultSet;
	}

	@Override
	public Project getProjectById(int projectId) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();

		Project project = (Project) session.get(Project.class, projectId);
		tx1.commit();

		return project;
	}

	@Override
	public List<Holiday> getHolidays(Date dateStart, Date dateEnd) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();

		int whereClauses = 0;
		String hqlQuery = "from Holiday";

		if (dateStart != null) {
			hqlQuery += ((whereClauses++ == 0) ? " where " : " and ") + "date >= :dateStart";
		}

		if (dateEnd != null) {
			hqlQuery += ((whereClauses++ == 0) ? " where " : " and ") + "date < :dateEnd";
		}

		Query query = (Query) session.createQuery(hqlQuery);

		if (dateStart != null) {
			query.setParameter("dateStart", dateStart);
		}

		if (dateEnd != null) {
			query.setParameter("dateEnd", dateEnd);
		}

		@SuppressWarnings("unchecked")
		List<Holiday> freeDays = query.list();
		tx1.commit();

		return freeDays;
	}

	@Override
	public LoginInfo getLoginInfoByToken(String token) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();

		Query query = (Query) session.createQuery("from LoginInfo WHERE token = :token");
		query.setParameter("token", token);

		LoginInfo loginInfo = (LoginInfo) query.uniqueResult();
		tx1.commit();

		return loginInfo;
	}

	@Override
	public List<LoginInfo> getLoginInfoByUser(int userId) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();

		Query query = (Query) session.createQuery("from LoginInfo WHERE userId = :userId");
		query.setParameter("userId", userId);

		@SuppressWarnings("unchecked")
		List<LoginInfo> loginInfo = query.list();
		tx1.commit();

		return loginInfo;
	}

	@Override
	public TimeSheet getTimeSheetById(int timeSheetId) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();

		TimeSheet timeSheet = (TimeSheet) session.get(TimeSheet.class, timeSheetId);
		tx1.commit();

		return timeSheet;
	}

	@Override
	public List<TimeSheet> getTimeSheets(Integer userId, Integer projectId, Date dateStart, Date dateEnd) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();

		int whereClauses = 0;
		String hqlQuery = "from TimeSheet";

		if (userId != null) {
			hqlQuery += ((whereClauses++ == 0) ? " where " : " and ") + "userId = :userId";
		}

		if (projectId != null) {
			hqlQuery += ((whereClauses++ == 0) ? " where " : " and ") + "projectId = :projectId";
		}

		if (dateStart != null) {
			hqlQuery += ((whereClauses++ == 0) ? " where " : " and ") + "dateStart = :dateStart";
		}

		if (dateEnd != null) {
			hqlQuery += ((whereClauses++ == 0) ? " where " : " and ") + "dateEnd = :dateEnd";
		}

		Query query = (Query) session.createQuery(hqlQuery);

		if (userId != null) {
			query.setParameter("userId", userId);
		}

		if (projectId != null) {
			query.setParameter("projectId", projectId);
		}

		if (dateStart != null) {
			query.setParameter("dateStart", dateStart);
		}

		if (dateEnd != null) {
			query.setParameter("dateEnd", dateEnd);
		}

		@SuppressWarnings("unchecked")
		List<TimeSheet> timeSheets = query.list();
		tx1.commit();

		return timeSheets;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LeaveRequest> getLeaveRequests(Integer requestType, Integer userId, Integer projectId, Date dateStart,
			Date dateEnd, Boolean status) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();

		List<LeaveRequest> leaveRequests;

		try {
			int whereClauses = 0;
			String hqlQuery = "from LeaveRequest";

			if (requestType != null) {
				hqlQuery += ((whereClauses++ == 0) ? " where " : " and ") + "requestType = :requestType";
			}

			if (userId != null) {
				hqlQuery += ((whereClauses++ == 0) ? " where " : " and ") + "userId = :userId";
			}

			if (dateStart != null) {
				hqlQuery += ((whereClauses++ == 0) ? " where " : " and ") + "dateStart = :dateStart";
			}

			if (dateEnd != null) {
				hqlQuery += ((whereClauses++ == 0) ? " where " : " and ") + "dateEnd = :dateEnd";
			}

			if (status != null) {
				hqlQuery += ((whereClauses++ == 0) ? " where " : " and ") + "status = :status";
			}

			Query query = (Query) session.createQuery(hqlQuery);

			if (requestType != null) {
				query.setParameter("requestType", requestType);
			}

			if (userId != null) {
				query.setParameter("userId", userId);
			}

			if (dateStart != null) {
				query.setParameter("dateStart", dateStart);
			}

			if (dateEnd != null) {
				query.setParameter("dateEnd", dateEnd);
			}

			if (status != null) {
				query.setParameter("status", status);
			}

			leaveRequests = query.list();
			tx1.commit();
		} catch (Exception e) {
			leaveRequests = new ArrayList<LeaveRequest>();
			tx1.rollback();
		}

		return leaveRequests;
	}

	@Override
	public LeaveRequest getLeaveRequesById(int requestId) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();

		LeaveRequest leaveRequest = (LeaveRequest) session.get(LeaveRequest.class, requestId);
		tx1.commit();

		return leaveRequest;
	}

	@Override
	public Role getAuthorization(CredentialsInfo credentials) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		Role role = null;

		Query query = (Query) session.createQuery("from LoginInfo WHERE userId = :userId and token = :token");
		query.setParameter("userId", credentials.getUserId());
		query.setParameter("token", credentials.getToken());

		LoginInfo loginInfo = (LoginInfo) query.uniqueResult();

		if (loginInfo != null) {
			role = loginInfo.getUser().getRole();
		}
		tx1.commit();

		return role;
	}
}
