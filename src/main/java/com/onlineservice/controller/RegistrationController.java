package com.onlineservice.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineservice.center.model.create.ServiceCenterCreateModel;
import com.onlineservice.user.model.create.UserCreateModel;
import com.onlineservice.user.service.RegistrationService;

@RestController
@RequestMapping("signup")
@CrossOrigin
public class RegistrationController {

	private RegistrationService service;

	public RegistrationController(RegistrationService service) {
		this.service = service;
	}

	@PostMapping(path = "serviceCenter", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> registerServiceCenter(@RequestBody ServiceCenterCreateModel model) {
		return ResponseEntity.ok(service.registerServiceCenter(model));
	}

	@PostMapping(path = "customer", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> registerCustomer(@RequestBody UserCreateModel model) {
		return ResponseEntity.ok(service.registerCustomer(model));
	}

}
