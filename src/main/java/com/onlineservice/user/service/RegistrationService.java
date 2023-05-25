package com.onlineservice.user.service;

import org.springframework.stereotype.Service;

import com.onlineservice.center.model.create.ServiceCenterCreateModel;
import com.onlineservice.user.model.create.UserCreateModel;

@Service
public interface RegistrationService {
	public String registerServiceCenter(ServiceCenterCreateModel model);

	public String registerCustomer(UserCreateModel model);
}
