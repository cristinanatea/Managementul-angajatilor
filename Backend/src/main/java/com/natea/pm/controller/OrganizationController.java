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
import com.natea.pm.dto.base.ContractInfo;
import com.natea.pm.dto.base.OrganizationDetails;
import com.natea.pm.dto.base.RoleInfo;
import com.natea.pm.dto.request.Request;
import com.natea.pm.dto.response.Acknowledge;
import com.natea.pm.dto.response.Organization;

@RestController
@Path("/organization")
public class OrganizationController {

	ApplicationContext context;
	UsersManagerItf business;

	public OrganizationController() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		business = (UsersManagerItf) context.getBean("business");
	}

	@POST
	@Path("/assign")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledge assignUserToProject(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<OrganizationDetails> request = mapper.readValue(json,
					new TypeReference<Request<OrganizationDetails>>() {
					});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.assignUserToProject(request);
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
	public Acknowledge removeUserFromProject(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<OrganizationDetails> request = mapper.readValue(json,
					new TypeReference<Request<OrganizationDetails>>() {
					});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.removeUserFromProject(request);
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
	public Organization getOrganizationInfo(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<Integer> request = mapper.readValue(json, new TypeReference<Request<Integer>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.getOrganizationInfo(request);
		} catch (Exception e) {
			// JsonParseException JsonMappingException IOException
			e.printStackTrace();
			System.out.println("exceptie " + e);
			return null;
		}
	}

	@POST
	@Path("/add/role")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledge addRole(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<RoleInfo> request = mapper.readValue(json, new TypeReference<Request<RoleInfo>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.addRole(request);
		} catch (Exception e) {
			// JsonParseException JsonMappingException IOException
			e.printStackTrace();
			System.out.println("exceptie " + e);
			return null;
		}
	}

	@POST
	@Path("/add/contract")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledge addContract(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<ContractInfo> request = mapper.readValue(json, new TypeReference<Request<ContractInfo>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.addContract(request);
		} catch (Exception e) {
			// JsonParseException JsonMappingException IOException
			e.printStackTrace();
			System.out.println("exceptie " + e);
			return null;
		}
	}

	@POST
	@Path("/remove/role")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledge removeRole(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<Integer> request = mapper.readValue(json, new TypeReference<Request<Integer>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.removeRole(request);
		} catch (Exception e) {
			// JsonParseException JsonMappingException IOException
			e.printStackTrace();
			System.out.println("exceptie " + e);
			return null;
		}
	}

	@POST
	@Path("/remove/contract")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledge removeContract(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<Integer> request = mapper.readValue(json, new TypeReference<Request<Integer>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.removeContract(request);
		} catch (Exception e) {
			// JsonParseException JsonMappingException IOException
			e.printStackTrace();
			System.out.println("exceptie " + e);
			return null;
		}
	}
}
