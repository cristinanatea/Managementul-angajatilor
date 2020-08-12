package com.sample.servlets;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.natea.pm.dto.base.GenericQuery;
import com.natea.pm.dto.base.LeaveRequestInfo;
import com.natea.pm.dto.request.Request;
import com.natea.pm.dto.response.Credentials;
import com.natea.pm.dto.response.Requests;

@WebServlet("/requests/*")
public class RequestsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final ObjectMapper mapper = new ObjectMapper();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Credentials credentials = Commons.getCredentialsOrRedirect(request, response);

		if (credentials != null) {
			String path = request.getPathInfo();
			if (path == null) {
				LeaveRequestInfo query = new LeaveRequestInfo();

				Date start = Commons.parseDate(request.getParameter("dateStart"));
				Date end = Commons.parseDate(request.getParameter("dateEnd"));

				if ((start == null) || ((start != null) && (end != null) && (start.getTime() > end.getTime()))) {
					request.setAttribute("errorMessage", "Invalid dates entered");
					request.getRequestDispatcher("/error.jsp").forward(request, response);
					doGet(request, response);
					return;
				}

				query.setDateStart(start);
				query.setDateEnd(end);
				query.setComment(request.getParameter("comment"));
				query.setUserId(credentials.getCrededentials().getUserId());

				String jsonString = mapper
						.writeValueAsString(new Request<LeaveRequestInfo>(credentials.getCrededentials(), query));
				String json = Commons.request(request, response, Commons.URL_ADD_REQUEST, jsonString);
				if (json != null) {
					doGet(request, response);
				}

				return;
			} else if (path != null && (path.startsWith("/approve/"))) {
				Integer userId = Commons.tryParseInt(path.substring(9));

				if (userId != null) {
					String jsonString = mapper
							.writeValueAsString(new Request<Integer>(credentials.getCrededentials(), userId));
					String json = Commons.request(request, response, Commons.URL_APPROVE_REQUEST, jsonString);
					if (json == null) {
						return;
					}
				}
			} else if (path != null && (path.startsWith("/reject/"))) {
				Integer userId = Commons.tryParseInt(path.substring(8));

				if (userId != null) {
					String jsonString = mapper
							.writeValueAsString(new Request<Integer>(credentials.getCrededentials(), userId));
					String json = Commons.request(request, response, Commons.URL_REJECT_REQUEST, jsonString);
					if (json == null) {
						return;
					}
				}
			} else if (path != null && (path.startsWith("/cancel/"))) {
				Integer userId = Commons.tryParseInt(path.substring(8));

				if (userId != null) {
					String jsonString = mapper
							.writeValueAsString(new Request<Integer>(credentials.getCrededentials(), userId));
					String json = Commons.request(request, response, Commons.URL_CANCEL_REQUEST, jsonString);
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
			GenericQuery query = new GenericQuery();

			String path = request.getPathInfo();
			if ((path == null) || (!path.equals("/all"))) {
				request.setAttribute("enableSubmit", true);
				query.setUserId(credentials.getCrededentials().getUserId());
			}

			String jsonString = mapper
					.writeValueAsString(new Request<GenericQuery>(credentials.getCrededentials(), query));
			String json = Commons.request(request, response, Commons.URL_LIST_REQUESTS, jsonString);
			if (json != null) {
				Requests requests = mapper.readValue(json, new TypeReference<Requests>() {
				});

				session.setAttribute("canApprove", credentials.getRights().getFullAdministration()
						|| credentials.getRights().getManageLeaveRequests());
				session.setAttribute("requests", requests);
				request.getRequestDispatcher("/page/requests.jsp").forward(request, response);
			}
		}
	}
}