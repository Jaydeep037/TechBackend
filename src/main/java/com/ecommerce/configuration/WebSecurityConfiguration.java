package com.ecommerce.configuration;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.security.JwtAuthenticationEntryPoint;
import com.ecommerce.security.JwtAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter	 {

	@Autowired
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	JwtAuthenticationFilter jwtRequestFilter;
	
	@Autowired
	UserDetailsService jwtService;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	protected  void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors();
		httpSecurity.csrf().disable()
		.authorizeRequests()
		.antMatchers("/authenticate").permitAll()
		.antMatchers("/allProducts").permitAll()
		.antMatchers("/registerNew").permitAll()
//		.antMatchers("/addNewProduct").permitAll()
//		.antMatchers("/deleteProducts/{productId}").permitAll()
		.antMatchers("/forAdmin").hasAnyAuthority("Admin")
	    .antMatchers("/forUser").hasAnyAuthority("Admin","User")
	    .antMatchers("/placeorder").hasAnyAuthority("User")
	    .antMatchers("/addNewProduct","/deleteProducts/{productId}","/getProductDetailsById/{productId}").hasAnyAuthority("Admin")
//		.antMatchers(HttpHeaders.ALLOW).permitAll()
//		.antMatchers(HttpMethod.GET).permitAll()//  Only access get api without login
		.anyRequest().authenticated()
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(jwtAuthenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
//		httpSecurity.csrf().disable()
		// dont authenticate this particular request
//		.authorizeRequests().antMatchers("/authenticate").permitAll().
		// all other requests need to be authenticated
//		anyRequest().authenticated().and().
		// make sure we use stateless session; session won't be used to
		// store user's state.
//		exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
//		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(jwtService).passwordEncoder(passwordEncoder());
	}
}
