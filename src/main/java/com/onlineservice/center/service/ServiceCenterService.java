package com.onlineservice.center.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import com.onlineservice.center.model.ServiceType;
import com.onlineservice.center.resource.DetailedOfferedServiceResource;
import com.onlineservice.center.resource.ServiceCenterAddressResource;
import com.onlineservice.center.resource.ServiceCenterResource;
import com.onlineservice.request.resource.DashboardResource;
import com.onlineservice.user.resource.UserResource;

@Service
public interface ServiceCenterService {

	public ServiceCenterResource getServiceCenter(Long scId, String username);

	public ServiceCenterAddressResource getServiceCenterAddress(Long scId, String username);

	public UserResource getServiceCenterAdmin(Long scId, String username);

	public Resources<DetailedOfferedServiceResource> getAllOfferedServices(String pinCode, ServiceType type,
			String username);

	public Page<DetailedOfferedServiceResource> getOfferedServices(String pinCode, ServiceType type, String username,
			Pageable pageable);

	public DashboardResource getDashboard(String username);
}
