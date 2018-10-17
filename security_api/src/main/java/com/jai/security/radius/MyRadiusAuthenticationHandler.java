package com.jai.security.radius;

import java.security.GeneralSecurityException;
import java.util.List;

import javax.security.auth.login.FailedLoginException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jasig.cas.adaptors.radius.RadiusServer;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.SimplePrincipal;

/**
 * 
 * This is a copy of
 * org.jasig.cas.adaptors.radius.authentication.handler.support.RadiusAuthenticationHandler
 * class.
 * 
 * Only change is instead of credential.password it uses credential.otp to
 * authenticate against the RadiusServer
 * 
 * 
 * @author jjayarman
 * 
 */
public class MyRadiusAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {

	/** Array of RADIUS servers to authenticate against. */
	@NotNull
	@Size(min = 1)
	private List<RadiusServer> servers;

	/**
	 * Determines whether to fail over to the next configured RadiusServer if
	 * there was an exception.
	 */
	private boolean failoverOnException;

	/**
	 * Determines whether to fail over to the next configured RadiusServer if
	 * there was an authentication failure.
	 */
	private boolean failoverOnAuthenticationFailure;

	@Override
	protected final HandlerResult authenticateUsernamePasswordInternal(final UsernamePasswordCredential credential)
			throws GeneralSecurityException, PreventedException {

		OTPUsernamePasswordCredential otpCredential = null;

		if (credential instanceof OTPUsernamePasswordCredential) {
			otpCredential = (OTPUsernamePasswordCredential) credential;
		}

		final String username = otpCredential.getUsername();
		for (final RadiusServer radiusServer : this.servers) {
			logger.debug("Attempting to authenticate {} at {}", username, radiusServer);
			try {
				if (radiusServer.authenticate(username, otpCredential.getOtp())) {
					return createHandlerResult(otpCredential, new SimplePrincipal(username), null);
				}

				if (!this.failoverOnAuthenticationFailure) {
					throw new FailedLoginException();
				}
				logger.debug("failoverOnAuthenticationFailure enabled -- trying next server");
			} catch (final PreventedException e) {
				if (!this.failoverOnException) {
					throw e;
				}
				logger.warn("failoverOnException enabled -- trying next server.", e);
			}
		}
		throw new FailedLoginException();
	}

	/**
	 * Determines whether to fail over to the next configured RadiusServer if
	 * there was an authentication failure.
	 * 
	 * @param failoverOnAuthenticationFailure
	 *            boolean on whether to failover or not.
	 */
	public final void setFailoverOnAuthenticationFailure(final boolean failoverOnAuthenticationFailure) {
		this.failoverOnAuthenticationFailure = failoverOnAuthenticationFailure;
	}

	/**
	 * Determines whether to fail over to the next configured RadiusServer if
	 * there was an exception.
	 * 
	 * @param failoverOnException
	 *            boolean on whether to failover or not.
	 */
	public final void setFailoverOnException(final boolean failoverOnException) {
		this.failoverOnException = failoverOnException;
	}

	public final void setServers(final List<RadiusServer> servers) {
		this.servers = servers;
	}
}
