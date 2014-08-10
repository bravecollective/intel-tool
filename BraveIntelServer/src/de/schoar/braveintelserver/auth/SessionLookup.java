package de.schoar.braveintelserver.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import de.schoar.braveintelserver.C;

public class SessionLookup {

	private final Timer timerClean = new Timer(this.getClass()
			.getCanonicalName(), true);

	private final Map<String, SessionCacheHit> cache = new HashMap<String, SessionCacheHit>();
	private String table;

	public SessionLookup(String table) {
		this.table = table;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Failed to init MySQL driver!", e);
		}

		timerClean.schedule(new TimerTask() {
			@Override
			public void run() {
				clean();
			}
		}, C.AUTH_CLEAN_INTERVAL, C.AUTH_CLEAN_INTERVAL);
	}

	public void stop() {
		timerClean.cancel();
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

	private void clean() {
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
