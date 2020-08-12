package com.sample.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.natea.pm.dto.base.LoginDetails;
import com.natea.pm.dto.request.Request;
import com.natea.pm.dto.response.Credentials;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final ObjectMapper mapper = new ObjectMapper();

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();

		Credentials credentials = Commons.getCredentialsOrRedirect(request, response);

		if (credentials != null) {
			String jsonString = mapper.writeValueAsString(new Request<LoginDetails>(credentials.getCrededentials()));
			Commons.request(request, response, Commons.URL_LOGOUT, jsonString);
			// we don't care about the answer, the user shall be disconnected.
		}

		session.invalidate();
		response.sendRedirect(request.getServletContext().getContextPath() + "/login");
	}

}
