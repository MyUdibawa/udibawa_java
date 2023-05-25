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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.onlineservice.center.model.ServiceType;
import com.onlineservice.center.model.create.OfferedServiceCreateModel;
import com.onlineservice.center.model.create.ServiceCenterAddressCreateModel;
import com.onlineservice.center.model.create.ServiceCenterCreateModel;
import com.onlineservice.center.resource.OfferedServiceResource;
import com.onlineservice.center.resource.ServiceCenterAddressResource;
import com.onlineservice.center.resource.ServiceCenterResource;
import com.onlineservice.center.service.ServiceCenterAdminServiceCenterService;
import com.onlineservice.user.model.create.UserCreateModel;
import com.onlineservice.user.resource.UserResource;

@RestController
@RequestMapping("serviceCenterAdmin")
@CrossOrigin
public class ServiceCenterAdminController {
	private ServiceCenterAdminServiceCenterService service;

	@Autowired
	public ServiceCenterAdminController(ServiceCenterAdminServiceCenterService service) {
		this.service = service;
	}

	@GetMapping("serviceCenter")
	public ResponseEntity<ServiceCenterResource> getServiceCenter(Authentication authentication) {
		return ResponseEntity.ok(service.getServiceCenter(authentication.getName()));
	}

	@PutMapping(path = "serviceCenter", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateServiceCenter(@RequestBody ServiceCenterCreateModel model,
			Authentication authentication) {
		return ResponseEntity.ok(service.updateServiceCenter(model, authentication.getName()));
	}

	@PutMapping(path = "serviceCenter/image", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> uploadServiceCenterImage(@RequestParam("image") MultipartFile file,
			Authentication authentication) {
		return ResponseEntity.ok(service.uploadServiceCenterImage(file, authentication.getName()));
	}

	@GetMapping("serviceCenter/address")
	public ResponseEntity<ServiceCenterAddressResource> getServiceCenterAddress(Authentication authentication) {
		return ResponseEntity.ok(service.getServiceCenterAddress(authentication.getName()));
	}

	@PutMapping(path = "serviceCenter/address", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateServiceCenterAddress(@RequestBody ServiceCenterAddressCreateModel model,
			Authentication authentication) {
		return ResponseEntity.ok(service.updateServiceCenterAddress(model, authentication.getName()));
	}

	@PostMapping(path = "serviceCenter/engineer", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addServiceEngineer(@RequestBody UserCreateModel model,
			Authentication authentication) {
		return ResponseEntity.ok(service.addServiceEngineer(model, authentication.getName()));
	}

	@GetMapping("serviceCenter/engineer")
	public ResponseEntity<PagedResources<Resource<UserResource>>> getServiceEngineer(Authentication authentication,
			Pageable pageable, PagedResourcesAssembler<UserResource> assembler) {
		return ResponseEntity.ok(assembler.toResource(service.getServiceEngineer(authentication.getName(), pageable)));
	}

	@GetMapping("serviceCenter/engineer/{eid}")
	public ResponseEntity<UserResource> getServiceEngineer(@PathVariable("eid") Long eid,
			Authentication authentication) {
		return ResponseEntity.ok(service.getServiceEngineer(eid, authentication.getName()));
	}

	@DeleteMapping(path = "serviceCenter/engineer/{eid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteServiceEngineer(@PathVariable("eid") Long eid, Authentication authentication) {
		return ResponseEntity.ok(service.deleteServiceEngineer(eid, authentication.getName()));
	}

	@GetMapping("serviceCenter/engineer/all")
	public ResponseEntity<Resources<UserResource>> getAllServiceEngineer(Authentication authentication) {
		return ResponseEntity.ok(service.getAllServiceEngineer(authentication.getName()));
	}

	@PostMapping(path = "serviceCenter/offeredService", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addOfferedService(@RequestBody OfferedServiceCreateModel model,
			Authentication authentication) {
		return ResponseEntity.ok(service.addOfferedService(model, authentication.getName()));
	}

	@GetMapping("serviceCenter/offeredService")
	public ResponseEntity<PagedResources<Resource<OfferedServiceResource>>> getOfferedServices(
			Authentication authentication, Pageable pageable,
			PagedResourcesAssembler<OfferedServiceResource> assembler) {
		return ResponseEntity.ok(assembler.toResource(service.getOfferedServices(authentication.getName(), pageable)));
	}

	@GetMapping("serviceCenter/offeredService/all")
	public ResponseEntity<Resources<OfferedServiceResource>> getOfferedServices(Authentication authentication) {
		return ResponseEntity.ok(service.getAllOfferedServices(authentication.getName()));
	}

	@GetMapping("serviceCenter/offeredService/{osId}")
	public ResponseEntity<OfferedServiceResource> getOfferedService(@PathVariable("osId") Long osId,
			Authentication authentication) {
		return ResponseEntity.ok(service.getOfferedService(osId, authentication.getName()));
	}

	@PutMapping(path = "serviceCenter/offeredService/{osId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateOfferedService(@PathVariable("osId") Long osId,
			@RequestParam(name = "serviceType", required = false) ServiceType serviceType,
			@RequestParam(name = "serviceCharge", required = false) Float serviceCharge,
			@RequestParam(name = "pincodes", required = false) String pincodes, Authentication authentication) {
		return ResponseEntity
				.ok(service.updateOfferedService(osId, serviceType, serviceCharge, pincodes, authentication.getName()));
	}

	@DeleteMapping(path = "serviceCenter/offeredService/{osId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteOfferedService(@PathVariable("osId") Long osId, Authentication authentication) {
		return ResponseEntity.ok(service.deleteOfferedService(osId, authentication.getName()));
	}

	@PutMapping(path = "media", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> uploadMedia(@RequestParam("file") MultipartFile file, Authentication authentication) {
		return ResponseEntity.ok(service.uploadMedia(file, authentication.getName()));
	}
}
