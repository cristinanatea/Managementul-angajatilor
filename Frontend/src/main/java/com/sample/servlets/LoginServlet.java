package com.sample.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;

import com.natea.pm.dto.base.LoginDetails;
import com.natea.pm.dto.request.Request;
import com.natea.pm.dto.response.Credentials;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		ObjectMapper mapper = new ObjectMapper();

		LoginDetails loginInfo = new LoginDetails();
		loginInfo.setEmail(request.getParameter("email"));
		loginInfo.setPassword(request.getParameter("password"));
		String jsonString = mapper.writeValueAsString(new Request<LoginDetails>(null, loginInfo));

		Client client = Client.create();
		WebResource webResource = client.resource(Commons.URL_LOGIN);
		ClientResponse status = webResource.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,
				jsonString);

		if (status.getStatus() != 200) {
			request.setAttribute("errorCode", status.getStatus());
			request.setAttribute("errorType", "Server");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		} else {
			String json = status.getEntity(String.class);

			Credentials credentials = mapper.readValue(json, Credentials.class);

			if ((credentials == null) || (credentials.getErrorCode() != 0)) {
				request.setAttribute("message", "Invalid username or password");
				request.getRequestDispatcher("/page/login.jsp").forward(request, response);
			} else {
				System.out.println("User logged in: " + credentials);
				session.setAttribute("credentials", credentials);
				request.getRequestDispatcher("/page/main.jsp").forward(request, response);
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/page/login.jsp").forward(req, resp);
	}
}