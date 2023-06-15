package com.ecommerce.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.ecommerce.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 1000;

	private String secretKey = "jwtTokenKey";

//	retrieve username from jwt token
	
	public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

	 private Date getExpirationDateFromToken(String token) {
	        return getClaimFromToken(token, Claims::getExpiration);
	    }

	 public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
	        final Claims claims = getAllClaimsFromToken(token);
	        return claimsResolver.apply(claims);
	    }

//	For retrieving any information from token we will need the secret key

	 public Claims getAllClaimsFromToken(String token) {
	        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	    }
	 

//	check if token has expired

	 public Boolean isTokenExpired(String token){
	        final Date expiration = getExpirationDateFromToken(token);
	        return expiration.before(new Date());
	    }

	 public String generateToken(UserDetails userDetails){
	        Map<String, Object> claims = new HashMap<>();
	        return doGenerateToken(claims, userDetails.getUsername());
	    }

//	While creating token	

//	1.Define claims of the token , like issuer ,expiration ,subject and ID
//	2.Sign JWT using HS512 alogarithm and secret key
//	3.According to Jwt compact serialization 	(https://tools.itef.org/html

	 public String doGenerateToken(Map<String, Object> claims, String subject) {
	        return Jwts.builder()
	                .setClaims(claims)
	                .setSubject(subject)
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
	                .signWith(SignatureAlgorithm.HS512, secretKey)
	                .compact();
	    }

	 public Boolean validateToken(String token, UserDetails userDetails){
	        
		 // to validate first match username and then check token expire or not
	     
		 String username = getUsernameFromToken(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }

}