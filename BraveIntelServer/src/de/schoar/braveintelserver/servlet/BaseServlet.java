package de.schoar.braveintelserver.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.schoar.braveintelserver.C;
import de.schoar.braveintelserver.auth.Session;
import de.schoar.braveintelserver.misc.Helper;

public abstract class BaseServlet extends javax.servlet.http.HttpServlet
		implements javax.servlet.Servlet {

	private static final long serialVersionUID = -3967729827915181030L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected abstract void get(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException;

	protected abstract void put(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// ----------

		if (!ServletListener.isRunning()) {
			sendNotReady(resp);
			return;
		}

		// ----------

		get(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// ----------

		if (!ServletListener.isRunning()) {
			sendNotReady(resp);
			return;
		}

		// ----------

		put(req, resp);
	}

	protected Session authUser(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String token = Helper.getCookie(C.AUTH_COOKIE_NAME, req.getCookies());
		Session session = ServletListener.getUserLookup().validate(token);
		if (session == null && C.DEBUG) {
			return new Session(23, "Debug User", "mysecretusertoken",
					System.currentTimeMillis());
		}

		if (session == null) {
			send401(resp);
			return null;
		}

		return session;
	}

	protected Session authUploader(String token, HttpServletResponse resp)
			throws IOException {
		Session session = ServletListener.getUploaderLookup().validate(token);
		if (session == null && C.DEBUG) {
			return new Session(23, "Debug Uploader", "mysecretuploadertoken",
					System.currentTimeMillis());
		}

		if (session == null) {
			send401(resp);
			return null;
		}

		return session;
	}

	protected void sendNotReady(HttpServletResponse res) throws IOException {
		res.sendError(500, "System not ready!");
	}

	protected void send500(HttpServletResponse res) throws IOException {
		res.sendError(500, "Something went wrong!");
	}

	protected void send401(HttpServletResponse res) throws IOException {
		res.sendError(401, "The BIA is always watching!\n");
	}

	protected void send426(HttpServletResponse res) throws IOException {
		res.sendError(426, "Client is outdated. Please update.\n");
	}

	protected void send404(HttpServletResponse res) throws IOException {
		res.sendError(404, "I am lost!\n");
	}

}
