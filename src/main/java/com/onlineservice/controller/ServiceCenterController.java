package com.onlineservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineservice.center.model.ServiceType;
import com.onlineservice.center.resource.DetailedOfferedServiceResource;
import com.onlineservice.center.resource.ServiceCenterAddressResource;
import com.onlineservice.center.resource.ServiceCenterResource;
import com.onlineservice.center.service.ServiceCenterService;
import com.onlineservice.request.model.ServiceRequestStatusType;
import com.onlineservice.request.resource.DashboardResource;
import com.onlineservice.request.resource.ServiceRequestResource;
import com.onlineservice.request.service.ServiceRequestServiceCenterService;
import com.onlineservice.user.resource.UserResource;

@RestController
@RequestMapping("serviceCenter")
@CrossOrigin
public class ServiceCenterController {

	private ServiceCenterService service;
	private ServiceRequestServiceCenterService serviceRequestServiceCenterService;

	@Autowired
	public ServiceCenterController(ServiceCenterService service,
			ServiceRequestServiceCenterService serviceRequestServiceCenterService) {
		this.service = service;
		this.serviceRequestServiceCenterService = serviceRequestServiceCenterService;
	}

	@GetMapping("{scId}")
	public ResponseEntity<ServiceCenterResource> getServiceCenter(@PathVariable("scId") Long scId,
			Authentication authentication) {
		return ResponseEntity.ok(service.getServiceCenter(scId, authentication.getName()));
	}

	@GetMapping("{scId}/address")
	public ResponseEntity<ServiceCenterAddressResource> getServiceCenterAddress(@PathVariable("scId") Long scId,
			Authentication authentication) {
		return ResponseEntity.ok(service.getServiceCenterAddress(scId, authentication.getName()));
	}

	@GetMapping("{scId}/admin")
	public ResponseEntity<UserResource> getServiceCenterAdmin(@PathVariable("scId") Long scId,
			Authentication authentication) {
		return ResponseEntity.ok(service.getServiceCenterAdmin(scId, authentication.getName()));
	}

	@GetMapping("offeredService/all")
	public ResponseEntity<Resources<DetailedOfferedServiceResource>> getAllOfferedServices(
			@RequestParam("pinCode") String pinCode, @RequestParam("type") ServiceType type,
			Authentication authentication) {
		return ResponseEntity.ok(service.getAllOfferedServices(pinCode, type, authentication.getName()));
	}

	@GetMapping("offeredService")
	public ResponseEntity<PagedResources<Resource<DetailedOfferedServiceResource>>> getOfferedServices(
			@RequestParam("pinCode") String pinCode, @RequestParam("type") ServiceType type,
			Authentication authentication, Pageable pageable,
			PagedResourcesAssembler<DetailedOfferedServiceResource> assembler) {
		return ResponseEntity.ok(
				assembler.toResource(service.getOfferedServices(pinCode, type, authentication.getName(), pageable)));
	}

	@GetMapping("request/all")
	public ResponseEntity<Resources<ServiceRequestResource>> getAllServiceCenterServiceRequest(
			@RequestParam(name = "status", required = false) ServiceRequestStatusType status,
			Authentication authentication) {
		return ResponseEntity.ok(
				serviceRequestServiceCenterService.getAllServiceCenterServiceRequest(status, authentication.getName()));
	}

	@GetMapping("request/pending/all")
	public ResponseEntity<Resources<ServiceRequestResource>> getPendingServiceRequest(Authentication authentication) {
		return ResponseEntity.ok(serviceRequestServiceCenterService.getPendingServiceRequest(authentication.getName()));
	}

	@GetMapping("request/progress/all")
	public ResponseEntity<Resources<ServiceRequestResource>> getOnProgressServiceRequest(
			Authentication authentication) {
		return ResponseEntity
				.ok(serviceRequestServiceCenterService.getOnProgressServiceRequest(authentication.getName()));
	}

	@GetMapping("request/completed/all")
	public ResponseEntity<Resources<ServiceRequestResource>> getCompletedServiceRequest(Authentication authentication) {
		return ResponseEntity
				.ok(serviceRequestServiceCenterService.getCompletedServiceRequest(authentication.getName()));
	}

	@GetMapping("dashboard")
	public ResponseEntity<DashboardResource> dashboard(Authentication authentication) {
		return ResponseEntity.ok(service.getDashboard(authentication.getName()));
	}
}
