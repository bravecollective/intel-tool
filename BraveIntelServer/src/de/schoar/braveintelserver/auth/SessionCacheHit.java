package de.schoar.braveintelserver.auth;

public class SessionCacheHit {

	private long cachedAt = 0;
	private Session session = null;

	public SessionCacheHit(long cachedAt, Session session) {
		this.cachedAt = cachedAt;
		this.session = session;
	}

	public long getCachedAt() {
		return cachedAt;
	}

	public Session getSession() {
		return session;
	}
}
