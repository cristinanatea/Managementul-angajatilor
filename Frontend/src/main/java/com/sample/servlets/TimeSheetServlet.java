package com.sample.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.natea.pm.dto.base.GenericQuery;
import com.natea.pm.dto.base.TimeSheetDetails;
import com.natea.pm.dto.base.TimeSheetInfo;
import com.natea.pm.dto.base.UserInfo;
import com.natea.pm.dto.request.Request;
import com.natea.pm.dto.response.Credentials;
import com.natea.pm.dto.response.Projects;
import com.natea.pm.dto.response.TimeSheets;

@WebServlet("/timesheet/*")
public class TimeSheetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final ObjectMapper mapper = new ObjectMapper();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Credentials credentials = Commons.getCredentialsOrRedirect(request, response);

		if (credentials != null) {
			String entries = request.getParameter("entries");

			if (entries != null && entries.length() > 0) {
				Set<TimeSheetInfo> timeEntries = mapper.readValue(entries, new TypeReference<Set<TimeSheetInfo>>() {
				});

				TimeSheetDetails tsd = new TimeSheetDetails();
				tsd.setUserId(credentials.getCrededentials().getUserId());
				tsd.setTimeEntries(timeEntries);

				String jsonString = mapper
						.writeValueAsString(new Request<TimeSheetDetails>(credentials.getCrededentials(), tsd));
				String json = Commons.request(request, response, Commons.URL_ADD_TIMESHEET, jsonString);
				if (json == null) {
					return;
				}
			}

			doGet(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Credentials credentials = Commons.getCredentialsOrRedirect(request, response);

		if (credentials != null) {
			boolean enableSubmit = true;
			Integer userId = credentials.getCrededentials().getUserId();
			Integer projectId = null;
			GenericQuery tsQuery = new GenericQuery();
			List<String> summaryListing = new ArrayList<String>();

			String path = request.getPathInfo();
			if (path != null) {
				if (path.startsWith("/user/")) {
					Integer tempId = Commons.tryParseInt(path.substring(6));
					if (tempId != null && tempId != credentials.getCrededentials().getUserId()) {
						userId = tempId;
						enableSubmit = false;
					}
				} else {
					userId = null;
				}
				if (path.startsWith("/project/")) {
					projectId = Commons.tryParseInt(path.substring(9));
					enableSubmit = false;
				}
			}

			tsQuery.setUserId(userId);
			tsQuery.setProjectId(projectId);
			System.out.println(tsQuery);
			// Get users timesheet
			{
				String jsonString = mapper
						.writeValueAsString(new Request<GenericQuery>(credentials.getCrededentials(), tsQuery));
				String json = Commons.request(request, response, Commons.URL_LIST_TIMESHEET, jsonString);
				if (json != null) {
					TimeSheets timesheets = mapper.readValue(json, new TypeReference<TimeSheets>() {
					});

					String eventsList = (projectId == null) ? Commons.getUserEventsList(timesheets)
							: Commons.getProjectEventsList(timesheets);
					session.setAttribute("eventsList", eventsList);
					session.setAttribute("timeSheetsList", timesheets);

					if (projectId != null) {
						for (Integer key : timesheets.getUsers().keySet()) {
							UserInfo user = timesheets.getUsers().get(key);
							summaryListing.add(user.getFirstName() + " " + user.getLastName());
						}
					} else {
						// Get users projects
						GenericQuery query = new GenericQuery();
						query.setUserId(credentials.getCrededentials().getUserId());

						jsonString = mapper
								.writeValueAsString(new Request<GenericQuery>(credentials.getCrededentials(), query));
						json = Commons.request(request, response, Commons.URL_LIST_PROJECTS, jsonString);
						if (json != null) {
							Projects projects = mapper.readValue(json, new TypeReference<Projects>() {
							});

							for (Integer key : projects.getProjects().keySet()) {
								summaryListing.add(key + ". " + projects.getProjects().get(key).getProjectName());
							}
						}
					}
				}
			}

			session.setAttribute("summaryListing", summaryListing);
			session.setAttribute("enableSubmit", enableSubmit);
			request.getRequestDispatcher("/page/timesheet.jsp").forward(request, response);
		}
	}
}