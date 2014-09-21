package de.schoar.braveintelserver.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.schoar.braveintelserver.auth.Session;

public class ReloadServlet extends BaseServlet {

	private static final long serialVersionUID = 2014915983817701342L;

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// ----------

		Session session = authUser(req, resp);
		if (session == null) {
			return;
		}

		// ----------

		ServletListener.getMapStorage().load();
		ServletListener.getAnalyzer().load();

		resp.setStatus(200);
		resp.getOutputStream().write("OK".getBytes());
		ServletListener.getReportStorage().inject("kiu Nakamura",
				"The Brave Intel Map server has been updated...");
	}

	@Override
	protected void put(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		send404(resp);
	}

}
