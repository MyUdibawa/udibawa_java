package com.onlineservice.request.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.onlineservice.request.resource.ServiceRequestResource;

@Service
public interface ServiceRequestCustomerService {

	public Page<ServiceRequestResource> getUserServiceRequestAdmin(Long uId, String username, Pageable pageable);

	public Page<ServiceRequestResource> getUserServiceRequest(String username, Pageable pageable);

	public String doneServiceRequest(Long srId, String username);
}
