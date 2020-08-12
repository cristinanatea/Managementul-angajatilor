package com.sample.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.natea.pm.dto.base.RoleInfo;
import com.natea.pm.dto.request.Request;
import com.natea.pm.dto.response.Credentials;
import com.natea.pm.dto.response.Organization;

@WebServlet("/roles/*")
public class RolesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final ObjectMapper mapper = new ObjectMapper();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Credentials credentials = Commons.getCredentialsOrRedirect(request, response);

		if (credentials != null) {
			String path = request.getPathInfo();
			if (path == null) {
				RoleInfo role = new RoleInfo();

				role.setRoleName(request.getParameter("roleName"));
				role.setViewAllProjects(Commons.checkBooleanVal(request.getParameter("viewAllProjects")));
				role.setManageProject(Commons.checkBooleanVal(request.getParameter("manageProject")));
				role.setManageUsers(Commons.checkBooleanVal(request.getParameter("manageUsers")));
				role.setManageHolidays(Commons.checkBooleanVal(request.getParameter("manageHolidays")));
				role.setManageLeaveRequests(Commons.checkBooleanVal(request.getParameter("manageLeaveRequests")));

				String jsonString = mapper
						.writeValueAsString(new Request<RoleInfo>(credentials.getCrededentials(), role));

				String json = Commons.request(request, response, Commons.URL_ADD_ROLE, jsonString);
				if (json == null) {
					return;
				}
			} else if (path != null && (path.startsWith("/remove/"))) {
				Integer userId = Commons.tryParseInt(path.substring(8));

				if (userId != null) {
					String jsonString = mapper
							.writeValueAsString(new Request<Integer>(credentials.getCrededentials(), userId));
					String json = Commons.request(request, response, Commons.URL_REMOVE_ROLE, jsonString);
					if (json == null) {
						return;
					}
				}
			}

			response.sendRedirect(request.getServletContext().getContextPath() + request.getServletPath());
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Credentials credentials = Commons.getCredentialsOrRedirect(request, response);

		if (credentials != null) {
			String jsonString = mapper.writeValueAsString(new Request<Integer>(credentials.getCrededentials()));

			String json = Commons.request(request, response, Commons.URL_LIST_ORGANIZATION, jsonString);
			if (json != null) {
				Organization org = mapper.readValue(json, new TypeReference<Organization>() {
				});
				session.setAttribute("rolesList", org.getRoles());
				request.setAttribute("canRemove", credentials.getRights().getFullAdministration());
				request.getRequestDispatcher("/page/roles.jsp").forward(request, response);
			}
		}
	}
}