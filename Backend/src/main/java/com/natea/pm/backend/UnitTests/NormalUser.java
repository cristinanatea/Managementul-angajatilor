/**
 * 
 */
package com.natea.pm.backend.UnitTests;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.fail;
//
//import java.io.IOException;
//import java.sql.Date;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//
//import org.codehaus.jackson.JsonGenerationException;
//import org.codehaus.jackson.JsonParseException;
//import org.codehaus.jackson.map.JsonMappingException;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.type.TypeReference;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.natea.pm.controller.OrganizationController;
//import com.natea.pm.controller.ProjectsController;
//import com.natea.pm.controller.RequestsController;
//import com.natea.pm.controller.TimeSheetsController;
//import com.natea.pm.controller.UsersController;
//import com.natea.pm.dto.base.CredentialsInfo;
//import com.natea.pm.dto.base.GenericQuery;
//import com.natea.pm.dto.base.LeaveRequestInfo;
//import com.natea.pm.dto.base.LoginDetails;
//import com.natea.pm.dto.base.OrganizationDetails;
//import com.natea.pm.dto.base.ProjectInfo;
//import com.natea.pm.dto.base.TimeSheetDetails;
//import com.natea.pm.dto.base.TimeSheetInfo;
//import com.natea.pm.dto.base.UserInfo;
//import com.natea.pm.dto.request.Request;
//import com.natea.pm.dto.response.Acknowledge;
//import com.natea.pm.dto.response.Credentials;
//import com.natea.pm.dto.response.Projects;
//import com.natea.pm.dto.response.Requests;
//import com.natea.pm.dto.response.TimeSheets;
//import com.natea.pm.dto.response.Users;
//import com.natea.pm.persistence.dao.DatabaseItf;
//import com.natea.pm.persistence.model.Contract;
//import com.natea.pm.persistence.model.Project;
//import com.natea.pm.persistence.model.Role;
//import com.natea.pm.persistence.model.User;

/**
 * @author dasbi
 *
 */
class NormalUser {
//	final static String VALID_EMAIL = "bart@olo.meu";
//	final static String VALID_EMAIL2 = "bart2@olo.meu";
//	final static String VALID_PASS = "pass";
//	final static String VALID_PASS2 = "pass2";
//	final static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//
//	LoginDetails validUserDetails = new LoginDetails(VALID_EMAIL, VALID_PASS);
//	LoginDetails invalidUserDetails = new LoginDetails(VALID_PASS, VALID_EMAIL);
//	ObjectMapper mapper = new ObjectMapper();
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@BeforeAll
//	static void setUpBeforeClass() throws Exception {
//		DatabaseItf database = (DatabaseItf) context.getBean("database");
//
//		// Clean up database contents
//		database.truncateData();
//
//		database.addHoliday(strToDate("2020-04-04"), "Super sarbatoare");
//		database.addHoliday(strToDate("2020-04-17"), "Vinerea mare");
//		database.addHoliday(strToDate("2020-04-18"), "Pasti");
//		database.addHoliday(strToDate("2020-04-29"), "Pasti");
//		database.addHoliday(strToDate("2020-04-20"), "Pasti");
//
//		Contract contract = database.getContractByName("Normal");
//
//		if (contract == null) {
//			contract = database.addContract(new Contract("Normal", 8, 30, 21));
//
//			assertNotNull(contract);
//		}
//
//		Role roleUser = database.getRoleByName("user");
//		Role roleManager = database.getRoleByName("supervisor");
//
//		if (roleUser == null) {
//			roleUser = database.addRole(new Role("user"));
//
//			assertNotNull(roleUser);
//		}
//
//		if (roleManager == null) {
//			roleManager = database.addRole(new Role("supervisor"));
//			roleManager = database.updateRole(roleManager);
//
//			assertNotNull(roleManager);
//		}
//
//		User first = database.addUser(new User("Ion", "Bartolomeu", VALID_EMAIL, VALID_PASS, roleUser, contract));
//		@SuppressWarnings("unused")
//		User second = database
//				.addUser(new User("Ginger", "Alternativ", VALID_EMAIL2, VALID_PASS2, roleManager, contract));
//		database.addUser(new User("1", "1", "1", "1", roleManager, contract));
//		database.addUser(new User("2", "2", "2", "2", roleManager, contract));
//		database.addUser(new User("3", "3", "3", "3", roleManager, contract));
//		database.addUser(new User("4", "4", "4", "4", roleManager, contract));
//		database.addProject(new Project("1", "1", first));
//		database.addProject(new Project("2", "2", first));
//		database.addProject(new Project("3", "3", first));
//		database.addProject(new Project("4", "4", first));
//		database.addProject(new Project("5", "5", first));
//
//		Project project = database.addProject(new Project("Proiect1", "O descriere", first));
//		assertNotNull(project);
//
//		// check that we can't add a project with the same name
//		Project project2 = database.addProject(new Project("Proiect1", "O descriere", first));
//		assertNull(project2);
//
//		first.getProjects().add(project);
//		User copyUser = database.updateUser(first);
//
//		assertEquals(first, copyUser);
//
//		assertNull(copyUser.getContract());
//
//		first = database.getUserByEmail(VALID_EMAIL, null);
//		contract = database.getContractByName("Normal");
//		assertEquals(6, contract.getUsers().size());
//		assertEquals(8, first.getContract().getDailyNorm());
//
//		roleUser = database.getRoleByName("user");
//		assertEquals(1, roleUser.getUsers().size());
//
//		assertEquals(0, first.getLeaveRequests().size());
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@AfterAll
//	static void tearDownAfterClass() throws Exception {
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@BeforeEach
//	void setUp() throws Exception {
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@AfterEach
//	void tearDown() throws Exception {
//	}
//
//	private static Date strToDate(String str) {
//		java.sql.Date date = null;
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		java.util.Date parsed = null;
//		try {
//			parsed = sdf.parse(str);
//			date = new java.sql.Date(parsed.getTime());
//		} catch (ParseException e1) {
//
//		}
//
//		return date;
//	}
//
//	@Test
//	void validUserLogin() {
//		try {
//			String json = mapper.writeValueAsString(validUserDetails);
//
//			UsersController controller = new UsersController();
//			Credentials info = controller.login(json);
//
//			assertEquals(0, info.getErrorCode());
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail("Exception thrown while trying to login: " + e.getMessage());
//		}
//	}
//
//	@Test
//	void invalidUserLogin() {
//		try {
//			String json = mapper.writeValueAsString(invalidUserDetails);
//
//			UsersController controller = new UsersController();
//			Credentials info = controller.login(json);
//
//			assertNotEquals(0, info.getErrorCode());
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail("Exception thrown while trying to login: " + e.getMessage());
//		}
//	}
//
//	@Test
//	void testreflexion() {
//		Ob1 o = new Ob1(1, "sss");
//		Ob2 m = new Ob2(1.35);
//
//		Ob<Ob2> n = new Ob<Ob2>();
//		n.setData(m);
//		n.setNum(3);
//
//		String json = "";
//		try {
//			json = mapper.writeValueAsString(n);
//		} catch (JsonGenerationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(json);
//
//		try {
//
//			System.out.println(json);
//			Ob<Ob2> org = mapper.readValue(json, new TypeReference<Ob<Ob2>>() {
//			});
//
//			System.out.println(org);
//
//		} catch (JsonParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	@Test
//	void assignuserstopj() {
//		// @Path("/users")
//		try {
//			CredentialsInfo credentials = new CredentialsInfo();
//			UsersController controller = new UsersController();
//			ProjectsController controller2 = new ProjectsController();
//			// Login
//			{
//				String json = mapper.writeValueAsString(validUserDetails);
//
//				Credentials info = controller.login(json);
//				credentials.setToken(info.getCrededentials().getToken());
//				credentials.setUserId(info.getCrededentials().getUserId());
//			}
//			/*
//			 * AssignmentDetails ad = new AssignmentDetails();
//			 * ad.setCredentials(credentials);
//			 * 
//			 * ad.setId(2); ad.addReferenceIds(1); ad.addReferenceIds(2);
//			 * ad.addReferenceIds(3);
//			 * 
//			 * String json = mapper.writeValueAsString(ad);
//			 * 
//			 * Acknowledge a1 = controller.assignUserToProjects(json); assertEquals(0,
//			 * a1.getErrorCode());
//			 * 
//			 * //Acknowledge a2 = controller2.assignProjectsToUser(json); //assertEquals(0,
//			 * a2.getErrorCode());
//			 * 
//			 * 
//			 * 
//			 * 
//			 * AssignmentDetails ad2 = new AssignmentDetails();
//			 * ad2.setCredentials(credentials);
//			 * 
//			 * ad2.setId(2); ad2.addReferenceIds(2); ad2.addReferenceIds(1);
//			 * 
//			 * String json2 = mapper.writeValueAsString(ad2);
//			 * 
//			 * Acknowledge a4 = controller.assignUserToProjects(json2); assertEquals(0,
//			 * a4.getErrorCode());
//			 * 
//			 * //Acknowledge a3 = controller2.assignProjectsToUser(json2); //assertEquals(0,
//			 * a3.getErrorCode());
//			 * 
//			 */
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail("Exception thrown while trying to login: " + e.getMessage());
//		}
//	}
//
//	@Test
//	void usersApi() {
//		// @Path("/users")
//		try {
//			CredentialsInfo credentials = new CredentialsInfo();
//			// Login
//			{
//				String json = mapper.writeValueAsString(validUserDetails);
//
//				UsersController controller = new UsersController();
//				Credentials info = controller.login(json);
//				credentials.setToken(info.getCrededentials().getToken());
//				credentials.setUserId(info.getCrededentials().getUserId());
//			}
//			Request<UserInfo> lur = new Request<UserInfo>();
//			lur.setCredentials(credentials);
//
//			String json = mapper.writeValueAsString(lur);
//
//			// @Path("/get")
//			// Get users
//			UsersController controller = new UsersController();
//			Users info = controller.getUsers(json);
//			assertEquals(2, info.getUsers().size());
//			assertEquals(0, info.getErrorCode());
//
//			// @Path("/add")
//			// Add new user
//			Request<UserInfo> uti = new Request<UserInfo>();
//			UserInfo userInfo = new UserInfo();
//			userInfo.setIsActive(true);
//			userInfo.setContractId(1);
//			userInfo.setRoleId(1);
//			userInfo.setPassword("124");
//			userInfo.setEmail("abc@aa.com");
//			userInfo.setFirstName("vosganian");
//			userInfo.setLastName("varujan");
//			uti.setData(userInfo);
//			json = mapper.writeValueAsString(uti);
//			Acknowledge status = controller.createUser(json);
//			assertEquals(0, status.getErrorCode());
//
//			// @Path("/get")
//			// Check that the number of users has increased by 1
//			json = mapper.writeValueAsString(lur);
//			info = controller.getUsers(json);
//			assertEquals(3, info.getUsers().size());
//
//			// @Path("/add")
//			// add same user again. check that it fails
//			json = mapper.writeValueAsString(uti);
//			status = controller.createUser(json);
//			assertNotEquals(0, status.getErrorCode());
//
//			// @Path("/login")
//			// Check login failed with wrong details
//			Request<com.natea.pm.dto.base.LoginDetails> loginDetails = new Request<com.natea.pm.dto.base.LoginDetails>();
//			loginDetails.setData(new com.natea.pm.dto.base.LoginDetails("abc@aa.com", "pass"));
//			json = mapper.writeValueAsString(loginDetails);
//			Credentials cred = controller.login(json);
//			assertNotEquals(0, cred.getErrorCode());
//
//			// @Path("/update/pass")
//			// update password
//			userInfo.setUserId(3);
//			userInfo.setPassword("pass");
//			uti.setData(userInfo);
//			json = mapper.writeValueAsString(uti);
//			status = controller.updateUserPassword(json);
//			assertEquals(0, status.getErrorCode());
//
//			// @Path("/login")
//			// Check login ok now with the same details
//			json = mapper.writeValueAsString(loginDetails);
//			cred = controller.login(json);
//			assertEquals(0, cred.getErrorCode());
//
//			// @Path("/remove")
//			// remove user
//			Request<Integer> rId = new Request<Integer>();
//			rId.setData(3);
//			json = mapper.writeValueAsString(rId);
//			status = controller.removeUser(json);
//			assertEquals(0, status.getErrorCode());
//
//			// @Path("/get")
//			// check the user is removed
//			json = mapper.writeValueAsString(lur);
//			info = controller.getUsers(json);
//			assertEquals(2, info.getUsers().size());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail("Exception thrown while trying to login: " + e.getMessage());
//		}
//	}
//
//	@Test
//	void projectsApi() {
//		// @Path("/projects")
//		try {
//			Request<GenericQuery> pquery = new Request<GenericQuery>();
//			String json = mapper.writeValueAsString(pquery);
//
//			// @Path("/get")
//			// Get projects
//			ProjectsController controller = new ProjectsController();
//			Projects info = controller.getProjects(json);
//			assertEquals(1, info.getProjects().size());
//			assertEquals(0, info.getErrorCode());
//
//			// @Path("/add")
//			// Add new project
//			Request<ProjectInfo> pj = new Request<ProjectInfo>();
//			ProjectInfo data = new ProjectInfo();
//			data.setProjectName("habar nu am");
//			data.setDescription("salut");
//			pj.setData(data);
//			json = mapper.writeValueAsString(pj);
//			Acknowledge status = controller.createProject(json);
//			assertEquals(0, status.getErrorCode());
//
//			// @Path("/get")
//			// Check that there is only one project with if projectId = 1
//			data.setProjectId(1);
//			pj.setData(data);
//			json = mapper.writeValueAsString(pquery);
//			info = controller.getProjects(json);
//			assertEquals(1, info.getProjects().size());
//			assertEquals(0, info.getErrorCode());
//
//			// @Path("/get")
//			// Check that the number of projects has increased by 1
//			data.setProjectId(null);
//			pj.setData(data);
//			json = mapper.writeValueAsString(pquery);
//			info = controller.getProjects(json);
//			assertEquals(2, info.getProjects().size());
//			assertEquals(0, info.getErrorCode());
//
//			// @Path("/add")
//			// add same project again. check that it fails
//			json = mapper.writeValueAsString(pj);
//			status = controller.createProject(json);
//			assertNotEquals(0, status.getErrorCode());
//
//			int lastId = 0;
//			for (int id : info.getProjects().keySet()) {
//				if (lastId < id)
//					lastId = id;
//			}
//
//			// @Path("/remove")
//			// remove user
//			Request<Integer> rId = new Request<Integer>();
//			rId.setData(lastId);
//			json = mapper.writeValueAsString(rId);
//			status = controller.removeProject(json);
//			assertEquals(0, status.getErrorCode());
//
//			// @Path("/get")
//			// check the user is removed
//			json = mapper.writeValueAsString(pquery);
//			info = controller.getProjects(json);
//			assertEquals(1, info.getProjects().size());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail("Exception thrown while trying to login: " + e.getMessage());
//		}
//	}
//
//	@Test
//	void organizationApi() {
//		// @Path("/organization")
//		try {
//			CredentialsInfo credentials = new CredentialsInfo();
//			// Login
//			{
//				String json = mapper.writeValueAsString(validUserDetails);
//
//				UsersController controller = new UsersController();
//				Credentials info = controller.login(json);
//				credentials.setToken(info.getCrededentials().getToken());
//				credentials.setUserId(info.getCrededentials().getUserId());
//			}
//
//			Request<ProjectInfo> pquery = new Request<ProjectInfo>();
//			pquery.setCredentials(credentials);
//			String json = mapper.writeValueAsString(pquery);
//
//			// Get id of the first project
//			ProjectsController controller = new ProjectsController();
//			Projects info = controller.getProjects(json);
//			assertEquals(1, info.getProjects().size());
//			assertEquals(1, info.getUsers().size());
//			assertEquals(0, info.getErrorCode());
//			Integer[] projects = new Integer[5];
//			projects = info.getProjects().keySet().toArray(projects);
//			int projectId = projects[0];
//
//			// Get users list
//			Request<GenericQuery> lur = new Request<GenericQuery>();
//			lur.setCredentials(credentials);
//			String json2 = mapper.writeValueAsString(lur);
//			UsersController controller2 = new UsersController();
//			Users info2 = controller2.getUsers(json2);
//			assertEquals(2, info2.getUsers().size());
//			assertEquals(0, info2.getErrorCode());
//
//			OrganizationController controller3 = new OrganizationController();
//
//			// check assignment of users to project
//			int usersOnProject = 0;
//			for (int userId : info2.getUsers().keySet()) {
//				Request<OrganizationDetails> details = new Request<OrganizationDetails>();
//				OrganizationDetails orgDetails = new OrganizationDetails();
//				orgDetails.setUserId(userId);
//				orgDetails.setProjectId(projectId);
//				details.setData(orgDetails);
//				String json3 = mapper.writeValueAsString(details);
//
//				Acknowledge status3 = controller3.assignUserToProject(json3);
//				assertEquals(0, status3.getErrorCode());
//
//				info = controller.getProjects(json);
//				assertEquals(1, info.getProjects().size());
//				assertEquals(++usersOnProject, info.getUsers().size());
//			}
//
//			// check removal of users from project
//			for (int userId : info2.getUsers().keySet()) {
//				Request<com.natea.pm.dto.base.OrganizationDetails> details = new Request<OrganizationDetails>();
//				OrganizationDetails orgDetails = new OrganizationDetails();
//				orgDetails.setUserId(userId);
//				orgDetails.setProjectId(projectId);
//				details.setData(orgDetails);
//				String json3 = mapper.writeValueAsString(details);
//
//				Acknowledge status3 = controller3.removeUserFromProject(json3);
//				assertEquals(0, status3.getErrorCode());
//
//				info = controller.getProjects(json);
//				assertEquals(1, info.getProjects().size());
//				assertEquals(--usersOnProject, info.getUsers().size());
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail("Exception thrown while trying to login: " + e.getMessage());
//		}
//	}
//
//	@SuppressWarnings("deprecation")
//	@Test
//	void requestApi() {
//		// @Path("/requests")
//		try {
//			Request<GenericQuery> lquery = new Request<GenericQuery>();
//
//			// @Path("/get")
//			// Get requests
//			RequestsController controller = new RequestsController();
//			String json = mapper.writeValueAsString(lquery);
//			Requests info = controller.getLeaveRequests(json);
//			assertEquals(0, info.getLeaveRequests().size());
//			assertEquals(0, info.getErrorCode());
//
//			// @Path("/request")
//			// Add new project
//			Request<LeaveRequestInfo> details = new Request<LeaveRequestInfo>();
//			LeaveRequestInfo data = new LeaveRequestInfo();
//			data.setUserId(1);
//			data.setDateStart(new Date(2009, 10, 10));
//			data.setDateEnd(new Date(2009, 10, 10));
//			data.setComment("bla bla");
//			details.setData(data);
//
//			json = mapper.writeValueAsString(details);
//			Acknowledge status = controller.registerLeaveRequest(json);
//			assertEquals(0, status.getErrorCode());
//
//			// @Path("/get")
//			// Get requests
//			json = mapper.writeValueAsString(lquery);
//			info = controller.getLeaveRequests(json);
//			assertEquals(1, info.getLeaveRequests().size());
//			assertEquals(0, info.getErrorCode());
//
//			// @Path("/approve")
//			// Check that there is only one project with if projectId = 1
//			Request<Integer> rId = new Request<Integer>();
//			rId.setData(1);
//			json = mapper.writeValueAsString(rId);
//			status = controller.approveLeaveRequest(json);
//			assertEquals(0, status.getErrorCode());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail("Exception thrown while trying to login: " + e.getMessage());
//		}
//	}
//
//	@Test
//	void timesheetApi() {
//		// @Path("/requests")
//		try {
//			Request<GenericQuery> tsquery = new Request<GenericQuery>();
//
//			// @Path("/get")
//			// Get timesheets
//			TimeSheetsController controller = new TimeSheetsController();
//			String json = mapper.writeValueAsString(tsquery);
//			TimeSheets info = controller.getLoggedTime(json);
//			assertEquals(0, info.getTimeSheets().size());
//			assertEquals(0, info.getErrorCode());
//
//			// @Path("/register")
//			// Add new timesheet
//			TimeSheetDetails details = new TimeSheetDetails();
//			details.setUserId(1);
//			Calendar cal = Calendar.getInstance();
//			cal.set(Calendar.YEAR, 2000);
//			cal.set(Calendar.MONTH, 0);
//			cal.set(Calendar.DAY_OF_MONTH, 1);
//			Date date = new Date(cal.getTimeInMillis());
//			TimeSheetInfo entry = new TimeSheetInfo();
//			entry.setDate(date);
//			entry.setTimeSpent(10);
//			entry.setProjectId(1);
//			details.addTimeEntry(entry);
//			entry = new TimeSheetInfo();
//			entry.setDate(date);
//			entry.setTimeSpent(12);
//			entry.setProjectId(1);
//			details.addTimeEntry(entry);
//
//			json = mapper.writeValueAsString(details);
//			Acknowledge status = controller.registerTimeSheet(json);
//			assertEquals(0, status.getErrorCode());
//
//			// @Path("/get")
//			// Get timesheets expect to have a extra one
//			json = mapper.writeValueAsString(tsquery);
//			info = controller.getLoggedTime(json);
//			assertEquals(2, info.getTimeSheets().size());
//			assertEquals(0, info.getErrorCode());
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail("Exception thrown while trying to login: " + e.getMessage());
//		}
//	}
}
