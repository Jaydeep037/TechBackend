package com.ecommerce.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.UserDao;
import com.ecommerce.entity.JwtRequest;
import com.ecommerce.entity.JwtResponse;
import com.ecommerce.entity.User;
import com.ecommerce.security.CustomUserDetailService;
import com.ecommerce.security.JwtTokenHelper;

@Service
public class JwtService {

	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
		String userName = jwtRequest.getUserName();	
		String userPassword = jwtRequest.getUserPassword();
		authenticate(userName, userPassword);
		final UserDetails userDetails = customUserDetailService.loadUserByUsername(userName);
		String newGeneratedToken = jwtTokenHelper.generateToken(userDetails);
		User user = userDao.findById(userName).get();
		return new JwtResponse(user, newGeneratedToken);
	}
	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//		User user = userDao.findById(username).get();
//		if(user != null) {
//			return new org.springframework.security.core.userdetails.User(
//					user.getPassword(),
//					user.getPassword(),
//					getAuthorities(user));
//		}else {
//			throw new UsernameNotFoundException("UserName not found");
//		}
//	}
//	
//	private Set getAuthorities(User user) {
//		Set authorities = new HashSet<>();
//		user.getRole().forEach(role ->{
//			authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
//		});
//		return authorities;
//		
//	}

	public void authenticate(String userName,String password) throws Exception {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
		try {
			
		} catch (DisabledException e) {
			throw new Exception("User is disabled");
		}catch (Exception e) {
			throw new Exception("Bad credentials");
		}
	}
}
