package com.onlineservice.user.service;

import org.springframework.stereotype.Service;

import com.onlineservice.user.model.AdminUser;
import com.onlineservice.user.model.CustomerUser;
import com.onlineservice.user.model.ServiceCenterAdminUser;
import com.onlineservice.user.model.ServiceEngineerUser;
import com.onlineservice.user.model.User;

@Service
public interface LoginService {
	public User checkLogin(String username);

	public AdminUser checkAdmin(String username);

	public ServiceCenterAdminUser checkServiceCenterAdmin(String username);

	public ServiceEngineerUser checkServiceEngineer(String username);

	public CustomerUser checkCustomer(String username);
}
