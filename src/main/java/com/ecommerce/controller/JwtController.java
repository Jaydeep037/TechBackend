package com.ecommerce.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entity.JwtRequest;
import com.ecommerce.entity.JwtResponse;
import com.ecommerce.service.JwtService;

@RestController
@CrossOrigin
public class JwtController {

	@Autowired
	JwtService jwtService;
	
	@PostMapping("/authenticate")
	public JwtResponse createToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		JwtResponse response = jwtService.createJwtToken(jwtRequest);
		return response;
	}
	
	@PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        // Perform any necessary cleanup or additional logout operations
		SecurityContextHolder.getContext().setAuthentication(null);
		request.getSession().invalidate();
    }
}
