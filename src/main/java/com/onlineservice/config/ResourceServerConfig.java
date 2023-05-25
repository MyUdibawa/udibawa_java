package com.onlineservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/oauth/token")
				.permitAll()
				//
				.antMatchers("/signup/**", "/forgotPassword/**","/favicon.ico").permitAll()
				//
				.antMatchers("/matrimony/**", "/friend/**", "/post/**").hasAnyAuthority("USER")
				.antMatchers("/serviceCenterAdmin/**").hasAnyAuthority("SERVICE_CENTER_ADMIN")
				// ADMIN Operation
				.antMatchers("/admin/**").hasAuthority("ADMIN")
				// Both User
				.antMatchers("/user/**", "/serviceCenter/**","/serviceRequest/**")
				.hasAnyAuthority("ADMIN", "SERVICE_CENTER_ADMIN", "SERVICE_ENGINEER", "CUSTOMER");
	}

}
