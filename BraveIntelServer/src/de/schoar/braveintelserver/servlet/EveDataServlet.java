package de.schoar.braveintelserver.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import de.schoar.braveintelserver.auth.Session;
import de.schoar.braveintelserver.eveapi.EveResponse;
import de.schoar.braveintelserver.eveapi.KillData;

public class EveDataServlet extends BaseServlet {

	private static final long serialVersionUID = -3096796063220965645L;

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

		List<String> systems = ServletListener.getMapStorage().getSystems(
				region + ".svg.json");
		if (systems == null) {
			send404(resp);
			return;
		}

		EveResponse res = new EveResponse();

		Map<String, Integer> jumps = ServletListener.getEveJumps().getJumps();
		for (String key : jumps.keySet()) {
			if (!systems.contains(key)) {
				continue;
			}
			res.jumps.put(key, jumps.get(key));
		}

		Map<String, KillData> kills = ServletListener.getEveKills().getKills();
		for (String key : kills.keySet()) {
			if (!systems.contains(key)) {
				continue;
			}
			res.kills.put(key, kills.get(key));
		}

		resp.setStatus(200);
		resp.getOutputStream().write(new Gson().toJson(res).getBytes());
	}

	@Override
	protected void put(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		send404(resp);
	}

}
