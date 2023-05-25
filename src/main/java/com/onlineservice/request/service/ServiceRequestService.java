package com.onlineservice.request.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import com.onlineservice.request.model.ServiceRequestStatusType;
import com.onlineservice.request.model.create.ReviewCreateModel;
import com.onlineservice.request.model.create.ServiceRequestCreateModel;
import com.onlineservice.request.resource.ServiceRequestResource;

@Service
public interface ServiceRequestService {

	public String createServiceRequest(ServiceRequestCreateModel model, String username);

	public ServiceRequestResource getServiceRequest(Long srId, String username);

	public String reviewServiceRequest(Long srId,ReviewCreateModel model,  String username);

	public String assignEngineer(Long srId, Long eId, String username);

	public Page<ServiceRequestResource> getUserServiceRequest(String username, Pageable pageable);

	public Resources<ServiceRequestResource> getAllUserServiceRequest(String username);

	public Page<ServiceRequestResource> getUserServiceRequestAdmin(Long uId, String username, Pageable pageable);

	public Resources<ServiceRequestResource> getAllUserServiceRequestAdmin(Long uId, String username);

	public Page<ServiceRequestResource> getServiceCenterServiceRequest(ServiceRequestStatusType status, String username,
			Pageable pageable);

	public Resources<ServiceRequestResource> getAllServiceCenterServiceRequest(ServiceRequestStatusType status,
			String username);

	public Resources<ServiceRequestResource> getPendingServiceRequest(String username);

	public Resources<ServiceRequestResource> getOnProgressServiceRequest(String username);

	public Resources<ServiceRequestResource> getCompletedServiceRequest(String username);

	public Page<ServiceRequestResource> getServiceCenterServiceRequestAdmin(Long scId, ServiceRequestStatusType status,
			String username, Pageable pageable);

	public Resources<ServiceRequestResource> getAllServiceCenterServiceRequestAdmin(Long scId,
			ServiceRequestStatusType status, String username);
	
	public Resources<ServiceRequestResource> getPaymentHistory(Long date,String username);
}
