package com.onlineservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.onlineservice.user.resource.UserResource;
import com.onlineservice.user.service.UserService;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

	private UserService service;

	@Autowired
	public UserController(UserService userService) {
		this.service = userService;
	}

	@GetMapping("")
	public ResponseEntity<UserResource> getLoggedIn(Authentication authentication) {
		return ResponseEntity.ok(service.getLoggedIn(authentication.getName()));
	}

	@PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateUser(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("phoneNumber") String phoneNumber,
			Authentication authentication) {
		return ResponseEntity.ok(service.updateUser(firstName, lastName, phoneNumber, authentication.getName()));
	}

	@PutMapping(path = "profilePic", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> uploadProfilePic(@RequestParam("profilePic") MultipartFile file,
			Authentication authentication) {
		return ResponseEntity.ok(service.updateProfilePic(file, authentication.getName()));
	}

	@PutMapping(path = "password", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> changePassword(@RequestParam("password") String password,
			Authentication authentication) {
		return ResponseEntity.ok(service.changePassword(password, authentication.getName()));
	}
}
