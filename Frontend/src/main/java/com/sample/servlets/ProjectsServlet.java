package com.sample.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.natea.pm.dto.base.GenericQuery;
import com.natea.pm.dto.base.ProjectInfo;
import com.natea.pm.dto.request.Request;
import com.natea.pm.dto.response.Acknowledge;
import com.natea.pm.dto.response.Credentials;
import com.natea.pm.dto.response.Projects;

@WebServlet("/projects/*")
public class ProjectsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final ObjectMapper mapper = new ObjectMapper();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Credentials credentials = Commons.getCredentialsOrRedirect(request, response);
		if (credentials != null) {
			String URL = "";
			Integer projectId = null;
			String path = request.getPathInfo();
			if (path != null && (path.contentEquals("/add"))) {
				URL = Commons.URL_ADD_PROJECT;
			} else if (path != null && (path.startsWith("/edit"))) {
				URL = Commons.URL_EDIT_PROJECT;
				projectId = Commons.tryParseInt(request.getParameter("projectId"));

				if (projectId == null) {
					doGet(request, response);
					return;
				}
			} else {
				doGet(request, response);
				return;
			}

			ProjectInfo query = new ProjectInfo();
			query.setProjectId(projectId);
			query.setProjectName(request.getParameter("name"));
			query.setDescription(request.getParameter("description"));

			String jsonString = mapper
					.writeValueAsString(new Request<ProjectInfo>(credentials.getCrededentials(), query));
			String json = Commons.request(request, response, URL, jsonString);
			if (json != null) {
				Acknowledge result = mapper.readValue(json, new TypeReference<Acknowledge>() {
				});

				response.sendRedirect(request.getServletContext().getContextPath() + "/" + request.getServletPath()
						+ "/" + result.getId());
			}
		}
	}

	private static void displayProjectDetails(HttpServletRequest request, HttpServletResponse response,
			Request<GenericQuery> query, boolean editMode)
			throws JsonGenerationException, JsonMappingException, IOException, ServletException {
		HttpSession session = request.getSession();

		String jsonString = mapper.writeValueAsString(query);
		String json = Commons.request(request, response, Commons.URL_LIST_PROJECTS, jsonString);
		if (json != null) {
			Projects projects = mapper.readValue(json, new TypeReference<Projects>() {
			});

			String loadingPage = "/page/projects.jsp";
			request.setAttribute("editMode", editMode);
			request.setAttribute("projectsList", projects);

			if (projects.getProjects().size() == 1) {
				loadingPage = "/page/project.jsp";
			}

			request.getRequestDispatcher(loadingPage).forward(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Credentials credentials = Commons.getCredentialsOrRedirect(request, response);

		if (credentials != null) {
			String path = request.getPathInfo();

			if (path != null && (path.contentEquals("/add"))) {
				Projects projects = new Projects();
				projects.addProject(1, new ProjectInfo());
				request.setAttribute("projectsList", projects);
				request.setAttribute("addMode", true);
				request.getRequestDispatcher("/page/project.jsp").forward(request, response);
			} else {
				boolean editMode = false;
				GenericQuery query = new GenericQuery();

				if (path != null) {
					if (path.equals("/mine")) {
						query.setUserId(credentials.getCrededentials().getUserId());
					} else if (path.startsWith("/user/")) {
						query.setUserId(Commons.tryParseInt(path.substring(6)));
					} else if (path.startsWith("/edit/")) {
						editMode = true;
						query.setProjectId(Commons.tryParseInt(path.substring(6)));
					} else if (path.length() > 1) {
						query.setProjectId(Commons.tryParseInt(path.substring(1)));
					}
				}

				displayProjectDetails(request, response,
						new Request<GenericQuery>(credentials.getCrededentials(), query), editMode);
			}
		}
	}
}