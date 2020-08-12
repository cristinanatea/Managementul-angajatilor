package com.natea.pm.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RestController;

import com.natea.pm.business.UsersManagerItf;
import com.natea.pm.dto.base.AssignmentDetails;
import com.natea.pm.dto.base.GenericQuery;
import com.natea.pm.dto.base.LoginDetails;
import com.natea.pm.dto.base.PassUpdateDetails;
import com.natea.pm.dto.base.UserInfo;
import com.natea.pm.dto.request.Request;
import com.natea.pm.dto.response.Acknowledge;
import com.natea.pm.dto.response.Credentials;
import com.natea.pm.dto.response.Users;

@RestController
@Path("/users")
public class UsersController {

	ApplicationContext context;
	UsersManagerItf business;

	public UsersController() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		business = (UsersManagerItf) context.getBean("business");
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Credentials login(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<LoginDetails> loginInfo = mapper.readValue(json, new TypeReference<Request<LoginDetails>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.login(loginInfo);
		} catch (Exception e) {
			// JsonParseException JsonMappingException IOException
			e.printStackTrace();
			System.out.println("exceptie " + e);
			return null;
		}
	}

	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledge logout(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<Integer> loginInfo = mapper.readValue(json, new TypeReference<Request<Integer>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.logout(loginInfo);
		} catch (Exception e) {
			// JsonParseException JsonMappingException IOException
			e.printStackTrace();
			System.out.println("exceptie " + e);
			return null;
		}
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledge createUser(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<UserInfo> request = mapper.readValue(json, new TypeReference<Request<UserInfo>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.createUser(request);
		} catch (Exception e) {
			// JsonParseException JsonMappingException IOException
			e.printStackTrace();
			System.out.println("exceptie " + e);
			return null;
		}
	}

	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledge updateUser(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<UserInfo> request = mapper.readValue(json, new TypeReference<Request<UserInfo>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.updateUser(request);
		} catch (Exception e) {
			// JsonParseException JsonMappingException IOException
			e.printStackTrace();
			System.out.println("exceptie " + e);
			return null;
		}
	}

	@POST
	@Path("/update/password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledge updateUserPassword(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<PassUpdateDetails> request = mapper.readValue(json,
					new TypeReference<Request<PassUpdateDetails>>() {
					});
			System.out.println(request);
			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.updateUserPassword(request);
		} catch (Exception e) {
			// JsonParseException JsonMappingException IOException
			e.printStackTrace();
			System.out.println("exceptie " + e);
			return null;
		}
	}

	@POST
	@Path("/reset/password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledge resetUserPassword(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<Integer> request = mapper.readValue(json, new TypeReference<Request<Integer>>() {
			});

			System.out.println(request);
			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.resetUserPassword(request);
		} catch (Exception e) {
			// JsonParseException JsonMappingException IOException
			e.printStackTrace();
			System.out.println("exceptie " + e);
			return null;
		}
	}

	@POST
	@Path("/remove")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledge removeUser(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<Integer> request = mapper.readValue(json, new TypeReference<Request<Integer>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.removeUser(request);
		} catch (Exception e) {
			// JsonParseException JsonMappingException IOException
			e.printStackTrace();
			System.out.println("exceptie " + e);
			return null;
		}
	}

	@POST
	@Path("/get")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Users getUsers(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<GenericQuery> request = mapper.readValue(json, new TypeReference<Request<GenericQuery>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.getUsers(request);
		} catch (Exception e) {
			// JsonParseException JsonMappingException IOException
			e.printStackTrace();
			System.out.println("exceptie " + e);
			return null;
		}
	}

	@POST
	@Path("/assign")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledge assignUserToProjects(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<AssignmentDetails> request = mapper.readValue(json,
					new TypeReference<Request<AssignmentDetails>>() {
					});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.assignUsersToProject(request);
		} catch (Exception e) {
			// JsonParseException JsonMappingException IOException
			e.printStackTrace();
			System.out.println("exceptie " + e);
			return null;
		}
	}
}
