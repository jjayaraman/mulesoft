package com.jai.security.jwt;

/**
 * JWT token validator
 * 
 * 
 * @author jay
 *
 */
public class JWTValidator {


	
	public void validateJWT(String bearerToken) {
		System.out.println("Bearer token : " +bearerToken);
		if(bearerToken != null) {
			String token = bearerToken.split(" ")[1];
			System.out.println(" token : " +token);
		
		}
		
//		return true;
	}

}
