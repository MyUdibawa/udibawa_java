package com.onlineservice.user.service.implementation;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.onlineservice.center.model.ServiceCenter;
import com.onlineservice.center.model.ServiceCenterAddress;
import com.onlineservice.center.model.create.ServiceCenterAddressCreateModel;
import com.onlineservice.center.model.create.ServiceCenterCreateModel;
import com.onlineservice.center.repository.ServiceCenterAddressRepository;
import com.onlineservice.center.repository.ServiceCenterRepository;
import com.onlineservice.exception.ApiException;
import com.onlineservice.service.EmailService;
import com.onlineservice.service.StorageService;
import com.onlineservice.user.model.CustomerAddress;
import com.onlineservice.user.model.CustomerUser;
import com.onlineservice.user.model.ResetPasswordOTPTable;
import com.onlineservice.user.model.ServiceCenterAdminUser;
import com.onlineservice.user.model.User;
import com.onlineservice.user.model.create.CustomerAddressCreateModel;
import com.onlineservice.user.model.create.UserCreateModel;
import com.onlineservice.user.repository.CustomerAddressRepository;
import com.onlineservice.user.repository.CustomerUserRepository;
import com.onlineservice.user.repository.ResetPasswordOTPTableRepository;
import com.onlineservice.user.repository.UserRepository;
import com.onlineservice.user.resource.CustomerAddressResource;
import com.onlineservice.user.resource.UserResource;
import com.onlineservice.user.service.CustomerService;
import com.onlineservice.user.service.ForgotPasswordService;
import com.onlineservice.user.service.LoginService;
import com.onlineservice.user.service.RegistrationService;
import com.onlineservice.user.service.UserService;
import com.onlineservice.utils.GeneralUtils;
import com.onlineservice.utils.JsonResponseUtils;

@Service
public class UserServiceImplementation
		implements UserService, CustomerService, RegistrationService, ForgotPasswordService {
	private UserRepository userRepository;
	private ServiceCenterRepository serviceCenterRepository;
	private ServiceCenterAddressRepository serviceCenterAddressRepository;
	private CustomerUserRepository customerUserRepository;
	private CustomerAddressRepository customerAddressRepository;
	private ResetPasswordOTPTableRepository resetPasswordOTPTableRepository;
	private EmailService emailService;
	private LoginService loginService;
	private StorageService storageService;

	@Autowired
	public UserServiceImplementation(UserRepository userRepository, ServiceCenterRepository serviceCenterRepository,
			ServiceCenterAddressRepository serviceCenterAddressRepository,
			CustomerUserRepository customerUserRepository, CustomerAddressRepository customerAddressRepository,
			ResetPasswordOTPTableRepository resetPasswordOTPTableRepository, EmailService emailService,
			LoginService loginService, StorageService storageService) {
		this.userRepository = userRepository;
		this.serviceCenterRepository = serviceCenterRepository;
		this.serviceCenterAddressRepository = serviceCenterAddressRepository;
		this.customerUserRepository = customerUserRepository;
		this.customerAddressRepository = customerAddressRepository;
		this.resetPasswordOTPTableRepository = resetPasswordOTPTableRepository;
		this.emailService = emailService;
		this.loginService = loginService;
		this.storageService = storageService;
	}

	private PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public User findByEmail(String username) {
		return userRepository.findByEmail(username);
	}

	@Override
	public UserResource getLoggedIn(String username) {
		User user = loginService.checkLogin(username);
		return new UserResource(user);
	}

	@Override
	public String updateUser(String firstName, String lastName, String phoneNumber, String username) {
		User user = loginService.checkLogin(username);
		if (firstName != null && !firstName.isEmpty())
			user.setFirstName(firstName);
		if (lastName != null && !lastName.isEmpty())
			user.setLastName(lastName);
		if (phoneNumber != null && !phoneNumber.isEmpty()) {
			GeneralUtils.checkPhoneNumber(phoneNumber);
			user.setPhoneNumber(phoneNumber);
		}
		userRepository.save(user);
		return JsonResponseUtils.createStatusResponse("User Update Successfully");
	}

	@Override
	public String changePassword(String password, String username) {
		User user = loginService.checkLogin(username);
		GeneralUtils.checkPassword(user.getFirstName(), password);
		if (getEncoder().matches(password, user.getPassword()))
			throw new ApiException(HttpStatus.FAILED_DEPENDENCY, "Old Password And New Password Should Not Be Same",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		user.setPassword(getEncoder().encode(password));
		userRepository.save(user);
		return JsonResponseUtils.createStatusResponse("User Password Updated Successfully");
	}

	@Override
	public String registerServiceCenter(ServiceCenterCreateModel model) {
		User already = userRepository.findByEmail(model.getAdmin().getEmail());
		if (already != null) {
			throw new ApiException(HttpStatus.FAILED_DEPENDENCY, "Email Already Exists",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}

		ServiceCenter serviceCenter = new ServiceCenter();
		serviceCenter.setName(model.getName());
		serviceCenter.setDescription(model.getDescription());
		serviceCenter.setGstin(model.getGstin());
		serviceCenter.setPan(model.getPan());

		ServiceCenterAddress address = new ServiceCenterAddress();
		ServiceCenterAddressCreateModel addressCreateModel = model.getAddress();
		address.setAddressLine1(addressCreateModel.getAddressLine1());
		address.setAddressLine2(addressCreateModel.getAddressLine2());
		address.setCity(addressCreateModel.getCity());
		address.setState(addressCreateModel.getState());
		address.setCountry(addressCreateModel.getCountry());
		address.setPincode(addressCreateModel.getPincode());
		address.setLatitude(addressCreateModel.getLatitude());
		address.setLongitude(addressCreateModel.getLongitude());
		serviceCenterAddressRepository.save(address);
		serviceCenter.setAddress(address);

		ServiceCenterAdminUser admin = new ServiceCenterAdminUser();
		UserCreateModel adminCreateModel = model.getAdmin();
		admin.setFirstName(adminCreateModel.getFirstName());
		admin.setLastName(adminCreateModel.getLastName());
		GeneralUtils.checkEmail(adminCreateModel.getEmail());
		admin.setEmail(adminCreateModel.getEmail());
		GeneralUtils.checkPhoneNumber(adminCreateModel.getPhoneNumber());
		admin.setPhoneNumber(adminCreateModel.getPhoneNumber());
		GeneralUtils.checkPassword(adminCreateModel.getFirstName(), adminCreateModel.getPassword());
		admin.setPassword(getEncoder().encode(adminCreateModel.getPassword()));
		userRepository.save(admin);

		serviceCenter.setAdmin(admin);

		serviceCenterRepository.save(serviceCenter);
		return JsonResponseUtils.createStatusResponse("Service Center Created Successfully");
	}

	@Override
	public String registerCustomer(UserCreateModel model) {
		User already = userRepository.findByEmail(model.getEmail());
		if (already != null) {
			throw new ApiException(HttpStatus.FAILED_DEPENDENCY, "Email Already Exists",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		CustomerUser user = new CustomerUser();
		user.setFirstName(model.getFirstName());
		user.setLastName(model.getLastName());
		GeneralUtils.checkEmail(model.getEmail());
		user.setEmail(model.getEmail());
		GeneralUtils.checkPhoneNumber(model.getPhoneNumber());
		user.setPhoneNumber(model.getPhoneNumber());
		GeneralUtils.checkPassword(model.getFirstName(), model.getPassword());
		user.setPassword(getEncoder().encode(model.getPassword()));
		userRepository.save(user);
		return JsonResponseUtils.createStatusResponse("User Created Successfully");
	}

	@Override
	public String updateProfilePic(MultipartFile file, String username) {
		User user = loginService.checkLogin(username);
		String fileLocation = "";
		fileLocation = storageService.uploadProfilePic(file, user);
		user.setProfilePic(fileLocation);
		userRepository.save(user);
		return JsonResponseUtils.createStatusResponse("User Profile Updated Successfully");
	}

	@Override
	public Resources<CustomerAddressResource> getCustomerAddresses(String username) {
		CustomerUser customer = loginService.checkCustomer(username);
		List<CustomerAddress> addresses = customerAddressRepository.findByCustomerAndIsDeletedIsFalse(customer);
		List<CustomerAddressResource> resources = new LinkedList<>();
		for (CustomerAddress address : addresses) {
			resources.add(new CustomerAddressResource(address));
		}
		return new Resources<>(resources);
	}

	@Override
	public Page<CustomerAddressResource> getCustomerAddresses(String username, Pageable pageable) {
		CustomerUser customer = loginService.checkCustomer(username);
		Page<CustomerAddress> page = customerAddressRepository.findByCustomerAndIsDeletedIsFalse(customer, pageable);
		List<CustomerAddressResource> resources = new LinkedList<>();
		for (CustomerAddress address : page.getContent()) {
			resources.add(new CustomerAddressResource(address));
		}
		return new PageImpl<>(resources, pageable, page.getTotalElements());
	}

	@Override
	public String createCustomerAddress(CustomerAddressCreateModel model, String username) {
		CustomerUser customer = loginService.checkCustomer(username);

		CustomerAddress address = new CustomerAddress();
		address.setTitle(model.getTitle());
		address.setAddressLine1(model.getAddressLine1());
		address.setAddressLine2(model.getAddressLine2());
		address.setCity(model.getCity());
		address.setState(model.getState());
		address.setCountry(model.getCountry());
		address.setPincode(model.getPincode());
		address.setLatitude(model.getLatitude());
		address.setLongitude(model.getLongitude());
		address.setCustomer(customer);
		customerAddressRepository.save(address);

		return JsonResponseUtils.createStatusResponse("Customer Address Created Successfully");
	}

	@Override
	public CustomerAddressResource getCustomerAddress(Long caId, String username) {
		loginService.checkLogin(username);
		CustomerAddress address = customerAddressRepository.findOne(caId);
		if (address == null || address.getIsDeleted() == true)
			throw new ApiException(HttpStatus.NOT_FOUND, "Customer Address Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		return new CustomerAddressResource(address);
	}

	@Override
	public String updateCustomerAddress(Long caId, CustomerAddressCreateModel model, String username) {
		CustomerUser customer = loginService.checkCustomer(username);
		CustomerAddress address = customerAddressRepository.findByIdAndCustomer(caId, customer);
		if (address == null)
			throw new ApiException(HttpStatus.NOT_FOUND, "Customer Address Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		if (model.getTitle() != null)
			address.setTitle(model.getTitle());
		if (model.getAddressLine1() != null)
			address.setAddressLine1(model.getAddressLine1());
		if (model.getAddressLine2() != null)
			address.setAddressLine2(model.getAddressLine2());
		if (model.getCity() != null)
			address.setCity(model.getCity());
		if (model.getState() != null)
			address.setState(model.getState());
		if (model.getCountry() != null)
			address.setCountry(model.getCountry());
		if (model.getPincode() != null)
			address.setPincode(model.getPincode());
		if (model.getLatitude() != null)
			address.setLatitude(model.getLatitude());
		if (model.getLongitude() != null)
			address.setLongitude(model.getLongitude());
		customerAddressRepository.save(address);
		return JsonResponseUtils.createStatusResponse("Customer Address Updated Successfully");
	}

	@Override
	public String deleteCustomerAddress(Long caId, String username) {
		CustomerUser customer = loginService.checkCustomer(username);
		CustomerAddress address = customerAddressRepository.findByIdAndCustomer(caId, customer);
		if (address == null)
			throw new ApiException(HttpStatus.NOT_FOUND, "Customer Address Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		address.setIsDeleted(true);
		customerAddressRepository.save(address);
		return JsonResponseUtils.createStatusResponse("Customer Address Deleted Successfully");
	}

	@Override
	public String requestForgetPassword(String email) {
		User user = findByEmail(email);
		if (user == null)
			throw new ApiException(HttpStatus.NOT_FOUND, "User with Email Id " + email + " Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		if (!user.getIsEnabled().booleanValue())
			throw new ApiException(HttpStatus.EXPECTATION_FAILED,
					"User is not activated. If Link already Expired ask Admin to send a new link",

					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		ResetPasswordOTPTable already = this.resetPasswordOTPTableRepository.findByUser(user);
		if (already != null) {
			already.setUser(null);
			resetPasswordOTPTableRepository.delete(already);
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ResetPasswordOTPTable otpTable = new ResetPasswordOTPTable(user);
					otpTable = resetPasswordOTPTableRepository.save(otpTable);
					emailService.sendResetPasswordLink(otpTable);
				} catch (MessagingException | IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		return JsonResponseUtils.createStatusResponse("OTP has been Sent to " + user.getEmail());
	}

	@Override
	public String changePassword(String email, int otp, String password) {
		User user = findByEmail(email);
		if (user == null)
			throw new ApiException(HttpStatus.NOT_FOUND, "User with Email Id " + email + " Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		if (password == null || password.isEmpty())
			throw new ApiException(HttpStatus.NOT_FOUND, "Password Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());

		ResetPasswordOTPTable otpTable = this.resetPasswordOTPTableRepository.findByOtp(otp);
		if (otpTable == null)
			throw new ApiException(HttpStatus.NOT_FOUND, "Link Expired",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		if (otpTable.getExpiryDate().getTime() < (new Date()).getTime())
			throw new ApiException(HttpStatus.EXPECTATION_FAILED, "Link Expired",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		if (getEncoder().matches(password, user.getPassword()))
			throw new ApiException(HttpStatus.EXPECTATION_FAILED, "New Password And Old Password Should not be Same",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		GeneralUtils.checkPassword(user.getFirstName(), password);
		user.setPassword(getEncoder().encode(password));
		user = (User) this.userRepository.save(user);
		otpTable.setUser(null);
		this.resetPasswordOTPTableRepository.delete(otpTable);
		return JsonResponseUtils.createStatusResponse("Password Updated");
	}

	@Override
	public Resources<UserResource> getAllCustomers(String username) {
		loginService.checkAdmin(username);
		List<CustomerUser> list = (List<CustomerUser>) customerUserRepository.findAll();
		List<UserResource> resources = new LinkedList<UserResource>();
		for (CustomerUser user : list) {
			resources.add(new UserResource(user));
		}
		return new Resources<>(resources);
	}

	@Override
	public UserResource getCustomer(Long uId, String username) {
		loginService.checkAdmin(username);
		CustomerUser customerUser = customerUserRepository.findOne(uId);
		if (customerUser == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Customer Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		return new UserResource(customerUser);
	}

}
