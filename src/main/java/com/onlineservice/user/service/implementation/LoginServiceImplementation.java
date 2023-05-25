package com.onlineservice.user.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.onlineservice.exception.ApiException;
import com.onlineservice.user.model.AdminUser;
import com.onlineservice.user.model.CustomerUser;
import com.onlineservice.user.model.ServiceCenterAdminUser;
import com.onlineservice.user.model.ServiceEngineerUser;
import com.onlineservice.user.model.User;
import com.onlineservice.user.repository.UserRepository;
import com.onlineservice.user.service.LoginService;

@Service
public class LoginServiceImplementation implements LoginService {

	private UserRepository userRepository;
	private static final String USER_NOT_LOGGED_IN = "User Not Logged In";
	public static final String USER_NOT_AUTHORIZED = "User Is Not Authorized";

	@Autowired
	public LoginServiceImplementation(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User checkLogin(String username) {
		User user = userRepository.findByEmail(username);
		if (user == null || user.getIsDeleted())
			throw new ApiException(HttpStatus.UNAUTHORIZED, USER_NOT_LOGGED_IN,
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		return user;
	}

	@Override
	public AdminUser checkAdmin(String username) {
		User user = userRepository.findByEmail(username);
		if (user == null || user.getIsDeleted())
			throw new ApiException(HttpStatus.UNAUTHORIZED, USER_NOT_LOGGED_IN,
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		if (!(user instanceof AdminUser))
			throw new ApiException(HttpStatus.UNAUTHORIZED, USER_NOT_AUTHORIZED,
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		return (AdminUser) user;
	}

	@Override
	public ServiceCenterAdminUser checkServiceCenterAdmin(String username) {
		User user = userRepository.findByEmail(username);
		if (user == null || user.getIsDeleted())
			throw new ApiException(HttpStatus.UNAUTHORIZED, USER_NOT_LOGGED_IN,
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		if (!(user instanceof ServiceCenterAdminUser))
			throw new ApiException(HttpStatus.UNAUTHORIZED, USER_NOT_AUTHORIZED,
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		return (ServiceCenterAdminUser) user;
	}

	@Override
	public ServiceEngineerUser checkServiceEngineer(String username) {
		User user = userRepository.findByEmail(username);
		if (user == null || user.getIsDeleted())
			throw new ApiException(HttpStatus.UNAUTHORIZED, USER_NOT_LOGGED_IN,
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		if (!(user instanceof ServiceEngineerUser))
			throw new ApiException(HttpStatus.UNAUTHORIZED, USER_NOT_AUTHORIZED,
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		return (ServiceEngineerUser) user;
	}

	@Override
	public CustomerUser checkCustomer(String username) {
		User user = userRepository.findByEmail(username);
		if (user == null || user.getIsDeleted())
			throw new ApiException(HttpStatus.UNAUTHORIZED, USER_NOT_LOGGED_IN,
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		if (!(user instanceof CustomerUser))
			throw new ApiException(HttpStatus.UNAUTHORIZED, USER_NOT_AUTHORIZED,
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		return (CustomerUser) user;
	}

}
