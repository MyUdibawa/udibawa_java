package com.onlineservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineservice.user.service.ForgotPasswordService;

@RestController
@RequestMapping("forgotPassword")
@CrossOrigin
public class ForgotPasswordController {

	private ForgotPasswordService service;

	@Autowired
	public ForgotPasswordController(ForgotPasswordService service) {
		this.service = service;
	}

	@PutMapping(path = "request", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> requestForgetPassword(@RequestParam("email") String email) {
		return ResponseEntity.ok(service.requestForgetPassword(email));
	}

	@PutMapping(path = "change", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> changePassword(@RequestParam("email") String email, @RequestParam("otp") Integer otp,
			@RequestParam("password") String password) {
		return ResponseEntity.ok(service.changePassword(email, otp, password));
	}

}
