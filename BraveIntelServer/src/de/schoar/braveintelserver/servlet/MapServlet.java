package de.schoar.braveintelserver.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.schoar.braveintelserver.auth.Session;

public class MapServlet extends BaseServlet {

	private static final long serialVersionUID = 5071119813559087927L;

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// ----------

		Session session = authUser(req, resp);
		if (session == null) {
			return;
		}

		// ----------

		String region = req.getParameter("region");
		if (region == null || region.length() == 0) {
			send404(resp);
			return;
		}

		String map = ServletListener.getMapStorage().get(region + ".svg.json");
		if (map == null) {
			send404(resp);
			return;
		}

		resp.setStatus(200);
		resp.getOutputStream().write(map.getBytes());
	}

	@Override
	protected void put(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		send404(resp);
	}

}
