package com.onlineservice.request.service;

import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import com.onlineservice.request.model.ServiceRequestStatusType;
import com.onlineservice.request.resource.ServiceRequestResource;

@Service
public interface ServiceRequestServiceCenterService {
	public Resources<ServiceRequestResource> getAllServiceCenterServiceRequest(ServiceRequestStatusType status,
			String username);

	public Resources<ServiceRequestResource> getPendingServiceRequest(String username);

	public Resources<ServiceRequestResource> getOnProgressServiceRequest(String username);

	public Resources<ServiceRequestResource> getCompletedServiceRequest(String username);
}
