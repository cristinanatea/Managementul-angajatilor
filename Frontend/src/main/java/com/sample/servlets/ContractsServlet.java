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

import com.natea.pm.dto.base.ContractInfo;
import com.natea.pm.dto.request.Request;
import com.natea.pm.dto.response.Credentials;
import com.natea.pm.dto.response.Organization;

@WebServlet("/contracts/*")
public class ContractsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final ObjectMapper mapper = new ObjectMapper();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Credentials credentials = Commons.getCredentialsOrRedirect(request, response);

		if (credentials != null) {
			String path = request.getPathInfo();
			if (path == null) {
				String contractName = request.getParameter("contractName");
				Integer dailyNorm = Commons.tryParseInt(request.getParameter("dailyNorm"));
				Integer yearlySickDays = Commons.tryParseInt(request.getParameter("yearlySickDays"));
				Integer yearlyPaidDays = Commons.tryParseInt(request.getParameter("yearlyPaidDays"));

				if ((contractName != null) && (dailyNorm != null) && (yearlySickDays != null)
						&& (yearlyPaidDays != null)) {
					ContractInfo details = new ContractInfo();
					details.setContractName(contractName);
					details.setDailyNorm(dailyNorm);
					details.setYearlyPaidDays(yearlyPaidDays);
					details.setYearlySickDays(yearlySickDays);

					String jsonString = mapper
							.writeValueAsString(new Request<ContractInfo>(credentials.getCrededentials(), details));
					String json = Commons.request(request, response, Commons.URL_ADD_CONTRACT, jsonString);
					if (json == null) {
						return;
					}
				} else {
					request.setAttribute("errorMessage", "Missing details for performing action!");
				}
			} else if (path != null && (path.startsWith("/remove/"))) {
				Integer userId = Commons.tryParseInt(path.substring(8));

				if (userId != null) {
					String jsonString = mapper
							.writeValueAsString(new Request<Integer>(credentials.getCrededentials(), userId));
					String json = Commons.request(request, response, Commons.URL_REMOVE_CONTRACT, jsonString);
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
				session.setAttribute("contractsList", org.getContracts());
				request.setAttribute("canRemove", credentials.getRights().getFullAdministration());
				request.getRequestDispatcher("/page/contracts.jsp").forward(request, response);
			}
		}
	}
}