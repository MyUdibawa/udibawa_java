package com.onlineservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.onlineservice.user.model.MyUserDetails;
import com.onlineservice.user.model.User;
import com.onlineservice.user.service.UserService;

@SpringBootApplication
public class ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

	private PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, UserService userService) throws Exception {
		builder.userDetailsService(username -> {
			User rootUser = userService.findByEmail(username);
			if (rootUser.getIsDeleted())
				return null;
			return new MyUserDetails(rootUser);
		}).passwordEncoder(getEncoder());
	}

}
