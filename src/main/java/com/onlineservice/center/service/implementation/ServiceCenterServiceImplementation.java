package com.onlineservice.center.service.implementation;

import java.io.IOException;
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

import com.onlineservice.center.model.OfferedService;
import com.onlineservice.center.model.ServiceCenter;
import com.onlineservice.center.model.ServiceCenterAddress;
import com.onlineservice.center.model.ServiceType;
import com.onlineservice.center.model.create.OfferedServiceCreateModel;
import com.onlineservice.center.model.create.ServiceCenterAddressCreateModel;
import com.onlineservice.center.model.create.ServiceCenterCreateModel;
import com.onlineservice.center.repository.OfferedServiceRepository;
import com.onlineservice.center.repository.ServiceCenterAddressRepository;
import com.onlineservice.center.repository.ServiceCenterRepository;
import com.onlineservice.center.resource.DetailedOfferedServiceResource;
import com.onlineservice.center.resource.OfferedServiceResource;
import com.onlineservice.center.resource.ServiceCenterAddressResource;
import com.onlineservice.center.resource.ServiceCenterResource;
import com.onlineservice.center.service.ServiceCenterAdminServiceCenterService;
import com.onlineservice.center.service.ServiceCenterService;
import com.onlineservice.exception.ApiException;
import com.onlineservice.request.repository.ReviewRepository;
import com.onlineservice.request.repository.ServiceRequestRepository;
import com.onlineservice.request.resource.DashboardResource;
import com.onlineservice.service.EmailService;
import com.onlineservice.service.StorageService;
import com.onlineservice.user.model.ServiceCenterAdminUser;
import com.onlineservice.user.model.ServiceEngineerUser;
import com.onlineservice.user.model.User;
import com.onlineservice.user.model.create.UserCreateModel;
import com.onlineservice.user.repository.ServiceEngineerUserRepository;
import com.onlineservice.user.repository.UserRepository;
import com.onlineservice.user.resource.UserResource;
import com.onlineservice.user.service.AdminServiceCenterService;
import com.onlineservice.user.service.LoginService;
import com.onlineservice.utils.JsonResponseUtils;
import com.onlineservice.utils.RandomString;

@Service
public class ServiceCenterServiceImplementation
		implements ServiceCenterService, ServiceCenterAdminServiceCenterService, AdminServiceCenterService {

	private ServiceCenterRepository serviceCenterRepository;
	private ServiceCenterAddressRepository serviceCenterAddressRepository;
	private UserRepository userRepository;
	private ServiceEngineerUserRepository serviceEngineerUserRepository;
	private OfferedServiceRepository offeredServiceRepository;
	private ServiceRequestRepository serviceRequestRepository;
	private ReviewRepository reviewRepository;
	private LoginService loginService;
	private StorageService storageService;
	private EmailService emailService;

	@Autowired
	public ServiceCenterServiceImplementation(ServiceCenterRepository serviceCenterRepository,
			ServiceCenterAddressRepository serviceCenterAddressRepository, UserRepository userRepository,
			ServiceEngineerUserRepository serviceEngineerUserRepository,
			OfferedServiceRepository offeredServiceRepository, ServiceRequestRepository serviceRequestRepository,
			ReviewRepository reviewRepository, LoginService loginService, StorageService storageService,
			EmailService emailService) {
		this.serviceCenterRepository = serviceCenterRepository;
		this.serviceCenterAddressRepository = serviceCenterAddressRepository;
		this.userRepository = userRepository;
		this.serviceEngineerUserRepository = serviceEngineerUserRepository;
		this.offeredServiceRepository = offeredServiceRepository;
		this.serviceRequestRepository = serviceRequestRepository;
		this.reviewRepository = reviewRepository;
		this.loginService = loginService;
		this.storageService = storageService;
		this.emailService = emailService;
	}

	private PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public ServiceCenterResource getServiceCenter(Long scId, String username) {
		loginService.checkLogin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByIdAndIsDeletedIsFalse(scId);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		return new ServiceCenterResource(serviceCenter);
	}

	@Override
	public ServiceCenterResource getServiceCenter(String username) {
		ServiceCenterAdminUser admin = loginService.checkServiceCenterAdmin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		return new ServiceCenterResource(serviceCenter);
	}

	@Override
	public String updateServiceCenter(ServiceCenterCreateModel model, String username) {
		ServiceCenterAdminUser admin = loginService.checkServiceCenterAdmin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		if (model.getName() != null)
			serviceCenter.setName(model.getName());
		if (model.getDescription() != null)
			serviceCenter.setDescription(model.getDescription());
		serviceCenterRepository.save(serviceCenter);

		ServiceCenterAddressCreateModel addressModel = model.getAddress();
		ServiceCenterAddress address = serviceCenter.getAddress();
		if (addressModel.getAddressLine1() != null)
			address.setAddressLine1(addressModel.getAddressLine1());
		if (addressModel.getAddressLine2() != null)
			address.setAddressLine2(addressModel.getAddressLine2());
		if (addressModel.getCity() != null)
			address.setCity(addressModel.getCity());
		if (addressModel.getState() != null)
			address.setState(addressModel.getState());
		if (addressModel.getCountry() != null)
			address.setCountry(addressModel.getCountry());
		if (addressModel.getPincode() != null)
			address.setPincode(addressModel.getPincode());
		if (addressModel.getLatitude() != null)
			address.setLatitude(addressModel.getLatitude());
		if (addressModel.getLongitude() != null)
			address.setLongitude(addressModel.getLongitude());
		serviceCenterAddressRepository.save(address);

		return JsonResponseUtils.createStatusResponse("Service Center Updated Successfully");
	}

	@Override
	public ServiceCenterAddressResource getServiceCenterAddress(Long scId, String username) {
		loginService.checkLogin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByIdAndIsDeletedIsFalse(scId);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		return new ServiceCenterAddressResource(serviceCenter.getAddress());
	}

	@Override
	public ServiceCenterAddressResource getServiceCenterAddress(String username) {
		ServiceCenterAdminUser admin = loginService.checkServiceCenterAdmin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		return new ServiceCenterAddressResource(serviceCenter.getAddress());
	}

	@Override
	public String updateServiceCenterAddress(ServiceCenterAddressCreateModel model, String username) {
		ServiceCenterAdminUser admin = loginService.checkServiceCenterAdmin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		ServiceCenterAddress address = serviceCenter.getAddress();
		if (model.getAddressLine1() != null)
			address.setAddressLine1(model.getAddressLine1());
		if (model.getAddressLine2() != null)
			address.setAddressLine1(model.getAddressLine2());
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
		serviceCenterAddressRepository.save(address);
		return JsonResponseUtils.createStatusResponse("Service Center Address Updated Succcessfully");
	}

	@Override
	public UserResource getServiceCenterAdmin(Long scId, String username) {
		loginService.checkLogin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByIdAndIsDeletedIsFalse(scId);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		return new UserResource(serviceCenter.getAdmin());
	}

	@Override
	public Page<ServiceCenterResource> getServiceCenter(Boolean isVerified, String username, Pageable pageable) {
		loginService.checkAdmin(username);
		Page<ServiceCenter> page;
		if (isVerified == null)
			page = serviceCenterRepository.findByIsDeletedIsFalse(pageable);
		else
			page = serviceCenterRepository.findByIsVerifiedAndIsDeletedIsFalse(isVerified, pageable);
		List<ServiceCenterResource> resources = new LinkedList<>();
		for (ServiceCenter serviceCenter : page.getContent()) {
			resources.add(new ServiceCenterResource(serviceCenter));
		}
		return new PageImpl<>(resources, pageable, page.getTotalElements());
	}

	@Override
	public Resources<ServiceCenterResource> getAllServiceCenter(Boolean isVerified, String username) {
		loginService.checkAdmin(username);
		List<ServiceCenter> list;
		if (isVerified == null)
			list = serviceCenterRepository.findByIsDeletedIsFalse();
		else
			list = serviceCenterRepository.findByIsVerifiedAndIsDeletedIsFalse(isVerified);
		List<ServiceCenterResource> resources = new LinkedList<>();
		for (ServiceCenter serviceCenter : list) {
			resources.add(new ServiceCenterResource(serviceCenter));
		}
		return new Resources<>(resources);
	}

	@Override
	public String setVerifyServiceCenter(Long scId, Boolean verify, String username) {
		loginService.checkLogin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByIdAndIsDeletedIsFalse(scId);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		serviceCenter.setIsVerified(verify);
		serviceCenterRepository.save(serviceCenter);
		return JsonResponseUtils.createStatusResponse("Service Center Verification Updated Successfully");
	}

	@Override
	public String deleteServiceCenter(Long scId, String username) {
		loginService.checkLogin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByIdAndIsDeletedIsFalse(scId);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}

		ServiceCenterAdminUser admin = serviceCenter.getAdmin();
		admin.setIsDeleted(true);
		admin.setEmail(admin.getEmail() + ":deleted");
		userRepository.save(admin);

		serviceCenter.setIsDeleted(true);
		serviceCenterRepository.save(serviceCenter);

		return JsonResponseUtils.createStatusResponse("Service Center Deleted Successfully");
	}

	@Override
	public String uploadMedia(MultipartFile file, String username) {
		loginService.checkLogin(username);
		String url = storageService.uploadMedia(file);
		return JsonResponseUtils.createURLResponse(url);
	}

	@Override
	public String uploadServiceCenterImage(MultipartFile file, String username) {
		ServiceCenterAdminUser admin = loginService.checkServiceCenterAdmin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}

		String fileLocation = "";
		fileLocation = storageService.uploadServiceCenterImage(file, serviceCenter);
		serviceCenter.setImage(fileLocation);
		serviceCenterRepository.save(serviceCenter);
		return JsonResponseUtils.createStatusResponse("Service Center Image Updated Successfully");
	}

	@Override
	public String addServiceEngineer(UserCreateModel model, String username) {
		User already = userRepository.findByEmail(model.getEmail());
		if (already != null) {
			throw new ApiException(HttpStatus.FAILED_DEPENDENCY, "Email Already Exists",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		ServiceCenterAdminUser admin = loginService.checkServiceCenterAdmin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		ServiceEngineerUser engineer = new ServiceEngineerUser();
		engineer.setFirstName(model.getFirstName());
		engineer.setLastName(model.getLastName());
		engineer.setEmail(model.getEmail());
		engineer.setPhoneNumber(model.getPhoneNumber());
		engineer.setServiceCenter(serviceCenter);
		String password = new RandomString(10).nextString();
		engineer.setPassword(getEncoder().encode(password));
		userRepository.save(engineer);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					emailService.sendEmail(model.getEmail(), password);
				} catch (MessagingException | IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		return JsonResponseUtils.createStatusResponse("Service Center Engineer Has Been Added Successfully");
	}

	@Override
	public Page<UserResource> getServiceEngineer(String username, Pageable pageable) {
		ServiceCenterAdminUser admin = loginService.checkServiceCenterAdmin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		Page<ServiceEngineerUser> page = serviceEngineerUserRepository.findByServiceCenter(serviceCenter, pageable);
		List<UserResource> resources = new LinkedList<UserResource>();
		for (ServiceEngineerUser user : page.getContent()) {
			resources.add(new UserResource(user));
		}
		return new PageImpl<>(resources, pageable, page.getTotalElements());
	}

	@Override
	public Resources<UserResource> getAllServiceEngineer(String username) {
		ServiceCenterAdminUser admin = loginService.checkServiceCenterAdmin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		List<ServiceEngineerUser> users = (List<ServiceEngineerUser>) serviceEngineerUserRepository
				.findByServiceCenterAndIsDeletedIsFalse(serviceCenter);
		List<UserResource> resources = new LinkedList<UserResource>();
		for (ServiceEngineerUser user : users) {
			resources.add(new UserResource(user));
		}
		return new Resources<>(resources);
	}

	@Override
	public UserResource getServiceEngineer(Long eid, String username) {
		loginService.checkServiceCenterAdmin(username);
		ServiceEngineerUser user = serviceEngineerUserRepository.findOne(eid);
		if (user == null) {
			throw new ApiException(HttpStatus.UNAUTHORIZED, "User Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		return new UserResource(user);
	}

	@Override
	public String deleteServiceEngineer(Long eid, String username) {
		ServiceCenterAdminUser admin = loginService.checkServiceCenterAdmin(username);
		ServiceEngineerUser user = serviceEngineerUserRepository.findOne(eid);
		if (user == null) {
			throw new ApiException(HttpStatus.UNAUTHORIZED, "User Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
		if (!user.getServiceCenter().equals(serviceCenter)) {
			throw new ApiException(HttpStatus.UNAUTHORIZED, "User Is Not Authorized",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		user.setIsDeleted(true);
		user.setEmail(user.getEmail() + ":deleted");
		user.setPhoneNumber(user.getPhoneNumber() + ":deleted");
		userRepository.save(user);
		return JsonResponseUtils.createStatusResponse("Service Engineer Deleted Successfully");
	}

	@Override
	public String addOfferedService(OfferedServiceCreateModel model, String username) {
		ServiceCenterAdminUser admin = loginService.checkServiceCenterAdmin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		OfferedService offeredService = new OfferedService();
		offeredService.setServiceCenter(serviceCenter);
		offeredService.setServiceCharge(model.getServiceCharge());
		offeredService.setServiceType(model.getServiceType());
		offeredService.setPincodes(model.getPincodes());
		offeredServiceRepository.save(offeredService);
		return JsonResponseUtils.createStatusResponse("Service Offered Has Been Added Successfully");
	}

	@Override
	public Page<OfferedServiceResource> getOfferedServices(String username, Pageable pageable) {
		ServiceCenterAdminUser admin = loginService.checkServiceCenterAdmin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		Page<OfferedService> page = offeredServiceRepository.findByServiceCenterAndDeletedIsFalse(serviceCenter,
				pageable);
		List<OfferedServiceResource> resources = new LinkedList<>();
		for (OfferedService os : page.getContent()) {
			List<Double> reviewList = reviewRepository.ratingOfService(os);
			Double rating = -1d;
			for (Double r : reviewList) {
				rating += r;
			}
			resources.add(new OfferedServiceResource(os, rating));
		}
		return new PageImpl<>(resources, pageable, page.getTotalElements());
	}

	@Override
	public Resources<OfferedServiceResource> getAllOfferedServices(String username) {
		ServiceCenterAdminUser admin = loginService.checkServiceCenterAdmin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		List<OfferedService> list = offeredServiceRepository.findByServiceCenterAndDeletedIsFalse(serviceCenter);
		List<OfferedServiceResource> resources = new LinkedList<>();
		for (OfferedService os : list) {
			List<Double> reviewList = reviewRepository.ratingOfService(os);
			Double rating = -1d;
			for (Double r : reviewList) {
				if (r != null)
					rating = r;
			}
			resources.add(new OfferedServiceResource(os, rating));
		}
		return new Resources<>(resources);
	}

	@Override
	public OfferedServiceResource getOfferedService(Long osId, String username) {
		loginService.checkServiceCenterAdmin(username);
		OfferedService offeredService = offeredServiceRepository.findOne(osId);
		if (offeredService == null || offeredService.getDeleted()) {
			throw new ApiException(HttpStatus.UNAUTHORIZED, "Offered Service Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		List<Double> reviewList = reviewRepository.ratingOfService(offeredService);
		Double rating = -1d;
		for (Double r : reviewList) {
			if (r != null)
				rating = r;
		}
		return new OfferedServiceResource(offeredService, rating);
	}

	@Override
	public String updateOfferedService(Long osId, ServiceType serviceType, Float serviceCharge, String pincodes,
			String username) {
		ServiceCenterAdminUser admin = loginService.checkServiceCenterAdmin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		OfferedService offeredService = offeredServiceRepository.findOne(osId);
		if (offeredService == null || !offeredService.getServiceCenter().equals(serviceCenter)
				|| offeredService.getDeleted())
			throw new ApiException(HttpStatus.UNAUTHORIZED, "Offered Service Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		if (serviceType != null)
			offeredService.setServiceType(serviceType);
		if (serviceCharge != null)
			offeredService.setServiceCharge(serviceCharge);
		if (pincodes != null)
			offeredService.setPincodes(pincodes);
		offeredServiceRepository.save(offeredService);
		return JsonResponseUtils.createStatusResponse("Service Offered Has Been Updated Successfully");
	}

	@Override
	public String deleteOfferedService(Long osId, String username) {
		ServiceCenterAdminUser admin = loginService.checkServiceCenterAdmin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		OfferedService offeredService = offeredServiceRepository.findOne(osId);
		if (offeredService == null || !offeredService.getServiceCenter().equals(serviceCenter)
				|| offeredService.getDeleted())
			throw new ApiException(HttpStatus.UNAUTHORIZED, "Offered Service Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		offeredService.setDeleted(true);
		offeredServiceRepository.save(offeredService);
		return JsonResponseUtils.createStatusResponse("Service Offered Has Been Deleted Successfully");
	}

	@Override
	public Resources<DetailedOfferedServiceResource> getAllOfferedServices(String pinCode, ServiceType type,
			String username) {
		loginService.checkLogin(username);
		List<OfferedService> services = offeredServiceRepository.findByServiceTypeAndPincodesContaining(type, pinCode);
		List<DetailedOfferedServiceResource> resources = new LinkedList<DetailedOfferedServiceResource>();
		for (OfferedService service : services) {
			if (!service.getDeleted()) {
				List<Double> reviewList = reviewRepository.ratingOfService(service);
				Double rating = -1d;
				for (Double r : reviewList) {
					if (r != null)
						rating = r;
				}
				resources.add(new DetailedOfferedServiceResource(rating, service));
			}
		}
		return new Resources<>(resources);
	}

	@Override
	public Page<DetailedOfferedServiceResource> getOfferedServices(String pinCode, ServiceType type, String username,
			Pageable pageable) {
		loginService.checkLogin(username);
		Page<OfferedService> page = offeredServiceRepository
				.findByServiceTypeAndPincodesContainingAndDeletedIsFalse(type, pinCode, pageable);
		List<DetailedOfferedServiceResource> resources = new LinkedList<DetailedOfferedServiceResource>();
		for (OfferedService service : page.getContent()) {
			List<Double> reviewList = reviewRepository.ratingOfService(service);
			Double rating = -1d;
			for (Double r : reviewList) {
				if (r != null)
					rating = r;
			}
			resources.add(new DetailedOfferedServiceResource(rating, service));
		}
		return new PageImpl<>(resources, pageable, page.getTotalElements());
	}

	@Override
	public DashboardResource getDashboard(String username) {
		ServiceCenterAdminUser user = loginService.checkServiceCenterAdmin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(user);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		DashboardResource dashboard = new DashboardResource();
		dashboard.setTotalEngineers(
				serviceEngineerUserRepository.countByServiceCenterAndIsDeletedIsFalse(serviceCenter));
		dashboard.setTotalServices(offeredServiceRepository.countByServiceCenterAndDeletedIsFalse(serviceCenter));
		dashboard.setTotalOrders(serviceRequestRepository.countByServiceServiceCenter(serviceCenter));
		return dashboard;
	}

}
