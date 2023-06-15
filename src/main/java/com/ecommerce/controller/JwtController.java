package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
}
