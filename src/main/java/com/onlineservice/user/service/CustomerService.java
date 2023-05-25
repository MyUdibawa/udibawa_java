package com.onlineservice.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import com.onlineservice.user.model.create.CustomerAddressCreateModel;
import com.onlineservice.user.resource.CustomerAddressResource;
import com.onlineservice.user.resource.UserResource;

@Service
public interface CustomerService {
	public Resources<UserResource> getAllCustomers(String username);

	public UserResource getCustomer(Long uId, String username);

	public String createCustomerAddress(CustomerAddressCreateModel model, String username);

	public Resources<CustomerAddressResource> getCustomerAddresses(String username);

	public Page<CustomerAddressResource> getCustomerAddresses(String username, Pageable pageable);

	public CustomerAddressResource getCustomerAddress(Long caId, String username);

	public String updateCustomerAddress(Long caId, CustomerAddressCreateModel model, String username);

	public String deleteCustomerAddress(Long caId, String username);
}
