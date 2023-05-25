package com.onlineservice.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import com.onlineservice.center.resource.ServiceCenterResource;

@Service
public interface AdminServiceCenterService {
	public Page<ServiceCenterResource> getServiceCenter(Boolean isVerified, String username, Pageable pageable);

	public Resources<ServiceCenterResource> getAllServiceCenter(Boolean isVerified, String username);

	public String setVerifyServiceCenter(Long scId, Boolean verify, String username);

	public String deleteServiceCenter(Long scId, String username);
}
