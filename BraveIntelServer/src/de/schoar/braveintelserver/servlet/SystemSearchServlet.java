package de.schoar.braveintelserver.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import de.schoar.braveintelserver.auth.Session;
import de.schoar.braveintelserver.data.SolarSystem;

public class SystemSearchServlet extends BaseServlet {

	private static final long serialVersionUID = 3870380836762863921L;

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// ----------

		Session session = authUser(req, resp);
		if (session == null) {
			return;
		}

		// ----------

		String query = req.getParameter("q");
		if (query == null || query.length() == 0) {
			send404(resp);
			return;
		}

		List<SolarSystem> systems = ServletListener.getSystemStorage()
				.findMatchingSystems(query);

		resp.setStatus(200);
		resp.getOutputStream().write(new Gson().toJson(systems).getBytes());
	}

	@Override
	protected void put(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		send404(resp);
	}

}
