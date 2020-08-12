package com.sample.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.natea.pm.dto.base.PassUpdateDetails;
import com.natea.pm.dto.base.ProjectInfo;
import com.natea.pm.dto.base.UserInfo;
import com.natea.pm.dto.request.Request;
import com.natea.pm.dto.response.Acknowledge;
import com.natea.pm.dto.response.Credentials;
import com.natea.pm.dto.response.Organization;
import com.natea.pm.dto.response.Users;

@WebServlet("/users/*")
public class UsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final ObjectMapper mapper = new ObjectMapper();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Credentials credentials = Commons.getCredentialsOrRedirect(request, response);

		if (credentials != null) {

			String path = request.getPathInfo();
			if (path != null && (path.contentEquals("/add"))) {
				UserInfo userInfo = new UserInfo();

				userInfo.setFirstName(request.getParameter("firstname"));
				userInfo.setLastName(request.getParameter("lastname"));
				userInfo.setEmail(request.getParameter("email"));
				userInfo.setPassword(request.getParameter("password"));
				userInfo.setRoleId(Integer.parseInt(request.getParameter("roleId")));
				userInfo.setContractId(Integer.parseInt(request.getParameter("contractId")));

				String jsonString = mapper
						.writeValueAsString(new Request<UserInfo>(credentials.getCrededentials(), userInfo));
				String json = Commons.request(request, response, Commons.URL_ADD_USER, jsonString);
				if (json != null) {
					Acknowledge result = mapper.readValue(json, new TypeReference<Acknowledge>() {
					});

					GenericQuery query = new GenericQuery();
					query.setUserId(result.getId());
					displayUserDetails(request, response,
							new Request<GenericQuery>(credentials.getCrededentials(), query));
				}
			} else if (path != null && (path.startsWith("/edit/"))) {
				UserInfo userInfo = new UserInfo();

				userInfo.setFirstName(request.getParameter("firstname"));
				userInfo.setLastName(request.getParameter("lastname"));
				userInfo.setEmail(request.getParameter("email"));
				userInfo.setPassword(request.getParameter("password"));
				userInfo.setRoleId(Integer.parseInt(request.getParameter("roleId")));
				userInfo.setUserId(Integer.parseInt(request.getParameter("userId")));
				userInfo.setContractId(Integer.parseInt(request.getParameter("contractId")));
				userInfo.setAvailablePaidDays(Integer.parseInt(request.getParameter("availablePaidDays")));
				userInfo.setAvailableSickDays(Integer.parseInt(request.getParameter("availableSickDays")));

				String jsonString = mapper
						.writeValueAsString(new Request<UserInfo>(credentials.getCrededentials(), userInfo));
				String json = Commons.request(request, response, Commons.URL_UPDATE_USER, jsonString);
				if (json != null) {
					Acknowledge result = mapper.readValue(json, new TypeReference<Acknowledge>() {
					});

					response.sendRedirect(request.getServletContext().getContextPath() + request.getServletPath() + "/"
							+ result.getId());
					return;
				}
			} else if (path != null && (path.contentEquals("/update/password"))) {
				PassUpdateDetails passInfo = new PassUpdateDetails();

				passInfo.setOldPassword(request.getParameter("oldPassword"));
				passInfo.setNewPassword(request.getParameter("newPassword"));

				String jsonString = mapper
						.writeValueAsString(new Request<PassUpdateDetails>(credentials.getCrededentials(), passInfo));
				String json = Commons.request(request, response, Commons.URL_UPDATE_PASS, jsonString);
				if (json != null) {
					GenericQuery query = new GenericQuery();
					query.setUserId(credentials.getCrededentials().getUserId());
					displayUserDetails(request, response,
							new Request<GenericQuery>(credentials.getCrededentials(), query));
				}
			} else if (path != null && (path.startsWith("/reset/password/"))) {
				Integer userId = Commons.tryParseInt(path.substring(16));

				if (userId != null) {
					String jsonString = mapper
							.writeValueAsString(new Request<Integer>(credentials.getCrededentials(), userId));
					String json = Commons.request(request, response, Commons.URL_RESET_PASS, jsonString);
					if (json != null) {
						response.sendRedirect(request.getServletContext().getContextPath() + "/"
								+ request.getServletPath() + "/" + userId);
					}
				}
			} else {
				doGet(request, response);
			}
		}
	}

	private static void displayUserDetails(HttpServletRequest request, HttpServletResponse response,
			Request<GenericQuery> query)
			throws JsonGenerationException, JsonMappingException, IOException, ServletException {
		HttpSession session = request.getSession();

		String jsonString = mapper.writeValueAsString(query);
		String json = Commons.request(request, response, Commons.URL_LIST_USERS, jsonString);
		if (json != null) {
			Users users = mapper.readValue(json, new TypeReference<Users>() {
			});

			String loadingPage = "/page/users.jsp";
			request.setAttribute("usersList", users);

			if (users.getUsers().size() == 1) {
				Integer userId = (Integer) users.getUsers().keySet().toArray()[0];
				UserInfo user = users.getUsers().get(userId);
				List<ProjectInfo> projects = new ArrayList<ProjectInfo>();
				user.setUserId(userId);

				for (Integer key : users.getProjectsAssignedToUser().get(userId)) {
					projects.add(users.getProjects().get(key));
				}
				request.setAttribute("userInfo", user);
				request.setAttribute("projectsList", projects);
				loadingPage = "/page/user.jsp";
			}

			request.getRequestDispatcher(loadingPage).forward(request, response);
		}
	}

	private static Users getUsers(HttpServletRequest request, HttpServletResponse response, Request<GenericQuery> query)
			throws JsonGenerationException, JsonMappingException, IOException, ServletException {
		HttpSession session = request.getSession();

		String jsonString = mapper.writeValueAsString(query);
		String json = Commons.request(request, response, Commons.URL_LIST_USERS, jsonString);
		if (json != null) {
			Users users = mapper.readValue(json, new TypeReference<Users>() {
			});

			return users;
		}

		return null;
	}

	Organization getOrganizationDetails(HttpServletRequest request, HttpServletResponse response,
			Credentials credentials)
			throws JsonGenerationException, JsonMappingException, IOException, ServletException {
		Request<Integer> query = new Request<Integer>(credentials.getCrededentials());

		String jsonString = mapper.writeValueAsString(query);
		String json = Commons.request(request, response, Commons.URL_LIST_ORGANIZATION, jsonString);
		if (json != null) {
			Organization organization = mapper.readValue(json, new TypeReference<Organization>() {
			});

			return organization;
		}

		return null;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Credentials credentials = Commons.getCredentialsOrRedirect(request, response);

		if (credentials != null) {
			String path = request.getPathInfo();
			if (path != null && (path.contentEquals("/add"))) {
				request.setAttribute("userInfo", new UserInfo());
				request.setAttribute("addMode", true);

				Organization org = getOrganizationDetails(request, response, credentials);

				if (org != null) {
					request.setAttribute("rolesList", org.getRoles());
					request.setAttribute("contractsList", org.getContracts());

					request.getRequestDispatcher("/page/user.jsp").forward(request, response);
					return;
				}
			} else if (path != null && (path.startsWith("/edit/"))) {
				Integer userId = Commons.tryParseInt(path.substring(6));
				if (userId != null) {
					GenericQuery query = new GenericQuery();
					query.setUserId(userId);

					Users users = getUsers(request, response,
							new Request<GenericQuery>(credentials.getCrededentials(), query));

					if (users.getUsers().size() == 1) {
						UserInfo user = users.getUsers().get(userId);
						user.setUserId(userId);

						request.setAttribute("userInfo", user);
						request.setAttribute("editMode", true);
						Organization org = getOrganizationDetails(request, response, credentials);

						if (org != null) {
							request.setAttribute("rolesList", org.getRoles());
							request.setAttribute("contractsList", org.getContracts());

							request.getRequestDispatcher("/page/user.jsp").forward(request, response);
							return;
						}
					}
				}
				response.sendRedirect(request.getServletContext().getContextPath() + "/" + request.getServletPath());
			} else if (path != null && (path.contentEquals("/update/password"))) {
				request.getRequestDispatcher("/page/manage_password.jsp").forward(request, response);
			} else {
				GenericQuery query = new GenericQuery();

				if (path != null) {
					if (path.equals("/me")) {
						query.setUserId(credentials.getCrededentials().getUserId());
					} else if (path.startsWith("/role/")) {
						query.setRoleId(Commons.tryParseInt(path.substring(6)));
					} else if (path.startsWith("/contract/")) {
						query.setContractId(Commons.tryParseInt(path.substring(10)));
					} else if (path.length() > 1) {
						query.setUserId(Commons.tryParseInt(path.substring(1)));
					}
				}

				displayUserDetails(request, response, new Request<GenericQuery>(credentials.getCrededentials(), query));
			}
		}
	}
}