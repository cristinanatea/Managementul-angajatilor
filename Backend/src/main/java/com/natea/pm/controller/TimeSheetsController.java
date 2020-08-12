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
import com.natea.pm.dto.base.TimeSheetDetails;
import com.natea.pm.dto.request.Request;
import com.natea.pm.dto.response.Acknowledge;
import com.natea.pm.dto.response.TimeSheets;

@RestController
@Path("/timesheets")
public class TimeSheetsController {

	ApplicationContext context;
	UsersManagerItf business;

	public TimeSheetsController() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		business = (UsersManagerItf) context.getBean("business");
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledge registerTimeSheet(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<TimeSheetDetails> request = mapper.readValue(json, new TypeReference<Request<TimeSheetDetails>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.registerTimeSheet(request);
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
	public TimeSheets getLoggedTime(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request<GenericQuery> request = mapper.readValue(json, new TypeReference<Request<GenericQuery>>() {
			});

			UsersManagerItf business = (UsersManagerItf) context.getBean("business");

			return business.getLoggedTime(request);
		} catch (Exception e) {
			// JsonParseException JsonMappingException IOException
			e.printStackTrace();
			System.out.println("exceptie " + e);
			return null;
		}
	}
}
