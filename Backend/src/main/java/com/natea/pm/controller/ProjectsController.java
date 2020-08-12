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
import com.natea.pm.dto.base.ProjectInfo;
import com.natea.pm.dto.request.Request;
import com.natea.pm.dto.response.Acknowledge;
import com.natea.pm.dto.response.Projects;

@RestController
@Path("/projects")
public class ProjectsController {

	ApplicationContext context;
	UsersManagerItf business;

	public ProjectsController() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		business = (UsersManagerItf) context.getBean("business");
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledge createProject(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<ProjectInfo> request = mapper.readValue(json, new TypeReference<Request<ProjectInfo>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.createProject(request);
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
	public Acknowledge updateProject(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<ProjectInfo> request = mapper.readValue(json, new TypeReference<Request<ProjectInfo>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.updateProject(request);
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
	public Acknowledge removeProject(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<Integer> request = mapper.readValue(json, new TypeReference<Request<Integer>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.removeProject(request);
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
	public Projects getProjects(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<GenericQuery> request = mapper.readValue(json, new TypeReference<Request<GenericQuery>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.getProjects(request);
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
	public Acknowledge assignProjectsToUser(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<AssignmentDetails> request = mapper.readValue(json,
					new TypeReference<Request<AssignmentDetails>>() {
					});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.assignProjectsToUser(request);
		} catch (Exception e) {
			// JsonParseException JsonMappingException IOException
			e.printStackTrace();
			System.out.println("exceptie " + e);
			return null;
		}
	}
}
