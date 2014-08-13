package de.schoar.braveintelserver.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.stream.MalformedJsonException;

import de.schoar.braveintelserver.C;
import de.schoar.braveintelserver.auth.Session;
import de.schoar.braveintelserver.data.IntelResponse;
import de.schoar.braveintelserver.data.IntelUpload;
import de.schoar.braveintelserver.misc.Helper;

public class ReportServlet extends BaseServlet {

	private static final long serialVersionUID = 3361775367429183015L;

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// ----------

		String token = Helper.getCookie(C.AUTH_COOKIE_NAME, req.getCookies());
		Session session = ServletListener.getUserLookup().validate(token);
		if (session == null) {
			send401(resp);
			return;
		}

		// ----------

		ServletListener.getViwerCounter().add(session.getCharName());

		long rangeFrom = Helper.parseLongOrZero(req.getParameter("since"));
		long rangeTo = System.currentTimeMillis();

		// ----------

		IntelResponse res = new IntelResponse();
		res.timestamp = rangeTo;
		res.reports = ServletListener.getReportStorage()
				.get(rangeFrom, rangeTo);
		res.submitterCount = ServletListener.getUploaderCounter().getCount();
		res.viewerCount = ServletListener.getViwerCounter().getCount();

		resp.setStatus(200);
		resp.getOutputStream().write(new Gson().toJson(res).getBytes());
	}

	@Override
	protected void put(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		IntelUpload upload = null;

		try {
			upload = new Gson().fromJson(req.getReader(), IntelUpload.class);
		} catch (MalformedJsonException e) {
			e.printStackTrace();
			send500(resp);
			return;
		}

		Session session = ServletListener.getUploaderLookup().validate(
				upload.token);
		if (session == null) {
			send401(resp);
			return;
		}

		// if (upload.version.length() == 0
		// || "Development".equals(upload.version)) {
		// send426(resp);
		// return;
		// }

		System.out.println("PUT: " + upload.text + " -- "
				+ session.getCharName() + " v" + upload.version + " ["
				+ req.getHeader("X-Real-IP") + "]");

		if ("stop".equals(upload.status)) {
			ServletListener.getUploaderCounter().remove(upload.token);
		} else {
			ServletListener.getUploaderCounter().add(upload.token);
		}

		ServletListener.getReportStorage().add(upload.text);

		resp.setStatus(200);
		resp.getOutputStream().write("OK\n".getBytes());
	}

}
