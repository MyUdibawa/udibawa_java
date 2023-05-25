package com.onlineservice.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineservice.request.model.PaymentMode;
import com.onlineservice.request.model.create.ReplacementPartCreateModel;
import com.onlineservice.request.resource.ServiceRequestResource;
import com.onlineservice.request.service.ServiceRequestEngineerService;

@RestController
@RequestMapping("serviceEngineer")
@CrossOrigin
public class ServiceEngineerController {
	private ServiceRequestEngineerService service;

	@Autowired
	public ServiceEngineerController(ServiceRequestEngineerService service) {
		this.service = service;
	}

	@GetMapping("request/waiting/all")
	public ResponseEntity<Resources<ServiceRequestResource>> getAllWaitingEngineerServiceRequest(
			Authentication authentication) {
		return ResponseEntity.ok(service.getAllWaitingEngineerServiceRequest(authentication.getName()));
	}

	@GetMapping("request/waiting")
	public ResponseEntity<PagedResources<Resource<ServiceRequestResource>>> getWaitingEngineerServiceRequest(
			Authentication authentication, Pageable pageable,
			PagedResourcesAssembler<ServiceRequestResource> assembler) {
		return ResponseEntity
				.ok(assembler.toResource(service.getWaitingEngineerServiceRequest(authentication.getName(), pageable)));
	}

	@PutMapping(path = "request/{srId}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> acceptServiceRequest(@PathVariable("srId") Long srId, Authentication authentication) {
		return ResponseEntity.ok(service.acceptServiceRequest(srId, authentication.getName()));
	}

	@PutMapping(path = "request/{srId}/start", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> startServiceRequest(@PathVariable("srId") Long srId, Authentication authentication) {
		return ResponseEntity.ok(service.startServiceRequest(srId, authentication.getName()));
	}

	@PutMapping(path = "request/{srId}/verify", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> verifyServiceRequest(@PathVariable("srId") Long srId,
			@RequestParam("otp") Integer otp, Authentication authentication) {
		return ResponseEntity.ok(service.verifyServiceRequest(srId, otp, authentication.getName()));
	}

	@PutMapping(path = "request/{srId}/completed", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> completeServiceRequest(@PathVariable("srId") Long srId,
			@RequestParam("paymentMode") PaymentMode paymentMode, @RequestBody List<ReplacementPartCreateModel> parts,
			Authentication authentication) {
		return ResponseEntity.ok(service.completeServiceRequest(srId, parts, paymentMode, authentication.getName()));
	}

	@GetMapping("request/accepted/all")
	public ResponseEntity<Resources<ServiceRequestResource>> getAllAcceptedEngineerServiceRequest(
			Authentication authentication) {
		return ResponseEntity.ok(service.getAllAcceptedEngineerServiceRequest(authentication.getName()));
	}

	@GetMapping("request/accepted")
	public ResponseEntity<PagedResources<Resource<ServiceRequestResource>>> getAcceptedEngineerServiceRequest(
			Authentication authentication, Pageable pageable,
			PagedResourcesAssembler<ServiceRequestResource> assembler) {
		return ResponseEntity.ok(
				assembler.toResource(service.getAcceptedEngineerServiceRequest(authentication.getName(), pageable)));
	}

	@GetMapping("request/completed")
	public ResponseEntity<PagedResources<Resource<ServiceRequestResource>>> getCompletedEngineerServiceRequest(
			Authentication authentication, Pageable pageable,
			PagedResourcesAssembler<ServiceRequestResource> assembler) {
		return ResponseEntity.ok(
				assembler.toResource(service.getCompletedEngineerServiceRequest(authentication.getName(), pageable)));
	}

}
