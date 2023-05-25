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
import org.springframework.web.bind.annotation.RestController;

import com.onlineservice.request.resource.ServiceRequestResource;
import com.onlineservice.request.service.ServiceRequestCustomerService;
import com.onlineservice.user.model.create.CustomerAddressCreateModel;
import com.onlineservice.user.resource.CustomerAddressResource;
import com.onlineservice.user.resource.UserResource;
import com.onlineservice.user.service.CustomerService;

@RestController
@RequestMapping("customer")
@CrossOrigin
public class CustomerController {

	private CustomerService customerService;
	private ServiceRequestCustomerService serviceRequestCustomerService;

	@Autowired
	public CustomerController(CustomerService customerService,
			ServiceRequestCustomerService serviceRequestCustomerService) {
		this.customerService = customerService;
		this.serviceRequestCustomerService = serviceRequestCustomerService;
	}

	@GetMapping("all")
	public ResponseEntity<Resources<UserResource>> getAllCustomers(Authentication authentication) {
		return ResponseEntity.ok(customerService.getAllCustomers(authentication.getName()));
	}

	@GetMapping("{uId}")
	public ResponseEntity<UserResource> getCustomer(@PathVariable("uId") Long uId, Authentication authentication) {
		return ResponseEntity.ok(customerService.getCustomer(uId, authentication.getName()));
	}

	@PostMapping(path = "address", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createCustomerAddress(@RequestBody CustomerAddressCreateModel model,
			Authentication authentication) {
		return ResponseEntity.ok(customerService.createCustomerAddress(model, authentication.getName()));
	}

	@GetMapping("address")
	public ResponseEntity<PagedResources<Resource<CustomerAddressResource>>> getCustomerAddresses(
			Authentication authentication, Pageable pageable,
			PagedResourcesAssembler<CustomerAddressResource> assembler) {
		return ResponseEntity
				.ok(assembler.toResource(customerService.getCustomerAddresses(authentication.getName(), pageable)));
	}

	@GetMapping("address/all")
	public ResponseEntity<Resources<CustomerAddressResource>> getCustomerAddresses(Authentication authentication) {
		return ResponseEntity.ok(customerService.getCustomerAddresses(authentication.getName()));
	}

	@GetMapping("address/{caId}")
	public ResponseEntity<CustomerAddressResource> getCustomerAddress(@PathVariable("caId") Long caId,
			Authentication authentication) {
		return ResponseEntity.ok(customerService.getCustomerAddress(caId, authentication.getName()));
	}

	@PutMapping(path = "address/{caId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateCustomerAddress(@PathVariable("caId") Long caId,
			@RequestBody CustomerAddressCreateModel model, Authentication authentication) {
		return ResponseEntity.ok(customerService.updateCustomerAddress(caId, model, authentication.getName()));
	}

	@DeleteMapping(path = "address/{caId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteCustomerAddress(@PathVariable("caId") Long caId,
			Authentication authentication) {
		return ResponseEntity.ok(customerService.deleteCustomerAddress(caId, authentication.getName()));
	}

	@GetMapping("{uId}/request")
	public ResponseEntity<PagedResources<Resource<ServiceRequestResource>>> getUserServiceRequest(
			@PathVariable("uId") Long uId, Authentication authentication, Pageable pageable,
			PagedResourcesAssembler<ServiceRequestResource> assembler) {
		return ResponseEntity.ok(assembler.toResource(
				serviceRequestCustomerService.getUserServiceRequestAdmin(uId, authentication.getName(), pageable)));
	}

	@GetMapping("request")
	public ResponseEntity<PagedResources<Resource<ServiceRequestResource>>> getUserServiceRequest(
			Authentication authentication, Pageable pageable,
			PagedResourcesAssembler<ServiceRequestResource> assembler) {
		return ResponseEntity.ok(assembler
				.toResource(serviceRequestCustomerService.getUserServiceRequest(authentication.getName(), pageable)));
	}

	@PutMapping(path = "request/{srId}/done", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> doneServiceRequest(@PathVariable("srId") Long srId, Authentication authentication) {
		return ResponseEntity.ok(serviceRequestCustomerService.doneServiceRequest(srId, authentication.getName()));
	}
}
