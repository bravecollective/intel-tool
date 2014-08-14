package de.schoar.braveintelserver.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.schoar.braveintelserver.C;
import de.schoar.braveintelserver.auth.Session;

public class UserServlet extends BaseServlet {

	private static final long serialVersionUID = 4468858027219606383L;

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// ----------

		Session session = authUser(req, resp);
		if (session == null) {
			return;
		}

		// ----------

		String settings = load(session);
		if (settings == null) {
			send500(resp);
			return;
		}

		resp.setStatus(200);
		resp.getOutputStream().write(settings.getBytes());
	}

	@Override
	protected void put(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// ----------

		Session session = authUser(req, resp);
		if (session == null) {
			return;
		}

		// ----------

		StringBuffer sb = new StringBuffer();
		BufferedReader br = req.getReader();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line.trim());
		}
		br.close();

		if (!store(session, sb.toString())) {
			send500(resp);
			return;
		}

		resp.setStatus(200);
		resp.getOutputStream().write("{}\n".getBytes());
	}

	private String load(Session session) {
		Connection con = null;
		try {
			con = DriverManager.getConnection(C.DATABASE_CONN, C.DATABASE_USER,
					C.DATABASE_PASS);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		String settings = null;

		try {
			ps = con.prepareStatement("SELECT value from settings WHERE charId = ?");
			ps.setInt(1, session.getCharId());
			rs = ps.executeQuery();
			if (rs.first()) {
				settings = rs.getString("value");
			} else {
				settings = "{}";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return settings;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}

		return settings;
	}

	private boolean store(Session session, String settings) {
		Connection con = null;
		try {
			con = DriverManager.getConnection(C.DATABASE_CONN, C.DATABASE_USER,
					C.DATABASE_PASS);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("UPDATE settings SET value = ? WHERE charId = ?");
			ps.setString(1, settings);
			ps.setInt(2, session.getCharId());
			if (ps.executeUpdate() == 0) {
				ps = con.prepareStatement("INSERT INTO settings (charId, charName, value) VALUES (?, ?, ?)");
				ps.setInt(1, session.getCharId());
				ps.setString(2, session.getCharName());
				ps.setString(3, settings);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}

		return true;
	}
}
