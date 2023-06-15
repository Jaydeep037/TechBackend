//package com.ecommerce.utils;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
////import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
////import com.ecommerce.service.JwtService;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//@Component
//public class JwtUtil {
//	
////	private static final String SECRET_KEY = "learn_programming_yourself";
////	private static final Integer TOKEN_VALIDITY= 3600 * 5;
//
////	@Autowired 
////	private JwtService jwtService;
//    private String secretKey = "ecommercejaydeep";
// 
//    public static final long JWT_TOKEN_VALIDITY = 1 * 60 * 60;
// 
// 
//    //validate token
//    public Boolean validateToken(String token, UserDetails userDetails){
//        // to validate first match username and then check token expire or not
//        String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
// 
//    // fetch username from token
//    public String getUsernameFromToken(String token) {
//        return getClaimsFromToken(token, Claims::getSubject);
//    }
// 
//    public Boolean isTokenExpired(String token){
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
// 
//   // check whether the token is expired or not
//    private Date getExpirationDateFromToken(String token) {
//        return getClaimsFromToken(token, Claims::getExpiration);
//    }
// 
//    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver){
//        final Claims claims = getAllClaimsFromToken(token);
//        return claimsResolver.apply(claims);
//    }
// 
//    public Claims getAllClaimsFromToken(String token) {
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
//    }
// 
//    //generate token
//    public String generateToken(UserDetails userDetails){
//        Map<String, Object> claims = new HashMap<>();
//        return doGenerateToken(claims, userDetails.getUsername());
//    }
// 
//    public String doGenerateToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
//                .signWith(SignatureAlgorithm.HS512, secretKey)
//                .compact();
//    }
//}
