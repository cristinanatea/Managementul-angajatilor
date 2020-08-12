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
import com.natea.pm.dto.base.GenericQuery;
import com.natea.pm.dto.base.LeaveRequestInfo;
import com.natea.pm.dto.request.Request;
import com.natea.pm.dto.response.Acknowledge;
import com.natea.pm.dto.response.Requests;

@RestController
@Path("/requests")
public class RequestsController {

	ApplicationContext context;
	UsersManagerItf business;

	public RequestsController() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		business = (UsersManagerItf) context.getBean("business");
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledge registerLeaveRequest(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<LeaveRequestInfo> request = mapper.readValue(json, new TypeReference<Request<LeaveRequestInfo>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.registerLeaveRequest(request);
		} catch (Exception e) {
			// JsonParseException JsonMappingException IOException
			e.printStackTrace();
			System.out.println("exceptie " + e);
			return null;
		}
	}

	@POST
	@Path("/approve")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledge approveLeaveRequest(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<Integer> request = mapper.readValue(json, new TypeReference<Request<Integer>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.approveLeaveRequest(request);
		} catch (Exception e) {
			// JsonParseException JsonMappingException IOException
			e.printStackTrace();
			System.out.println("exceptie " + e);
			return null;
		}
	}

	@POST
	@Path("/reject")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledge rejectLeaveRequest(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<Integer> request = mapper.readValue(json, new TypeReference<Request<Integer>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.rejectLeaveRequest(request);
		} catch (Exception e) {
			// JsonParseException JsonMappingException IOException
			e.printStackTrace();
			System.out.println("exceptie " + e);
			return null;
		}
	}

	@POST
	@Path("/cancel")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledge cancelLeaveRequest(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<Integer> request = mapper.readValue(json, new TypeReference<Request<Integer>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.cancelLeaveRequest(request);
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
	public Requests getLeaveRequests(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<GenericQuery> request = mapper.readValue(json, new TypeReference<Request<GenericQuery>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.getLeaveRequests(request);
		} catch (Exception e) {
			// JsonParseException JsonMappingException IOException
			e.printStackTrace();
			System.out.println("exceptie " + e);
			return null;
		}
	}
}
