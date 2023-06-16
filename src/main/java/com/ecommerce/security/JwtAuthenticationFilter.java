package com.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;


	@Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {

		//    	1 get Token 
    	
    	String requestToken = request.getHeader("Authorization");
    	
//    	Bearer 2435546
    	System.out.println(requestToken);
		
    	String username=null;
    	String token = null;
    	if(requestToken!=null && requestToken.startsWith("Bearer")) {
    		token = requestToken.substring(7);
    		username = this.jwtTokenHelper.getUsernameFromToken(token);
    		try {
				
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get Jwt token");
			}
    		catch (ExpiredJwtException e) {
    			System.out.println("JWT token has expired");
    		}
    		catch (MalformedJwtException e) {
    			System.out.println("Invalid JWT");
    		}
    	}else {
    		System.out.println("JWT token does not begin with Bearer");
    	}
    	
//    	Once we get the token , now validate
    	if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
    		UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
    		
    		if(this.jwtTokenHelper.validateToken(token, userDetails)) {
    			
//    			chal raha haii
//    			authentication karna hai
    			

    			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
//				// After setting the Authentication in the context, we specify
//				// that the current user is authenticated. So it passes the
//				// Spring Security Configurations successfully.
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    		}else {
    			System.out.println("Invalid JWT token");
    		}
    	}else {
    		System.out.println("Username is null or Context is not null");
    	}
    		filterChain.doFilter(request, response);
    		
//        final Optional<String> token = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION));
//        if(token.isPresent() && jwtService.isTokenValid(cleanToken(token.get()))) {
//            final Optional<String> username = jwtService.getUsernameFromToken(cleanToken(token.get()));
//            if(username.isPresent()) {
//                Authentication authentication = new UsernamePasswordAuthenticationToken(username.get(), null, Collections.emptyList());
//                securityContextAccessor.getContext().setAuthentication(authentication);
//            }
//        }
//        filterChain.doFilter(request, response);
	}
}