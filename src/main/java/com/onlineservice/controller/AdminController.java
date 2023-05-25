package com.onlineservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineservice.center.resource.ServiceCenterResource;
import com.onlineservice.user.service.AdminServiceCenterService;

@RestController
@RequestMapping("admin")
@CrossOrigin
public class AdminController {

	private AdminServiceCenterService service;

	@Autowired
	public AdminController(AdminServiceCenterService service) {
		this.service = service;
	}

	@GetMapping("serviceCenter")
	public ResponseEntity<PagedResources<Resource<ServiceCenterResource>>> getServiceCenter(
			@RequestParam(name = "isVerified", required = false) Boolean isVerified, Authentication authentication,
			Pageable pageable, PagedResourcesAssembler<ServiceCenterResource> assembler) {
		return ResponseEntity
				.ok(assembler.toResource(service.getServiceCenter(isVerified, authentication.getName(), pageable)));
	}

	@GetMapping("serviceCenter/all")
	public ResponseEntity<Resources<ServiceCenterResource>> getAllServiceCenter(
			@RequestParam(name = "isVerified", required = false) Boolean isVerified, Authentication authentication) {
		return ResponseEntity.ok(service.getAllServiceCenter(isVerified, authentication.getName()));
	}

	@PutMapping(path = "serviceCenter/{scId}/verify", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> setVerifyServiceCenter(@PathVariable("scId") Long scId,
			@RequestParam("verify") Boolean verify, Authentication authentication) {
		return ResponseEntity.ok(service.setVerifyServiceCenter(scId, verify, authentication.getName()));
	}

	@DeleteMapping(path = "serviceCenter/{scId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteServiceCenter(@PathVariable("scId") Long scId, Authentication authentication) {
		return ResponseEntity.ok(service.deleteServiceCenter(scId, authentication.getName()));
	}
}
