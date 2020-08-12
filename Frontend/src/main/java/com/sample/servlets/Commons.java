package com.sample.servlets;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.natea.pm.dto.base.BaseResponse;
import com.natea.pm.dto.base.HolidayInfo;
import com.natea.pm.dto.base.LeaveRequestInfo;
import com.natea.pm.dto.base.TimeSheetInfo;
import com.natea.pm.dto.base.UserInfo;
import com.natea.pm.dto.response.Credentials;
import com.natea.pm.dto.response.TimeSheets;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Commons {
	public static final String MAIN_REQUEST_URL = "http://localhost:8080/Sample/rest";

	public static final String URL_LIST_REQUESTS = MAIN_REQUEST_URL + "/requests/get";
	public static final String URL_ADD_REQUEST = MAIN_REQUEST_URL + "/requests/add";
	public static final String URL_APPROVE_REQUEST = MAIN_REQUEST_URL + "/requests/approve";
	public static final String URL_REJECT_REQUEST = MAIN_REQUEST_URL + "/requests/reject";
	public static final String URL_CANCEL_REQUEST = MAIN_REQUEST_URL + "/requests/cancel";

	public static final String URL_ADD_PROJECT = MAIN_REQUEST_URL + "/projects/add";
	public static final String URL_EDIT_PROJECT = MAIN_REQUEST_URL + "/projects/update";
	public static final String URL_LIST_PROJECTS = MAIN_REQUEST_URL + "/projects/get";

	public static final String URL_ADD_USER = MAIN_REQUEST_URL + "/users/add";
	public static final String URL_UPDATE_USER = MAIN_REQUEST_URL + "/users/update";
	public static final String URL_LIST_USERS = MAIN_REQUEST_URL + "/users/get";
	public static final String URL_LOGIN = MAIN_REQUEST_URL + "/users/login";
	public static final String URL_LOGOUT = MAIN_REQUEST_URL + "/users/logout";
	public static final String URL_UPDATE_PASS = MAIN_REQUEST_URL + "/users/update/password";
	public static final String URL_RESET_PASS = MAIN_REQUEST_URL + "/users/reset/password";

	public static final String URL_LIST_TIMESHEET = MAIN_REQUEST_URL + "/timesheets/get";
	public static final String URL_ADD_TIMESHEET = MAIN_REQUEST_URL + "/timesheets/add";

	public static final String URL_LIST_HOLIDAYS = MAIN_REQUEST_URL + "/holidays/get";
	public static final String URL_ADD_HOLIDAY = MAIN_REQUEST_URL + "/holidays/add";
	public static final String URL_REMOVE_HOLIDAY = MAIN_REQUEST_URL + "/holidays/remove";

	public static final String URL_LIST_ORGANIZATION = MAIN_REQUEST_URL + "/organization/get";

	public static final String URL_ADD_ROLE = MAIN_REQUEST_URL + "/organization/add/role";
	public static final String URL_REMOVE_ROLE = MAIN_REQUEST_URL + "/organization/remove/role";

	public static final String URL_ADD_CONTRACT = MAIN_REQUEST_URL + "/organization/add/contract";
	public static final String URL_REMOVE_CONTRACT = MAIN_REQUEST_URL + "/organization/remove/contract";

	public static final String URL_ASSIGN_USERS_TO_PROJECT = MAIN_REQUEST_URL + "/users/assign";
	public static final String URL_ASSIGN_PROJECTS_TO_USER = MAIN_REQUEST_URL + "/projects/assign";

	private static final ObjectMapper mapper = new ObjectMapper();

	public static Date parseDate(String str) {
		java.sql.Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date parsed = null;
		try {
			parsed = sdf.parse(str);
			date = new java.sql.Date(parsed.getTime());
		} catch (ParseException e1) {

		}

		return date;
	}

	public static Credentials getCredentialsOrRedirect(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Credentials credentials = (Credentials) session.getAttribute("credentials");

		// Check if permissions are available
		if (credentials == null) {
			request.getRequestDispatcher("/page/login.jsp").forward(request, response);
		}

		return credentials;
	}

	public static String request(HttpServletRequest request, HttpServletResponse response, String URL,
			String parameters) throws ServletException, IOException {
		String result = null;

		mapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Client client = Client.create();
		WebResource webResource = client.resource(URL);
		ClientResponse status = webResource.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,
				parameters);

		if (status.getStatus() != 200) {
			request.setAttribute("errorCode", status.getStatus());
			request.setAttribute("errorType", "Server");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		} else {
			result = status.getEntity(String.class);
			BaseResponse requestStatus = mapper.readValue(result, new TypeReference<BaseResponse>() {
			});

			if (requestStatus.getErrorCode() != 0) {
				request.setAttribute("errorType", "Permissions");
				request.setAttribute("errorCode", requestStatus.getErrorCode());
				request.setAttribute("errorMessage", requestStatus.getErrorMessage());
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				result = null;
			}
		}
		return result;
	}

	public static Integer tryParseInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static boolean checkBooleanVal(String text) {
		if (text != null) {
			if (text.equals("yes") || text.equals("YES") || text.equals("Yes") || text.equals("true")
					|| text.equals("TRUE") || text.equals("True") || text.equals("On") || text.equals("on")
					|| text.equals("ON")) {
				return true;
			}
		}

		return false;
	}

	@SuppressWarnings("unused")
	private static class Event {
		private String title;
		private Date start;
		private Date end;
		String color = "#666";

		public String getTitle() {
			return title;
		}

		public Date getStart() {
			return start;
		}

		public Date getEnd() {
			return end;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public void setStart(Date start) {
			this.start = start;
		}

		public void setEnd(Date end) {
			this.end = end;
		}
	}

	public static String getTimeSpentStr(int time) {
		String response = "";
		int hours = time / 60;
		int minutes = time % 60;

		response += (hours > 0) ? (Integer.toString(hours) + "h") : "";
		response += (minutes > 0) ? (Integer.toString(minutes) + "m") : "";

		return response;
	}

	public static String getUserEventsList(TimeSheets ts) {
		String jsonString = "[]";
		List<Event> events = new ArrayList<Commons.Event>();

		// Logged Days
		for (Map.Entry<Integer, TimeSheetInfo> tsi : ts.getTimeSheets().entrySet()) {
			String projectName = ts.getProjects().get(tsi.getValue().getProjectId()).getProjectName();
			String timeSpent = getTimeSpentStr(tsi.getValue().getTimeSpent());

			Commons.Event ev = new Event();
			ev.setStart(tsi.getValue().getDate());
			ev.setTitle(tsi.getValue().getProjectId() + ". " + projectName + " - " + timeSpent);
			events.add(ev);
		}

		for (HolidayInfo day : ts.getHolidays()) {
			Event ev = new Event();
			ev.setStart(day.getDate());
			ev.setTitle(day.getName());
			ev.setColor("#a9de81");
			events.add(ev);
		}

		for (LeaveRequestInfo lr : ts.getLeaveRequests()) {
			UserInfo user = ts.getUsers().get(lr.getUserId());
			if (user != null) {
				Event ev = new Event();
				ev.setStart(lr.getDateStart());
				ev.setEnd(lr.getDateEnd());
				ev.setTitle(user.getFirstName() + " " + user.getLastName());
				ev.setColor("#42efff");
				events.add(ev);
			}
		}

		try {
			jsonString = mapper.writeValueAsString(events);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsonString;
	}

	public static String getProjectEventsList(TimeSheets ts) {
		String jsonString = "[]";
		List<Event> events = new ArrayList<Commons.Event>();

		// Logged Days
		for (Map.Entry<Integer, TimeSheetInfo> tsi : ts.getTimeSheets().entrySet()) {
			UserInfo user = ts.getUsers().get(tsi.getValue().getUserId());
			String userName = user.getFirstName() + " " + user.getLastName();
			String timeSpent = getTimeSpentStr(tsi.getValue().getTimeSpent());

			Commons.Event ev = new Event();
			ev.setStart(tsi.getValue().getDate());
			ev.setTitle(userName + " - " + timeSpent);
			events.add(ev);
		}

		for (HolidayInfo day : ts.getHolidays()) {
			Event ev = new Event();
			ev.setStart(day.getDate());
			ev.setTitle(day.getName());
			ev.setColor("#a9de81");
			events.add(ev);
		}

		try {
			jsonString = mapper.writeValueAsString(events);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsonString;
	}
}
