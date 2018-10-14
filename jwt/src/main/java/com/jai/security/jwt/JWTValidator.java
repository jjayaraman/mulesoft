package com.jai.security.jwt;

/**
 * JWT token validator
 * 
 * 
 * @author jay
 *
 */
public class JWTValidator {

	
	
	public boolean validateJWT(String bearerToken) {
		System.out.println("Bearer token : " +bearerToken);
		
		return true;
	}
	
	
	
	public static void main(String[] args) {

		JWTValidator validator = new JWTValidator();
		//validator.validateJWT(bearerToken);
		System.out.println("main called..");
	}

}
