package com.onlineservice.request.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import com.onlineservice.request.model.PaymentMode;
import com.onlineservice.request.model.create.ReplacementPartCreateModel;
import com.onlineservice.request.resource.ServiceRequestResource;

@Service
public interface ServiceRequestEngineerService {
	public Resources<ServiceRequestResource> getAllWaitingEngineerServiceRequest(String username);

	public Page<ServiceRequestResource> getWaitingEngineerServiceRequest(String username, Pageable pageable);

	public Resources<ServiceRequestResource> getAllAcceptedEngineerServiceRequest(String username);

	public Page<ServiceRequestResource> getAcceptedEngineerServiceRequest(String username, Pageable pageable);

	public Page<ServiceRequestResource> getCompletedEngineerServiceRequest(String username, Pageable pageable);

	public String acceptServiceRequest(Long srId, String username);

	public String startServiceRequest(Long srId, String username);

	public String verifyServiceRequest(Long srId, Integer otp, String username);

	public String completeServiceRequest(Long srId, List<ReplacementPartCreateModel> parts,PaymentMode paymentMode, String username);
}
