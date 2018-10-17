package com.jai.security.radius;

import org.jasig.cas.authentication.UsernamePasswordCredential;

public class OTPUsernamePasswordCredential extends UsernamePasswordCredential {

	private String otp;

	public OTPUsernamePasswordCredential() {
	}

	public OTPUsernamePasswordCredential(String userName, String password, String otp) {
		super(userName, password);
		this.otp = otp;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

}
