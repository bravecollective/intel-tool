package de.schoar.braveintelserver.servlet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	private static final Pattern patternVersion = Pattern
			.compile("([0-9]+)\\.([0-9]+)\\.([0-9]+)\\.([0-9]+)");

	private static int versionRequired[] = { 1, 1, 0, 5 };

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// ----------

		Session session = authUser(req, resp);
		if (session == null) {
			return;
		}

		// ----------

		ServletListener.getViewerCounter().add(session.getSessionId());

		// ----------

		long rangeFrom = Helper.parseLongOrZero(false,
				req.getParameter("since"));
		long rangeTo = System.currentTimeMillis();

		// ----------

		IntelResponse res = new IntelResponse();
		res.timestamp = rangeTo;
		res.reports = ServletListener.getReportStorage()
				.get(rangeFrom, rangeTo);
		res.submitterCount = ServletListener.getUploaderCounter().getCount();
		res.viewerCount = ServletListener.getViewerCounter().getCount();
		res.pollInterval = C.POLL_INTERVAL_BASE
				+ ((ServletListener.getViewerCounter().getCount() / C.POLL_INTERVAL_USER) * 1000);

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

		// ----------

		Session session = authUploader(upload.token, resp);
		if (session == null) {
			log("Auth Error", null, upload.version, req.getHeader("X-Real-IP"),
					upload.status);
			return;
		}

		// ----------

		if (!checkVersion(upload.version)) {
			log("Client Outdated", session.getCharName(), upload.version,
					req.getHeader("X-Real-IP"), upload.status);
			// send426(resp);
			// return;
		}

		// ----------

		log(upload.text, session.getCharName(), upload.version,
				req.getHeader("X-Real-IP"), upload.status);

		// ----------

		if ("stop".equals(upload.status)) {
			ServletListener.getUploaderCounter().remove(session.getCharName());
		}

		// ----------

		boolean reportIsValid = ServletListener.getReportStorage().add(
				session.getCharName(), upload.text);

		if (reportIsValid) {
			ServletListener.getUploaderCounter().add(session.getCharName());
		}

		// ----------

		resp.setStatus(200);
		resp.getOutputStream().write("OK\n".getBytes());
	}

	private boolean checkVersion(String version) {
		if (version == null) {
			return false;
		}
		if (version.trim().length() == 0) {
			return false;
		}

		if ("Development".equals(version)) {
			return true;
		}

		Matcher matcher = patternVersion.matcher(version);
		if (!matcher.matches()) {
			return false;
		}

		if (Helper.parseIntOrZero(false, matcher.group(1)) < versionRequired[0]) {
			return false;
		}
		if (Helper.parseIntOrZero(false, matcher.group(1)) > versionRequired[0]) {
			return true;
		}

		if (Helper.parseIntOrZero(false, matcher.group(2)) < versionRequired[1]) {
			return false;
		}
		if (Helper.parseIntOrZero(false, matcher.group(2)) > versionRequired[1]) {
			return true;
		}

		if (Helper.parseIntOrZero(false, matcher.group(3)) < versionRequired[2]) {
			return false;
		}
		if (Helper.parseIntOrZero(false, matcher.group(3)) > versionRequired[2]) {
			return true;
		}

		if (Helper.parseIntOrZero(false, matcher.group(4)) < versionRequired[3]) {
			return false;
		}
		if (Helper.parseIntOrZero(false, matcher.group(4)) > versionRequired[3]) {
			return true;
		}

		return true;
	}

	private void log(String msg, String username, String version, String ip,
			String status) {
		StringBuffer sb = new StringBuffer();
		sb.append("PUT: ");
		if (msg != null && msg.length() != 0) {
			sb.append(msg + " -- ");
		}
		if (status != null && status.length() != 0) {
			sb.append(status + " -- ");
		}
		if (username != null && username.length() != 0) {
			sb.append(username + " ");
		}
		if (version != null && version.length() != 0) {
			sb.append("(v" + version + ") ");
		}
		if (ip != null && ip.length() != 0) {
			sb.append("[" + ip + "]");
		}
		System.out.println(sb.toString());
	}
}
