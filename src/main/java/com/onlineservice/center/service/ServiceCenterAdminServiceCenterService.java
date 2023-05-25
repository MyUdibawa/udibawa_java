package com.onlineservice.center.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.onlineservice.center.model.ServiceType;
import com.onlineservice.center.model.create.OfferedServiceCreateModel;
import com.onlineservice.center.model.create.ServiceCenterAddressCreateModel;
import com.onlineservice.center.model.create.ServiceCenterCreateModel;
import com.onlineservice.center.resource.OfferedServiceResource;
import com.onlineservice.center.resource.ServiceCenterAddressResource;
import com.onlineservice.center.resource.ServiceCenterResource;
import com.onlineservice.user.model.create.UserCreateModel;
import com.onlineservice.user.resource.UserResource;

@Service
public interface ServiceCenterAdminServiceCenterService {

	public ServiceCenterResource getServiceCenter(String username);

	public String updateServiceCenter(ServiceCenterCreateModel model, String username);

	public ServiceCenterAddressResource getServiceCenterAddress(String username);

	public String updateServiceCenterAddress(ServiceCenterAddressCreateModel model, String username);

	public String uploadMedia(MultipartFile file, String username);

	public String uploadServiceCenterImage(MultipartFile file, String username);

	public String addServiceEngineer(UserCreateModel model, String username);

	public Page<UserResource> getServiceEngineer(String username, Pageable pageable);

	public UserResource getServiceEngineer(Long eid, String username);

	public String deleteServiceEngineer(Long eid, String username);

	public Resources<UserResource> getAllServiceEngineer(String username);

	public String addOfferedService(OfferedServiceCreateModel model, String username);

	public Page<OfferedServiceResource> getOfferedServices(String username, Pageable pageable);

	public Resources<OfferedServiceResource> getAllOfferedServices(String username);

	public OfferedServiceResource getOfferedService(Long osId, String username);

	public String updateOfferedService(Long osId, ServiceType serviceType, Float serviceCharge, String pincodes,
			String username);

	public String deleteOfferedService(Long osId, String username);
}
