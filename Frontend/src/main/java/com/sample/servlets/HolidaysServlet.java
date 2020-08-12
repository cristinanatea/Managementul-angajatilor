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

import com.natea.pm.dto.base.GenericQuery;
import com.natea.pm.dto.base.HolidayInfo;
import com.natea.pm.dto.request.Request;
import com.natea.pm.dto.response.Credentials;
import com.natea.pm.dto.response.Holidays;

@WebServlet("/holidays/*")
public class HolidaysServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final ObjectMapper mapper = new ObjectMapper();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Credentials credentials = Commons.getCredentialsOrRedirect(request, response);

		if (credentials != null) {
			String entries = request.getParameter("action");
			String date = request.getParameter("date");
			String name = request.getParameter("name");

			if (entries != null && date != null && name != null
					&& (entries.equals("add") || entries.equals("remove"))) {
				HolidayInfo details = new HolidayInfo();
				details.setDate(Commons.parseDate(date));
				details.setName(name);

				String jsonString = mapper
						.writeValueAsString(new Request<HolidayInfo>(credentials.getCrededentials(), details));
				String json = Commons.request(request, response,
						entries.equals("add") ? Commons.URL_ADD_HOLIDAY : Commons.URL_REMOVE_HOLIDAY, jsonString);
				if (json == null) {
					return;
				}
			} else {
				request.setAttribute("errorMessage", "Missing details for performing action!");
			}

			doGet(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Credentials credentials = Commons.getCredentialsOrRedirect(request, response);

		if (credentials != null) {
			String jsonString = mapper
					.writeValueAsString(new Request<GenericQuery>(credentials.getCrededentials(), new GenericQuery()));
			String json = Commons.request(request, response, Commons.URL_LIST_HOLIDAYS, jsonString);
			if (json != null) {
				Holidays holidays = mapper.readValue(json, new TypeReference<Holidays>() {
				});
				session.setAttribute("holidaysList", holidays);
				request.getRequestDispatcher("/page/holidays.jsp").forward(request, response);
			}
		}
	}
}