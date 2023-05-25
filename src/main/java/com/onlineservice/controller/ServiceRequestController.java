package com.onlineservice.controller;

import java.util.Date;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineservice.request.model.ServiceRequestStatusType;
import com.onlineservice.request.model.create.ReviewCreateModel;
import com.onlineservice.request.model.create.ServiceRequestCreateModel;
import com.onlineservice.request.resource.ServiceRequestResource;
import com.onlineservice.request.service.ServiceRequestService;

@RestController
@RequestMapping("serviceRequest")
@CrossOrigin
public class ServiceRequestController {

	private ServiceRequestService serviceRequestService;

	@Autowired
	public ServiceRequestController(ServiceRequestService serviceRequestService) {
		this.serviceRequestService = serviceRequestService;
	}

	@PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createServiceRequest(@RequestBody ServiceRequestCreateModel model,
			Authentication authentication) {
		return ResponseEntity.ok(serviceRequestService.createServiceRequest(model, authentication.getName()));
	}

	@GetMapping("{srId}")
	public ResponseEntity<ServiceRequestResource> getServiceRequest(@PathVariable("srId") Long srId,
			Authentication authentication) {
		return ResponseEntity.ok(serviceRequestService.getServiceRequest(srId, authentication.getName()));
	}

	@PostMapping(path = "{srId}/review", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> reviewServiceRequest(@PathVariable("srId") Long srId,
			@RequestBody ReviewCreateModel model, Authentication authentication) {
		return ResponseEntity.ok(serviceRequestService.reviewServiceRequest(srId, model, authentication.getName()));
	}

	@PutMapping(path = "{srId}/assign", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> assignEngineer(@PathVariable("srId") Long srId, @RequestParam("eId") Long eId,
			Authentication authentication) {
		return ResponseEntity.ok(serviceRequestService.assignEngineer(srId, eId, authentication.getName()));
	}

	@GetMapping("user")
	public ResponseEntity<PagedResources<Resource<ServiceRequestResource>>> getUserServiceRequest(
			Authentication authentication, Pageable pageable,
			PagedResourcesAssembler<ServiceRequestResource> assembler) {
		return ResponseEntity.ok(
				assembler.toResource(serviceRequestService.getUserServiceRequest(authentication.getName(), pageable)));
	}

	@GetMapping("user/all")
	public ResponseEntity<Resources<ServiceRequestResource>> getAllUserServiceRequest(Authentication authentication,
			Pageable pageable, PagedResourcesAssembler<ServiceRequestResource> assembler) {
		return ResponseEntity.ok(serviceRequestService.getAllUserServiceRequest(authentication.getName()));
	}

	@GetMapping("user/{uId}")
	public ResponseEntity<PagedResources<Resource<ServiceRequestResource>>> getUserServiceRequestAdmin(
			@PathVariable("uId") Long uId, Authentication authentication, Pageable pageable,
			PagedResourcesAssembler<ServiceRequestResource> assembler) {
		return ResponseEntity.ok(assembler
				.toResource(serviceRequestService.getUserServiceRequestAdmin(uId, authentication.getName(), pageable)));
	}

	@GetMapping("user/{uid}/all")
	public ResponseEntity<Resources<ServiceRequestResource>> getAllUserServiceRequestAdmin(
			@PathVariable("uId") Long uId, Authentication authentication) {
		return ResponseEntity.ok(serviceRequestService.getAllUserServiceRequestAdmin(uId, authentication.getName()));
	}

	@GetMapping("serviceCenter")
	public ResponseEntity<PagedResources<Resource<ServiceRequestResource>>> getServiceCenterServiceRequest(
			@RequestParam(name = "status", required = false) ServiceRequestStatusType status,
			Authentication authentication, Pageable pageable,
			PagedResourcesAssembler<ServiceRequestResource> assembler) {
		return ResponseEntity.ok(assembler.toResource(
				serviceRequestService.getServiceCenterServiceRequest(status, authentication.getName(), pageable)));
	}

	@GetMapping("serviceCenter/all")
	public ResponseEntity<Resources<ServiceRequestResource>> getAllServiceCenterServiceRequest(
			@RequestParam(name = "status", required = false) ServiceRequestStatusType status,
			Authentication authentication) {
		return ResponseEntity
				.ok(serviceRequestService.getAllServiceCenterServiceRequest(status, authentication.getName()));
	}

	@GetMapping("serviceCenter/pending/all")
	public ResponseEntity<Resources<ServiceRequestResource>> getPendingServiceRequest(Authentication authentication) {
		return ResponseEntity.ok(serviceRequestService.getPendingServiceRequest(authentication.getName()));
	}

	@GetMapping("serviceCenter/progress/all")
	public ResponseEntity<Resources<ServiceRequestResource>> getOnProgressServiceRequest(
			Authentication authentication) {
		return ResponseEntity.ok(serviceRequestService.getOnProgressServiceRequest(authentication.getName()));
	}

	@GetMapping("serviceCenter/completed/all")
	public ResponseEntity<Resources<ServiceRequestResource>> getCompletedServiceRequest(Authentication authentication) {
		return ResponseEntity.ok(serviceRequestService.getCompletedServiceRequest(authentication.getName()));
	}

	@GetMapping("serviceCenter/{scId}")
	public ResponseEntity<PagedResources<Resource<ServiceRequestResource>>> getServiceCenterServiceRequestAdmin(
			@PathVariable("scId") Long scId,
			@RequestParam(name = "status", required = false) ServiceRequestStatusType status,
			Authentication authentication, Pageable pageable,
			PagedResourcesAssembler<ServiceRequestResource> assembler) {
		return ResponseEntity.ok(assembler.toResource(serviceRequestService.getServiceCenterServiceRequestAdmin(scId,
				status, authentication.getName(), pageable)));
	}

	@GetMapping("serviceCenter/{scId}/all")
	public ResponseEntity<Resources<ServiceRequestResource>> getAllServiceCenterServiceRequestAdmin(
			@PathVariable("scId") Long scId, @RequestParam("status") ServiceRequestStatusType status,
			Authentication authentication) {
		return ResponseEntity.ok(
				serviceRequestService.getAllServiceCenterServiceRequestAdmin(scId, status, authentication.getName()));
	}

	@GetMapping("paymentHistory")
	public ResponseEntity<Resources<ServiceRequestResource>> getPaymentHistory(
			@RequestParam(name = "date", required = false) Long date, Authentication authentication) {
		return ResponseEntity.ok(serviceRequestService.getPaymentHistory(date, authentication.getName()));
	}
}