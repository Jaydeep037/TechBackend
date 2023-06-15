package com.ecommerce.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.RoleDao;
import com.ecommerce.dao.UserDao;
import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;

@Service
public class UserService {

	@Autowired 
	private UserDao userDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleDao roleDao; 
	
	public User registerNew(User user) {
		 Role role = roleDao.findById("User").get();
		 Set<Role> roles = new HashSet<>();
		 roles.add(role);
		 user.setRole(roles);
		 user.setPassword(getEncodedPassword(user.getPassword()));
		User savedUser = userDao.save(user);
		return savedUser;
	}
	
	public void initRolesAndUsers() {
		Role adminRole = new Role();
		adminRole.setRoleName("Admin");
		adminRole.setRoleDescription("Admin Role");
		roleDao.save(adminRole);
		
		Role userRole = new Role();
		userRole.setRoleName("User");
		userRole.setRoleDescription("User Role");
		
		roleDao.save(userRole);
		
		User adminuser = new User();
		adminuser.setUserName("admin123");
		adminuser.setUserFirstName("admin");
		adminuser.setUserLastName("admin");
		adminuser.setPassword(getEncodedPassword("admin@pass"));
		Set<Role>adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminuser.setRole(adminRoles);
		
		userDao.save(adminuser);
		
//		User user = new User();
//		user.setUserName("user123");
//		user.setUserFirstName("user");
//		user.setUserLastName("user");
//		user.setPassword(getEncodedPassword("user@pass"));
//		Set<Role>userRoles = new HashSet<>();
//		userRoles.add(userRole);
//		user.setRole(userRoles);
//		
//		userDao.save(user);
	}
	
	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}
	
}
