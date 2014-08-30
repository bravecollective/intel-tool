package de.schoar.braveintelserver.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import de.schoar.braveintelserver.C;
import de.schoar.braveintelserver.misc.TimerHelper;

public class SessionLookup extends TimerHelper {

	private final Map<String, SessionCacheHit> cache = new HashMap<String, SessionCacheHit>();
	private String table;

	public SessionLookup(String table) {
		this.table = table;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Failed to init MySQL driver!", e);
		}

		timerInit(C.AUTH_CLEAN_INTERVAL);
	}

	public Session validate(String token) {
		if (token == null) {
			return null;
		}
		if (token.length() == 0) {
			return null;
		}

		if (!cache.containsKey(token)) {
			Session session = lookup(token);
			cache.put(token, new SessionCacheHit(System.currentTimeMillis(),
					session));
		}

		SessionCacheHit sch = cache.get(token);
		if (sch == null) {
			return null;
		}
		return sch.getSession();
	}

	private Session lookup(String token) {
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
		Session session = null;

		try {
			ps = con.prepareStatement("SELECT * from " + this.table
					+ " where sessionId = ?");
			ps.setString(1, token);
			rs = ps.executeQuery();
			if (rs.first()) {
				session = new Session(rs.getInt("charId"),
						rs.getString("charName"), token,
						rs.getLong("createdAt"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return session;
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

		return session;
	}

	@Override
	protected void timerTick() {
		long now = System.currentTimeMillis();
		for (String token : new TreeSet<String>(cache.keySet())) {
			SessionCacheHit sch = cache.get(token);
			if (now - sch.getCachedAt() < C.AUTH_CLEAN_EXPIRE) {
				continue;
			}
			cache.remove(token);
		}
	}

}
