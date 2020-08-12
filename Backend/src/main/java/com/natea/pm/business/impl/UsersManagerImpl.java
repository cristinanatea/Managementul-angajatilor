package com.natea.pm.business.impl;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.natea.pm.backend.Commons;
import com.natea.pm.backend.Commons.RequestStatus;
import com.natea.pm.business.UsersManagerItf;
import com.natea.pm.dto.base.AssignmentDetails;
import com.natea.pm.dto.base.ContractInfo;
import com.natea.pm.dto.base.CredentialsInfo;
import com.natea.pm.dto.base.GenericQuery;
import com.natea.pm.dto.base.HolidayInfo;
import com.natea.pm.dto.base.LeaveRequestInfo;
import com.natea.pm.dto.base.LoginDetails;
import com.natea.pm.dto.base.OrganizationDetails;
import com.natea.pm.dto.base.PassUpdateDetails;
import com.natea.pm.dto.base.ProjectInfo;
import com.natea.pm.dto.base.RoleInfo;
import com.natea.pm.dto.base.TimeSheetDetails;
import com.natea.pm.dto.base.TimeSheetInfo;
import com.natea.pm.dto.base.UserInfo;
import com.natea.pm.dto.request.Request;
import com.natea.pm.dto.response.Acknowledge;
import com.natea.pm.dto.response.Credentials;
import com.natea.pm.dto.response.Holidays;
import com.natea.pm.dto.response.Organization;
import com.natea.pm.dto.response.Projects;
import com.natea.pm.dto.response.Requests;
import com.natea.pm.dto.response.TimeSheets;
import com.natea.pm.dto.response.Users;
import com.natea.pm.persistence.dao.DatabaseItf;
import com.natea.pm.persistence.model.Contract;
import com.natea.pm.persistence.model.Holiday;
import com.natea.pm.persistence.model.LeaveRequest;
import com.natea.pm.persistence.model.LoginInfo;
import com.natea.pm.persistence.model.Project;
import com.natea.pm.persistence.model.Role;
import com.natea.pm.persistence.model.TimeSheet;
import com.natea.pm.persistence.model.User;

public class UsersManagerImpl implements UsersManagerItf {

	@SuppressWarnings("unused")
	private SessionFactory sessionFactory;
	private static final SecureRandom secureRandom = new SecureRandom();
	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
	private static final ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;

	}

	public static String generateNewToken() {
		byte[] randomBytes = new byte[24];
		secureRandom.nextBytes(randomBytes);
		return base64Encoder.encodeToString(randomBytes);
	}

	@Override
	public Credentials login(Request<LoginDetails> request) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");

		Credentials response = new Credentials();
		LoginDetails data = request.getData();

		User user = database.getUserByEmail(data.getEmail(), data.getPassword());

		if (user != null) {
			String token = generateNewToken();

			LoginInfo info = new LoginInfo();
			info.setToken(token);
			info.setUserId(user.getUserId());
			// TODO: info.setLastAction()

			info = database.addLoginInfo(info);

			CredentialsInfo credentials = new CredentialsInfo();
			credentials.setToken(token);
			credentials.setUserId(user.getUserId());

			response.setUser(user.toDTO());
			response.setCrededentials(credentials);
			response.setRights(user.getRole().toDTO());
		} else {
			response.setErrorMessage("Provided credentials not valid");
			response.setErrorCode(-1);
		}

		return response;
	}

	@Override
	public Acknowledge logout(Request<Integer> loginInfo) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		database.removeLoginInfo(loginInfo.getCredentials().getUserId(), loginInfo.getCredentials().getToken());
		// This function shall always return true in order to not disclose information
		// about the users

		return response;
	}

	@Override
	public Users getUsers(Request<GenericQuery> request) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");

		Users response = new Users();
		Role authorization = database.getAuthorization(request.getCredentials());

		if (authorization != null) {
			List<User> users;
			GenericQuery query = request.getData();

			users = database.getUsers(query.getUserId(), query.getRoleId(), query.getContractId());

			for (User user : users) {
				Set<Integer> projectIds = new HashSet<Integer>();
				response.addUser(user.getUserId(), user.toDTO());

				for (Project project : user.getProjects()) {
					if (!response.getProjects().containsKey(project.getProjectId())) {
						response.addProject(project.getProjectId(), project.toDTO());
					}

					projectIds.add(project.getProjectId());
				}

				response.addProjectsMapping(user.getUserId(), projectIds);
			}
		} else {
			response.setErrorCode(-1);
			response.setErrorMessage("Not authorized to view users!");
			;
		}

		return response;
	}

	@Override
	public TimeSheets getLoggedTime(Request<GenericQuery> request) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");

		GenericQuery query = request.getData();
		TimeSheets response = new TimeSheets();
		List<TimeSheet> timesheets = database.getTimeSheets(query.getUserId(), query.getProjectId(),
				query.getStartDate(), query.getEndDate());

		response.setStartDate(query.getStartDate());
		response.setEndDate(query.getEndDate());

		for (TimeSheet timesheet : timesheets) {

			if (!response.getUsers().containsKey(timesheet.getUserId())) {
				response.addUser(timesheet.getUserId(), timesheet.getUser().toDTO());
			}

			if (!response.getProjects().containsKey(timesheet.getProjectId())) {
				response.addProject(timesheet.getProjectId(), timesheet.getProject().toDTO());
			}

			response.addTimeSheet(timesheet.getEntryId(), timesheet.toDTO());
		}

		List<LeaveRequest> leaveRequests = database.getLeaveRequests(null, query.getUserId(), null,
				query.getStartDate(), query.getEndDate(), null);
		for (LeaveRequest leaveRequest : leaveRequests) {
			response.addLeaveRequest(leaveRequest.toDTO());
		}

		List<Holiday> legalFreeDays = database.getHolidays(query.getStartDate(), query.getEndDate());
		Set<HolidayInfo> freeDaysInfo = new HashSet<HolidayInfo>();

		for (Holiday day : legalFreeDays) {
			freeDaysInfo.add(day.toDTO());
		}

		response.setFreeDates(freeDaysInfo);

		return response;
	}

	@Override
	public Projects getProjects(Request<GenericQuery> request) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");

		GenericQuery query = request.getData();
		Projects response = new Projects();
		List<Project> projects;

		if (query.getProjectId() != null) {
			projects = new ArrayList<Project>();
			Project project = database.getProjectById(query.getProjectId());
			if (project != null) {
				projects.add(project);
			}
		} else {
			projects = database.getProjects(query.getUserId());
		}

		for (Project project : projects) {
			Set<Integer> userIds = new HashSet<Integer>();
			response.addProject(project.getProjectId(), project.toDTO());

			for (User user : project.getUsers()) {
				if (!response.getUsers().containsKey(user.getUserId())) {
					response.addUser(user.getUserId(), user.toDTO());
				}

				userIds.add(user.getUserId());
			}

			response.addUsersMapping(project.getProjectId(), userIds);
		}

		return response;
	}

	@Override
	public Requests getLeaveRequests(Request<GenericQuery> request) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");

		GenericQuery query = request.getData();
		Requests response = new Requests();
		List<com.natea.pm.persistence.model.LeaveRequest> leaveRequests = database.getLeaveRequests(
				query.getRequestTypeId(), query.getUserId(), query.getProjectId(), query.getStartDate(),
				query.getEndDate(), query.getStatus());

		for (com.natea.pm.persistence.model.LeaveRequest leaveRequest : leaveRequests) {
			response.addLeaveRequest(leaveRequest.getRequestId(), leaveRequest.toDTO());

			if (!response.getUsers().containsKey(leaveRequest.getUserId())) {
				response.addUser(leaveRequest.getUserId(), leaveRequest.getUser().toDTO());
			}
		}

		return response;
	}

	@Override
	public Acknowledge registerLeaveRequest(Request<LeaveRequestInfo> request) {

		DatabaseItf database = (DatabaseItf) context.getBean("database");

		Acknowledge response = new Acknowledge();

		LeaveRequest req = database.addLeaveRequest(new LeaveRequest(request.getData()));

		if (req == null) {
			response.setErrorCode(-1);
			response.setErrorMessage("Leave request could not be registered");
		}

		return response;
	}

	@Override
	public Acknowledge approveLeaveRequest(Request<Integer> request) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		com.natea.pm.persistence.model.LeaveRequest req = database.getLeaveRequesById(request.getData());

		if (req != null) {
			req.setStatus(RequestStatus.approved.getValue());
			req = database.updateLeaveRequest(req);
		} else {
			response.setErrorCode(-1);
			response.setErrorMessage("Request could not be approved");
		}

		return response;
	}

	@Override
	public Acknowledge rejectLeaveRequest(Request<Integer> request) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		com.natea.pm.persistence.model.LeaveRequest req = database.getLeaveRequesById(request.getData());

		if (req != null) {
			req.setStatus(RequestStatus.rejected.getValue());
			req = database.updateLeaveRequest(req);
		} else {
			response.setErrorCode(-1);
			response.setErrorMessage("Request does not exist");
		}

		return response;
	}

	@Override
	public Acknowledge cancelLeaveRequest(Request<Integer> request) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		com.natea.pm.persistence.model.LeaveRequest req = database.getLeaveRequesById(request.getData());

		if (req != null) {
			// Only the user that created the request may cancel it.
			if (req.getUserId() == request.getCredentials().getUserId()
					&& req.getStatus() == RequestStatus.pending.getValue()) {
				if (!database.removeLeaveRequest(req)) {
					response.setErrorCode(-1);
					response.setErrorMessage("Database error. Could not remove request.");
				}
			} else {
				response.setErrorCode(-1);
				response.setErrorMessage("Only pending requests may be deleted by the person that created them.");
			}
		} else {
			response.setErrorCode(-1);
			response.setErrorMessage("Request could not be approved");
		}

		return response;
	}

	@Override
	public Acknowledge registerTimeSheet(Request<TimeSheetDetails> request) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		List<TimeSheet> timesheets = new ArrayList<TimeSheet>();

		for (TimeSheetInfo te : request.getData().getTimeEntries()) {
			timesheets.add(new TimeSheet(request.getData().getUserId(), te));
		}

		boolean status = database.addTimeSheet(timesheets);

		if (!status) {
			response.setErrorCode(-1);
			response.setErrorMessage("Time could not be logged");
		}

		return response;
	}

	@Override
	public Acknowledge createProject(Request<ProjectInfo> details) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		Project project = database.addProject(new Project(details.getData()));

		if (project == null) {
			response.setErrorCode(-1);
			response.setErrorMessage("Project could not be added");
		} else {
			response.setId(project.getProjectId());
		}

		return response;
	}

	@Override
	public Acknowledge updateProject(Request<ProjectInfo> details) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		Project project = database.getProjectById(details.getData().getProjectId());

		if (project != null) {
			project.setDescription(details.getData().getDescription());
			project.setProjectName(details.getData().getProjectName());
			project = database.updateProject(project);
		}

		if (project == null) {
			response.setErrorCode(-1);
			response.setErrorMessage("Project could not be added");
		} else {
			response.setId(project.getProjectId());
		}

		return response;
	}

	@Override
	public Acknowledge createUser(Request<UserInfo> details) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		Contract contract = database.getContractById(details.getData().getContractId());

		if (contract == null) {
			response.setErrorCode(-1);
			response.setErrorMessage("Contract does not exist");
		} else {
			Role role = database.getRoleById(details.getData().getRoleId());
			if (role == null) {
				response.setErrorCode(-1);
				response.setErrorMessage("Role does not exist");
			} else {
				User user = database.addUser(new User(details.getData().getFirstName(), details.getData().getLastName(),
						details.getData().getEmail(), details.getData().getPassword(), role, contract));

				if (user == null) {
					response.setErrorCode(-1);
					response.setErrorMessage("User could not be added");
				} else {
					response.setId(user.getUserId());
				}
			}
		}

		return response;
	}

	@Override
	public Acknowledge updateUser(Request<UserInfo> details) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		User user = database.getUserById(details.getData().getUserId());

		if (user != null) {
			UserInfo updatedUser = details.getData();

			if (updatedUser.getContractId() != null) {
				user.setContractId(updatedUser.getContractId());
			}

			if (updatedUser.getAvailablePaidDays() != null) {
				user.setAvailablePaidDays(updatedUser.getAvailablePaidDays());
			}

			if (updatedUser.getAvailableSickDays() != null) {
				user.setAvailableSickDays(updatedUser.getAvailableSickDays());
			}

			if (updatedUser.getFirstName() != null) {
				user.setFirstName(updatedUser.getFirstName());
			}

			if (updatedUser.getLastName() != null) {
				user.setLastName(updatedUser.getLastName());
			}

			if (updatedUser.getRoleId() != null) {
				user.setRoleId(updatedUser.getRoleId());
			}

			if (updatedUser.getIsActive() != null) {
				user.setActive(updatedUser.getIsActive());
			}

			user = database.updateUser(user);

			if (user == null) {
				response.setErrorCode(-1);
				response.setErrorMessage("Update could not be performed");
			} else {
				response.setId(user.getUserId());
			}
		} else {
			response.setErrorCode(-1);
			response.setErrorMessage("User does not exist");
		}

		return response;
	}

	@Override
	public Acknowledge updateUserPassword(Request<PassUpdateDetails> details) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		User currentUser = database.getUserById(details.getCredentials().getUserId());

		if (currentUser != null && details.getData().getOldPassword().equals(currentUser.getPassword())) {
			currentUser.setPassword(details.getData().getNewPassword());
			currentUser = database.updateUser(currentUser);
		} else {
			response.setErrorCode(-1);
			response.setErrorMessage("Invalid credentials provided");
		}

		return response;
	}

	public Acknowledge resetUserPassword(Request<Integer> request) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		User currentUser = database.getUserById(request.getData());

		if (currentUser != null) {
			currentUser.setPassword(Commons.DEFAULT_PASSWORD);
			currentUser = database.updateUser(currentUser);
		} else {
			response.setErrorCode(-1);
			response.setErrorMessage("User does not exist!");
		}

		return response;
	}

	@Override
	public Acknowledge removeUser(Request<Integer> id) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		if (!database.removeUser(id.getData())) {
			response.setErrorCode(-1);
			response.setErrorMessage("User could not be removed");
		}

		return response;
	}

	@Override
	public Acknowledge removeProject(Request<Integer> id) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		if (!database.removeProject(id.getData())) {
			response.setErrorCode(-1);
			response.setErrorMessage("User could not be removed");
		}

		return response;
	}

	@Override
	public Acknowledge assignUserToProject(Request<OrganizationDetails> details) {

		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		if (!database.assignUserToProject(details.getData().getUserId(), details.getData().getProjectId())) {
			response.setErrorCode(-1);
			response.setErrorMessage("User could not be added to project");
		}

		return response;
	}

	@Override
	public Acknowledge removeUserFromProject(Request<OrganizationDetails> details) {

		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		if (!database.removeUserFromProject(details.getData().getUserId(), details.getData().getProjectId())) {
			response.setErrorCode(-1);
			response.setErrorMessage("User could not be removed from project");
		}

		return response;
	}

	@Override
	public Holidays getHolidays(Request<GenericQuery> request) {

		DatabaseItf database = (DatabaseItf) context.getBean("database");

		GenericQuery query = request.getData();
		Holidays response = new Holidays();
		List<Holiday> holidays = database.getHolidays(query.getStartDate(), query.getEndDate());

		for (Holiday holiday : holidays) {
			response.addHoliday(holiday.toDTO());
		}

		return response;
	}

	@Override
	public Acknowledge removeHoliday(Request<HolidayInfo> request) {

		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		if (!database.removeHoliday(request.getData().getDate(), request.getData().getName())) {
			response.setErrorCode(-1);
			response.setErrorMessage("Holiday could not be removed!");
		}

		return response;
	}

	@Override
	public Acknowledge addHoliday(Request<HolidayInfo> request) {

		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		if (database.addHoliday(request.getData().getDate(), request.getData().getName()) == null) {
			response.setErrorCode(-1);
			response.setErrorMessage("Holiday could not be added!");
		}

		return response;
	}

	@Override
	public Organization getOrganizationInfo(Request<Integer> request) {

		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Organization response = new Organization();

		List<Role> roles = database.getRoles();
		List<Contract> contracts = database.getContracts();

		for (Role role : roles) {
			response.addRole(role.toDTO());
		}

		for (Contract contract : contracts) {
			response.addContract(contract.toDTO());
		}

		return response;
	}

	@Override
	public Acknowledge addRole(Request<RoleInfo> roleInfo) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		Role role = database.addRole(new Role(roleInfo.getData()));

		if (role == null) {
			response.setErrorCode(-1);
			response.setErrorMessage("Could not add role to database.");
		}

		return response;
	}

	@Override
	public Acknowledge addContract(Request<ContractInfo> contractInfo) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		Contract contract = database.addContract(new Contract(contractInfo.getData()));

		if (contract == null) {
			response.setErrorCode(-1);
			response.setErrorMessage("Could not add role to database.");
		}

		return response;
	}

	@Override
	public Acknowledge removeRole(Request<Integer> request) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		Role role = database.getRoleById(request.getData());

		if (role == null) {
			response.setErrorCode(-1);
			response.setErrorMessage("Could not find role in database.");
		} else {
			if (!database.removeRole(role)) {
				response.setErrorCode(-1);
				response.setErrorMessage("Could not remove role from database.");
			}
		}

		return response;
	}

	@Override
	public Acknowledge removeContract(Request<Integer> request) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		Contract contract = database.getContractById(request.getData());

		if (contract == null) {
			response.setErrorCode(-1);
			response.setErrorMessage("Could not find contract in database.");
		} else {
			if (!database.removeContract(contract)) {
				response.setErrorCode(-1);
				response.setErrorMessage("Could not remove contract from database.");
			}
		}

		return response;
	}

	@Override
	public Acknowledge assignUsersToProject(Request<AssignmentDetails> request) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		boolean status = database.assignUsersToProject(request.getData().getId(), request.getData().getReferenceIds());

		if (!status) {
			response.setErrorCode(-1);
			response.setErrorMessage("Could not update users");
		}

		return response;
	}

	@Override
	public Acknowledge assignProjectsToUser(Request<AssignmentDetails> request) {
		DatabaseItf database = (DatabaseItf) context.getBean("database");
		Acknowledge response = new Acknowledge();

		User user = database.getUserById(request.getData().getId());
		user.clearProjects();

		if (user != null) {
			for (Integer projectId : request.getData().getReferenceIds()) {
				Project project = database.getProjectById(projectId);

				if (project != null) {
					user.getProjects().add(project);
				}
			}

			user = database.updateUser(user);
		}
		if (user == null) {
			response.setErrorCode(-1);
			response.setErrorMessage("Could not update projects");
		}

		return response;
	}
}
