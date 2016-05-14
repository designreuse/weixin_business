package com.cht.emm.security.session;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistryImpl;

public class SmartSessionRegistry extends SessionRegistryImpl {

	public synchronized void registerNewSession(String sessionId,
			Object principal) {
		//
		// convert for SmartPrincipal
		//
		if (!(principal instanceof SmartPrincipal)) {
			principal = new SmartPrincipal(SecurityContextHolder.getContext()
					.getAuthentication());
		}

		// FIXME: rememberMe cause sessionId==null, won't success register
		if (sessionId != null) {
			super.registerNewSession(sessionId, principal);
		}
	}
}
