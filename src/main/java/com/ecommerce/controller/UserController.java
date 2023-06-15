package com.ecommerce.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entity.User;
import com.ecommerce.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostConstruct
	public void initRolesAndUsers() {
		userService.initRolesAndUsers();
	}

	@PostMapping("/registerNew")
	public User registerNew(@RequestBody User user) {
		User registerNew = userService.registerNew(user);
		return registerNew;

	}

	@GetMapping("/forAdmin")
//	@PreAuthorize("hasRole('Admin')")
	public String forAdmin() {
		return "This URL is only accessible for Admin";
	}

	@GetMapping("/forUser")
//	@PreAuthorize("hasRole('User')")
	public String forUser() {
		return "This URL is only accessible for User";
	}
}
