package com.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.ecommerce.dao.UserDao;
import com.ecommerce.entity.JwtRequest;
import com.ecommerce.entity.User;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	UserDao userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//loading user from the db
		User user = userRepo.findById(username).get();
		if(user !=null) {
			return user;
		}else {
			throw new UsernameNotFoundException("UserName not found");
		}
	}

}
