package com.sample.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.natea.pm.dto.base.AssignmentDetails;
import com.natea.pm.dto.base.GenericQuery;
import com.natea.pm.dto.base.ProjectInfo;
import com.natea.pm.dto.base.UserInfo;
import com.natea.pm.dto.request.Request;
import com.natea.pm.dto.response.Credentials;
import com.natea.pm.dto.response.Projects;
import com.natea.pm.dto.response.Users;

@WebServlet("/manage/*")
public class ManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final ObjectMapper mapper = new ObjectMapper();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Credentials credentials = Commons.getCredentialsOrRedirect(request, response);

		if (credentials != null) {
			String URL = "";
			Integer entryId = Commons.tryParseInt(request.getParameter("id"));
			String redirectPath = "";

			String path = request.getPathInfo();
			if (path != null && (path.startsWith("/user/"))) {
				URL = Commons.URL_ASSIGN_PROJECTS_TO_USER;
				redirectPath = "/users/";
			} else if (path != null && (path.startsWith("/project/"))) {
				URL = Commons.URL_ASSIGN_USERS_TO_PROJECT;
				redirectPath = "/projects/";
			}

			if (URL.isEmpty() || entryId == null) {
				request.getRequestDispatcher("/page/main.jsp").forward(request, response);
				return;
			}

			String[] checkedIds = request.getParameterValues("checkedEntries");
			if (checkedIds != null) {
				AssignmentDetails asig = new AssignmentDetails();

				asig.setId(entryId);

				for (int i = 0; i < checkedIds.length; i++) {
					asig.addReferenceIds(Commons.tryParseInt(checkedIds[i]));
				}

				String jsonString = mapper
						.writeValueAsString(new Request<AssignmentDetails>(credentials.getCrededentials(), asig));
				String json = Commons.request(request, response, URL, jsonString);
				if (json != null) {
					request.getRequestDispatcher(redirectPath + entryId).forward(request, response);
				}
			} else {
				request.getRequestDispatcher("/page/main.jsp").forward(request, response);
				return;
			}
		}
	}

	private static void displayUserDetails(HttpServletRequest request, HttpServletResponse response,
			Request<GenericQuery> query, Request<GenericQuery> queryProjects)
			throws JsonGenerationException, JsonMappingException, IOException, ServletException {
		String jsonString = mapper.writeValueAsString(query);
		String json = Commons.request(request, response, Commons.URL_LIST_USERS, jsonString);
		if (json != null) {
			Users users = mapper.readValue(json, new TypeReference<Users>() {
			});

			if (users.getUsers().size() == 1) {

				String jsonProjectsString = mapper.writeValueAsString(queryProjects);
				String jsonProjects = Commons.request(request, response, Commons.URL_LIST_PROJECTS, jsonProjectsString);
				if (jsonProjects != null) {
					Projects projects = mapper.readValue(jsonProjects, new TypeReference<Projects>() {
					});

					Integer userId = (Integer) users.getUsers().keySet().toArray()[0];
					UserInfo user = users.getUsers().get(userId);
					Set<Integer> assignedUsers = users.getProjectsAssignedToUser().get(userId);

					request.setAttribute("id", userId);
					request.setAttribute("title",
							user.getFirstName() + " " + user.getLastName() + " [" + user.getEmail() + "]");
					Map<Integer, String> checkedEntries = new HashMap<Integer, String>();
					Map<Integer, String> uncheckedEntries = new HashMap<Integer, String>();

					for (Integer key : projects.getProjects().keySet()) {
						ProjectInfo project = projects.getProjects().get(key);

						if (assignedUsers.contains(key)) {
							checkedEntries.put(key, project.getProjectName());
						} else {
							uncheckedEntries.put(key, project.getProjectName());
						}
					}
					request.setAttribute("checkedEntries", checkedEntries);
					request.setAttribute("uncheckedEntries", uncheckedEntries);

					request.getRequestDispatcher("/page/manage.jsp").forward(request, response);
				}
				return;
			}

			request.getRequestDispatcher("/page/main.jsp").forward(request, response);
		}
	}

	private static void displayProjectDetails(HttpServletRequest request, HttpServletResponse response,
			Request<GenericQuery> query, Request<GenericQuery> queryUsers)
			throws JsonGenerationException, JsonMappingException, IOException, ServletException {
		String jsonString = mapper.writeValueAsString(query);
		String json = Commons.request(request, response, Commons.URL_LIST_PROJECTS, jsonString);
		if (json != null) {
			Projects projects = mapper.readValue(json, new TypeReference<Projects>() {
			});

			if (projects.getProjects().size() == 1) {
				String jsonUsersString = mapper.writeValueAsString(queryUsers);
				String jsonUsers = Commons.request(request, response, Commons.URL_LIST_USERS, jsonUsersString);
				if (jsonUsers != null) {
					Users users = mapper.readValue(jsonUsers, new TypeReference<Users>() {
					});

					Integer projectId = (Integer) projects.getProjects().keySet().toArray()[0];
					ProjectInfo project = projects.getProjects().get(projectId);
					Set<Integer> assignedUsers = projects.getUsersAssignedToProject().get(projectId);

					request.setAttribute("id", projectId);
					request.setAttribute("title", project.getProjectName());
					Map<Integer, String> checkedEntries = new HashMap<Integer, String>();
					Map<Integer, String> uncheckedEntries = new HashMap<Integer, String>();

					for (Integer key : users.getUsers().keySet()) {
						UserInfo user = users.getUsers().get(key);

						if (assignedUsers.contains(key)) {
							checkedEntries.put(key,
									user.getFirstName() + " " + user.getLastName() + " " + user.getEmail());
						} else {
							uncheckedEntries.put(key,
									user.getFirstName() + " " + user.getLastName() + " " + user.getEmail());
						}
					}
					request.setAttribute("checkedEntries", checkedEntries);
					request.setAttribute("uncheckedEntries", uncheckedEntries);

					request.getRequestDispatcher("/page/manage.jsp").forward(request, response);
				}
				return;
			}
			request.getRequestDispatcher("/page/main.jsp").forward(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Credentials credentials = Commons.getCredentialsOrRedirect(request, response);

		if (credentials != null) {
			String path = request.getPathInfo();
			if (path != null && (path.startsWith("/user/"))) {
				Integer userId = Commons.tryParseInt(path.substring(6));

				if (userId != null) {
					GenericQuery userQuery = new GenericQuery();
					GenericQuery projectQuery = new GenericQuery();
					userQuery.setUserId(userId);
					displayUserDetails(request, response,
							new Request<GenericQuery>(credentials.getCrededentials(), userQuery),
							new Request<GenericQuery>(credentials.getCrededentials(), projectQuery));
					return;
				}
			} else if (path != null && (path.startsWith("/project/"))) {
				Integer projectId = Commons.tryParseInt(path.substring(9));

				if (projectId != null) {
					GenericQuery userQuery = new GenericQuery();
					GenericQuery projectQuery = new GenericQuery();
					projectQuery.setProjectId(projectId);
					displayProjectDetails(request, response,
							new Request<GenericQuery>(credentials.getCrededentials(), projectQuery),
							new Request<GenericQuery>(credentials.getCrededentials(), userQuery));
					return;
				}
			}

			// request.getRequestDispatcher("/page/main.jsp").forward(request, response);
		}
	}
}