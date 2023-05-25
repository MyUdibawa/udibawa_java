package com.onlineservice.request.service.implementation;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.onlineservice.center.model.OfferedService;
import com.onlineservice.center.model.ServiceCenter;
import com.onlineservice.center.repository.OfferedServiceRepository;
import com.onlineservice.center.repository.ServiceCenterRepository;
import com.onlineservice.exception.ApiException;
import com.onlineservice.request.model.AssignServiceEngineer;
import com.onlineservice.request.model.AssignServiceEngineerStatus;
import com.onlineservice.request.model.AssignServiceEngineerStatusType;
import com.onlineservice.request.model.PaymentMode;
import com.onlineservice.request.model.ReplacementPart;
import com.onlineservice.request.model.Review;
import com.onlineservice.request.model.ServiceRequest;
import com.onlineservice.request.model.ServiceRequestStatus;
import com.onlineservice.request.model.ServiceRequestStatusType;
import com.onlineservice.request.model.create.ReplacementPartCreateModel;
import com.onlineservice.request.model.create.ReviewCreateModel;
import com.onlineservice.request.model.create.ServiceRequestCreateModel;
import com.onlineservice.request.repository.AssignServiceEngineerRepository;
import com.onlineservice.request.repository.AssignServiceEngineerStatusRepository;
import com.onlineservice.request.repository.ReplacementPartRepository;
import com.onlineservice.request.repository.ReviewRepository;
import com.onlineservice.request.repository.ServiceRequestRepository;
import com.onlineservice.request.repository.ServiceRequestStatusRepository;
import com.onlineservice.request.resource.ReviewDashboardResource;
import com.onlineservice.request.resource.ReviewResource;
import com.onlineservice.request.resource.ServiceRequestResource;
import com.onlineservice.request.service.ReviewService;
import com.onlineservice.request.service.ServiceRequestCustomerService;
import com.onlineservice.request.service.ServiceRequestEngineerService;
import com.onlineservice.request.service.ServiceRequestService;
import com.onlineservice.request.service.ServiceRequestServiceCenterService;
import com.onlineservice.service.OtpService;
import com.onlineservice.service.RazorPayService;
import com.onlineservice.user.model.AdminUser;
import com.onlineservice.user.model.CustomerAddress;
import com.onlineservice.user.model.CustomerUser;
import com.onlineservice.user.model.ServiceCenterAdminUser;
import com.onlineservice.user.model.ServiceEngineerUser;
import com.onlineservice.user.model.User;
import com.onlineservice.user.repository.CustomerAddressRepository;
import com.onlineservice.user.repository.CustomerUserRepository;
import com.onlineservice.user.repository.ServiceEngineerUserRepository;
import com.onlineservice.user.service.LoginService;
import com.onlineservice.utils.GeneralUtils;
import com.onlineservice.utils.JsonResponseUtils;
import com.razorpay.Order;
import com.razorpay.RazorpayException;

@Service
public class ServiceRequestServiceImplementation implements ServiceRequestService, ServiceRequestEngineerService,
		ServiceRequestCustomerService, ServiceRequestServiceCenterService, ReviewService {

	private OfferedServiceRepository offeredServiceRepository;
	private CustomerAddressRepository customerAddressRepository;
	private ServiceRequestRepository serviceRequestRepository;
	private ServiceRequestStatusRepository serviceRequestStatusRepository;
	private ReviewRepository reviewRepository;
	private ServiceCenterRepository serviceCenterRepository;
	private AssignServiceEngineerRepository assignServiceEngineerRepository;
	private AssignServiceEngineerStatusRepository assignServiceEngineerStatusRepository;
	private ReplacementPartRepository replacementPartRepository;
	private CustomerUserRepository customerUserRepository;
	private ServiceEngineerUserRepository serviceEngineerUserRepository;
	private LoginService loginService;
	private RazorPayService razorPayService;
	private OtpService otpService;

	@Autowired
	public ServiceRequestServiceImplementation(OfferedServiceRepository offeredServiceRepository,
			CustomerAddressRepository customerAddressRepository, ServiceRequestRepository serviceRequestRepository,
			ServiceRequestStatusRepository serviceRequestStatusRepository, ReviewRepository reviewRepository,
			ServiceCenterRepository serviceCenterRepository,
			AssignServiceEngineerRepository assignServiceEngineerRepository,
			AssignServiceEngineerStatusRepository assignServiceEngineerStatusRepository,
			ReplacementPartRepository replacementPartRepository, CustomerUserRepository customerUserRepository,
			ServiceEngineerUserRepository serviceEngineerUserRepository, LoginService loginService,
			RazorPayService razorPayService, OtpService otpService) {
		this.offeredServiceRepository = offeredServiceRepository;
		this.customerAddressRepository = customerAddressRepository;
		this.serviceRequestRepository = serviceRequestRepository;
		this.serviceRequestStatusRepository = serviceRequestStatusRepository;
		this.reviewRepository = reviewRepository;
		this.serviceCenterRepository = serviceCenterRepository;
		this.replacementPartRepository = replacementPartRepository;
		this.assignServiceEngineerRepository = assignServiceEngineerRepository;
		this.assignServiceEngineerStatusRepository = assignServiceEngineerStatusRepository;
		this.customerUserRepository = customerUserRepository;
		this.serviceEngineerUserRepository = serviceEngineerUserRepository;
		this.loginService = loginService;
		this.razorPayService = razorPayService;
		this.otpService = otpService;
	}

	@Override
	public String createServiceRequest(ServiceRequestCreateModel model, String username) {
		CustomerUser customer = loginService.checkCustomer(username);
		CustomerAddress address = customerAddressRepository.findOne(model.getAddressId());
		if (address == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Customer Address Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		OfferedService service = offeredServiceRepository.findOne(model.getServiceId());
		if (service == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Offered Service Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		ServiceRequest request = new ServiceRequest();
		request.setCustomer(customer);
		request.setAddress(address);
		request.setService(service);
		request.setServiceCharge(service.getServiceCharge());
		request.setDescription(model.getDescription());
		request.setStatus(ServiceRequestStatusType.WAITING);
		request.setRequestedTime(model.getRequestedTime());
		request.setRequestedDate(new Date(model.getRequestedDate()));
		request = serviceRequestRepository.save(request);

		ServiceRequestStatus status = new ServiceRequestStatus();
		status.setStatus(ServiceRequestStatusType.WAITING);
		status.setServiceRequest(request);
		serviceRequestStatusRepository.save(status);

		return JsonResponseUtils.createStatusResponse("Request Placed Successfully");
	}

	@Override
	public Page<ServiceRequestResource> getUserServiceRequest(String username, Pageable pageable) {
		CustomerUser customer = loginService.checkCustomer(username);
		Page<ServiceRequest> page = serviceRequestRepository.findByCustomer(customer, pageable);
		List<ServiceRequestResource> resources = new LinkedList<>();
		for (ServiceRequest request : page.getContent()) {
			resources.add(new ServiceRequestResource(request));
		}
		return new PageImpl<>(resources, pageable, page.getTotalElements());
	}

	@Override
	public Resources<ServiceRequestResource> getAllUserServiceRequest(String username) {
		CustomerUser customer = loginService.checkCustomer(username);
		List<ServiceRequest> list = serviceRequestRepository.findByCustomer(customer);
		List<ServiceRequestResource> resources = new LinkedList<>();
		for (ServiceRequest request : list) {
			resources.add(new ServiceRequestResource(request));
		}
		return new Resources<>(resources);
	}

	@Override
	public Page<ServiceRequestResource> getUserServiceRequestAdmin(Long uId, String username, Pageable pageable) {
		loginService.checkAdmin(username);
		CustomerUser customer = customerUserRepository.findOne(uId);
		if (customer == null) {
			throw new ApiException(HttpStatus.UNAUTHORIZED, "Customer User Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		Page<ServiceRequest> page = serviceRequestRepository.findByCustomer(customer, pageable);
		List<ServiceRequestResource> resources = new LinkedList<>();
		for (ServiceRequest request : page.getContent()) {
			resources.add(new ServiceRequestResource(request));
		}
		return new PageImpl<>(resources, pageable, page.getTotalElements());
	}

	@Override
	public Resources<ServiceRequestResource> getAllUserServiceRequestAdmin(Long uId, String username) {
		loginService.checkAdmin(username);
		CustomerUser customer = customerUserRepository.findOne(uId);
		if (customer == null) {
			throw new ApiException(HttpStatus.UNAUTHORIZED, "Customer User Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		List<ServiceRequest> list = serviceRequestRepository.findByCustomer(customer);
		List<ServiceRequestResource> resources = new LinkedList<>();
		for (ServiceRequest request : list) {
			resources.add(new ServiceRequestResource(request));
		}
		return new Resources<>(resources);
	}

	@Override
	public Page<ServiceRequestResource> getServiceCenterServiceRequest(ServiceRequestStatusType status, String username,
			Pageable pageable) {
		ServiceCenterAdminUser admin = loginService.checkServiceCenterAdmin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		Page<ServiceRequest> page;
		if (status != null) {
			page = serviceRequestRepository.findByServiceServiceCenterAndStatus(serviceCenter, status, pageable);
		} else
			page = serviceRequestRepository.findByServiceServiceCenter(serviceCenter, pageable);
		List<ServiceRequestResource> resources = new LinkedList<>();
		for (ServiceRequest request : page.getContent()) {
			resources.add(new ServiceRequestResource(request));
		}
		return new PageImpl<>(resources, pageable, page.getTotalElements());
	}

	@Override
	public Resources<ServiceRequestResource> getAllServiceCenterServiceRequest(ServiceRequestStatusType status,
			String username) {
		ServiceCenterAdminUser admin = loginService.checkServiceCenterAdmin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		List<ServiceRequest> list;
		if (status != null)
			list = serviceRequestRepository.findByServiceServiceCenterAndStatus(serviceCenter, status);
		else
			list = serviceRequestRepository.findByServiceServiceCenter(serviceCenter);
		List<ServiceRequestResource> resources = new LinkedList<>();
		for (ServiceRequest request : list) {
			resources.add(new ServiceRequestResource(request));
		}
		return new Resources<>(resources);
	}

	@Override
	public Page<ServiceRequestResource> getServiceCenterServiceRequestAdmin(Long scId, ServiceRequestStatusType status,
			String username, Pageable pageable) {
		loginService.checkAdmin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findOne(scId);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		Page<ServiceRequest> page;
		if (status != null) {
			page = serviceRequestRepository.findByServiceServiceCenterAndStatus(serviceCenter, status, pageable);
		} else
			page = serviceRequestRepository.findByServiceServiceCenter(serviceCenter, pageable);
		List<ServiceRequestResource> resources = new LinkedList<>();
		for (ServiceRequest request : page.getContent()) {
			resources.add(new ServiceRequestResource(request));
		}
		return new PageImpl<>(resources, pageable, page.getTotalElements());
	}

	@Override
	public Resources<ServiceRequestResource> getAllServiceCenterServiceRequestAdmin(Long scId,
			ServiceRequestStatusType status, String username) {
		loginService.checkAdmin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findOne(scId);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		List<ServiceRequest> list;
		if (status != null)
			list = serviceRequestRepository.findByServiceServiceCenterAndStatus(serviceCenter, status);
		else
			list = serviceRequestRepository.findByServiceServiceCenter(serviceCenter);
		List<ServiceRequestResource> resources = new LinkedList<>();
		for (ServiceRequest request : list) {
			resources.add(new ServiceRequestResource(request));
		}
		return new Resources<>(resources);
	}

	@Override
	public ServiceRequestResource getServiceRequest(Long srId, String username) {
		User user = loginService.checkLogin(username);
		ServiceRequest serviceRequest = serviceRequestRepository.findOne(srId);
		if (serviceRequest == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Request Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		if (user instanceof AdminUser || (user instanceof ServiceCenterAdminUser
				&& user.equals(serviceRequest.getService().getServiceCenter().getAdmin()))) {
			return new ServiceRequestResource(serviceRequest);
		} else {
			throw new ApiException(HttpStatus.UNAUTHORIZED, "User Don't Have Authority",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
	}

	@Override
	public String reviewServiceRequest(Long srId, ReviewCreateModel model, String username) {
		User user = loginService.checkLogin(username);
		ServiceRequest serviceRequest = serviceRequestRepository.findOne(srId);
		if (serviceRequest == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Request Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		if (!serviceRequest.getCustomer().equals(user)) {
			throw new ApiException(HttpStatus.UNAUTHORIZED, "User Not Authorized",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		if (!serviceRequest.getStatus().equals(ServiceRequestStatusType.DONE)) {
			throw new ApiException(HttpStatus.UNAUTHORIZED, "Service Request Is Not Yet Completed",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		Review already = reviewRepository.findByServiceRequest(serviceRequest);
		if (already != null) {
			already.setRating(model.getRating());
			already.setDescription(model.getDescription());
			reviewRepository.save(already);
		} else {
			Review review = new Review();
			review.setRating(model.getRating());
			review.setDescription(model.getDescription());
			review.setServiceRequest(serviceRequest);
			reviewRepository.save(review);
		}
		return JsonResponseUtils.createStatusResponse("Review Added Successfully");
	}

	@Override
	public String assignEngineer(Long srId, Long eId, String username) {
		User user = loginService.checkLogin(username);
		ServiceRequest serviceRequest = serviceRequestRepository.findOne(srId);
		if (serviceRequest == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Request Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		ServiceEngineerUser serviceEngineerUser = serviceEngineerUserRepository.findOne(eId);
		if (serviceEngineerUser == null
				|| !serviceEngineerUser.getServiceCenter().equals(serviceRequest.getService().getServiceCenter())) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Engineer Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		if (user instanceof AdminUser || (user instanceof ServiceCenterAdminUser
				&& user.equals(serviceRequest.getService().getServiceCenter().getAdmin()))) {
			AssignServiceEngineer assignServiceEngineer = new AssignServiceEngineer();
			assignServiceEngineer.setServiceEngineer(serviceEngineerUser);
			assignServiceEngineer.setServiceRequest(serviceRequest);
			assignServiceEngineerRepository.save(assignServiceEngineer);

			AssignServiceEngineerStatus assignServiceEngineerStatus = new AssignServiceEngineerStatus();
			assignServiceEngineerStatus.setAssignServiceEngineer(assignServiceEngineer);
			assignServiceEngineerStatus.setStatus(AssignServiceEngineerStatusType.WAITING);
			assignServiceEngineerStatus.setCreatedBy(user);

			assignServiceEngineerStatusRepository.save(assignServiceEngineerStatus);

			serviceRequest.setEngineerStatus(AssignServiceEngineerStatusType.WAITING);
			serviceRequest.setAssignServiceEngineer(assignServiceEngineer);
			serviceRequestRepository.save(serviceRequest);

		} else {
			throw new ApiException(HttpStatus.UNAUTHORIZED, "User Don't Have Authority",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		return JsonResponseUtils.createStatusResponse("Service Engineer Assigned Successfully");
	}

	@Override
	public Resources<ServiceRequestResource> getAllWaitingEngineerServiceRequest(String username) {
		ServiceEngineerUser user = loginService.checkServiceEngineer(username);
		List<ServiceRequest> list = serviceRequestRepository
				.findByAssignServiceEngineerServiceEngineerAndEngineerStatus(user,
						AssignServiceEngineerStatusType.WAITING);
		List<ServiceRequestResource> resources = new LinkedList<>();
		for (ServiceRequest request : list) {
			resources.add(new ServiceRequestResource(request));
		}
		return new Resources<>(resources);
	}

	@Override
	public Resources<ServiceRequestResource> getAllAcceptedEngineerServiceRequest(String username) {
		ServiceEngineerUser user = loginService.checkServiceEngineer(username);
		List<ServiceRequest> list = serviceRequestRepository
				.findByAssignServiceEngineerServiceEngineerAndEngineerStatus(user,
						AssignServiceEngineerStatusType.ACCEPTED);
		List<ServiceRequestResource> resources = new LinkedList<>();
		for (ServiceRequest request : list) {
			resources.add(new ServiceRequestResource(request));
		}
		return new Resources<>(resources);
	}

	@Override
	public String acceptServiceRequest(Long srId, String username) {
		ServiceEngineerUser user = loginService.checkServiceEngineer(username);
		ServiceRequest serviceRequest = serviceRequestRepository.findOne(srId);
		if (serviceRequest == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Request Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		if (serviceRequest.getAssignServiceEngineer() == null
				|| !serviceRequest.getAssignServiceEngineer().getServiceEngineer().equals(user)) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Request Not Accessed To You",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		if (!serviceRequest.getEngineerStatus().equals(AssignServiceEngineerStatusType.WAITING)) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Request Status Already Assigned",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		AssignServiceEngineer assignServiceEngineer = serviceRequest.getAssignServiceEngineer();
		AssignServiceEngineerStatus assignServiceEngineerStatus = new AssignServiceEngineerStatus();
		assignServiceEngineerStatus.setAssignServiceEngineer(assignServiceEngineer);
		assignServiceEngineerStatus.setStatus(AssignServiceEngineerStatusType.ACCEPTED);
		assignServiceEngineerStatus.setCreatedBy(user);

		assignServiceEngineerStatusRepository.save(assignServiceEngineerStatus);

		ServiceRequestStatus status = new ServiceRequestStatus();
		status.setStatus(ServiceRequestStatusType.ACCEPTED);
		status.setServiceRequest(serviceRequest);
		serviceRequestStatusRepository.save(status);

		serviceRequest.setStatus(ServiceRequestStatusType.ACCEPTED);
		serviceRequest.setEngineerStatus(AssignServiceEngineerStatusType.ACCEPTED);
		serviceRequestRepository.save(serviceRequest);
		return JsonResponseUtils.createStatusResponse("Service Request Accepted Successfully");
	}

	@Override
	public String startServiceRequest(Long srId, String username) {
		ServiceEngineerUser user = loginService.checkServiceEngineer(username);
		ServiceRequest serviceRequest = serviceRequestRepository.findOne(srId);
		if (serviceRequest == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Request Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		if (serviceRequest.getAssignServiceEngineer() == null
				|| !serviceRequest.getAssignServiceEngineer().getServiceEngineer().equals(user)) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Request Not Accessed To You",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		if (!serviceRequest.getStatus().equals(ServiceRequestStatusType.ACCEPTED)) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Request Can Not Be Started",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		ServiceRequestStatus status = new ServiceRequestStatus();
		status.setStatus(ServiceRequestStatusType.ON_ROUTE);
		status.setServiceRequest(serviceRequest);
		serviceRequestStatusRepository.save(status);

		serviceRequest.setStatus(ServiceRequestStatusType.ON_ROUTE);
		serviceRequest.setVerifyOtp(GeneralUtils.createToken());
		serviceRequest = serviceRequestRepository.save(serviceRequest);

		try {
			otpService.sendOtp(serviceRequest);
		} catch (IOException | JSONException e) {
			throw new ApiException(HttpStatus.UNAUTHORIZED, "Failed To Send OTP",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}

		return JsonResponseUtils.createStatusResponse("Service Request Started Successfully");
	}

	@Override
	public String verifyServiceRequest(Long srId, Integer otp, String username) {
		ServiceEngineerUser user = loginService.checkServiceEngineer(username);
		ServiceRequest serviceRequest = serviceRequestRepository.findOne(srId);
		if (serviceRequest == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Request Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		if (serviceRequest.getAssignServiceEngineer() == null
				|| !serviceRequest.getAssignServiceEngineer().getServiceEngineer().equals(user)) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Request Not Accessed To You",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		if (!serviceRequest.getStatus().equals(ServiceRequestStatusType.ON_ROUTE)) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Request Can Not Be Verified",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		if (!serviceRequest.getVerifyOtp().equals(otp)) {
			throw new ApiException(HttpStatus.EXPECTATION_FAILED, "OTP Does Not Match",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		ServiceRequestStatus status = new ServiceRequestStatus();
		status.setStatus(ServiceRequestStatusType.ON_PROGRESS);
		status.setServiceRequest(serviceRequest);
		serviceRequestStatusRepository.save(status);

		serviceRequest.setStatus(ServiceRequestStatusType.ON_PROGRESS);
		serviceRequest.setVerifyOtp(null);
		serviceRequestRepository.save(serviceRequest);

		return JsonResponseUtils.createStatusResponse("Service Request Verified Successfully");
	}

	@Override
	public String completeServiceRequest(Long srId, List<ReplacementPartCreateModel> parts, PaymentMode paymentMode,
			String username) {
		ServiceEngineerUser user = loginService.checkServiceEngineer(username);
		ServiceRequest serviceRequest = serviceRequestRepository.findOne(srId);
		if (serviceRequest == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Request Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		if (serviceRequest.getAssignServiceEngineer() == null
				|| !serviceRequest.getAssignServiceEngineer().getServiceEngineer().equals(user)) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Request Not Accessed To You",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		if (!serviceRequest.getStatus().equals(ServiceRequestStatusType.ON_PROGRESS)) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Request Can Not Be COMPLETED",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}

		Float totalAmount = serviceRequest.getServiceCharge();
		if (parts != null) {
			for (ReplacementPartCreateModel part : parts) {
				totalAmount += part.getPrice();
				ReplacementPart replacementPart = new ReplacementPart();
				replacementPart.setName(part.getName());
				replacementPart.setPrice(part.getPrice());
				replacementPart.setServiceRequest(serviceRequest);
				replacementPartRepository.save(replacementPart);
			}
		}
		try {
			serviceRequest.setPaymentMode(paymentMode);
			serviceRequest.setStatus(ServiceRequestStatusType.COMPLETED);
			serviceRequest.setTotalCharge(totalAmount);
			if (paymentMode.equals(PaymentMode.CASH)) {
				serviceRequest.setIsPaid(true);
			} else if (paymentMode.equals(PaymentMode.ONLINE)) {
				Order order = razorPayService.createOrder(totalAmount);
				serviceRequest.setIsPaid(false);
				serviceRequest.setOrderId(order.get("id").toString());
			}
			serviceRequestRepository.save(serviceRequest);

			ServiceRequestStatus status = new ServiceRequestStatus();
			status.setStatus(ServiceRequestStatusType.COMPLETED);
			status.setServiceRequest(serviceRequest);
			serviceRequestStatusRepository.save(status);

//
//			ServiceRequestStatus status = new ServiceRequestStatus();
//			status.setStatus(ServiceRequestStatusType.COMPLETED);
//			status.setServiceRequest(serviceRequest);
//			serviceRequestStatusRepository.save(status);

//			serviceRequest.setStatus(ServiceRequestStatusType.COMPLETED);
//			serviceRequestRepository.save(serviceRequest);

			return JsonResponseUtils.createStatusResponse("Service Request Completed Successfully");
		} catch (JSONException | RazorpayException e) {
			e.printStackTrace();
		}

		throw new ApiException(HttpStatus.EXPECTATION_FAILED, "Failed To Complete Service Request",
				Thread.currentThread().getStackTrace()[1].getClassName(),
				Thread.currentThread().getStackTrace()[1].getMethodName(),
				Thread.currentThread().getStackTrace()[1].getLineNumber());

	}

	@Override
	public String doneServiceRequest(Long srId, String username) {
		CustomerUser user = loginService.checkCustomer(username);
		ServiceRequest serviceRequest = serviceRequestRepository.findOne(srId);
		if (serviceRequest == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Request Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		if (serviceRequest.getCustomer() == null || !serviceRequest.getCustomer().equals(user)) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Request Not Accessed To You",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		if (!serviceRequest.getStatus().equals(ServiceRequestStatusType.COMPLETED)) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Request Can Not Be DONE",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		if (serviceRequest.getPaymentMode().equals(PaymentMode.ONLINE)) {
			try {
				Order order = razorPayService.getOrder(serviceRequest.getOrderId());
				if (order.get("status").toString().equals("paid")) {
					serviceRequest.setIsPaid(true);
				} else {
					throw new ApiException(HttpStatus.EXPECTATION_FAILED, "Payment Not Completed",
							Thread.currentThread().getStackTrace()[1].getClassName(),
							Thread.currentThread().getStackTrace()[1].getMethodName(),
							Thread.currentThread().getStackTrace()[1].getLineNumber());
				}
			} catch (RazorpayException e) {
				e.printStackTrace();
			}
		}
		ServiceRequestStatus status = new ServiceRequestStatus();
		status.setStatus(ServiceRequestStatusType.DONE);
		status.setServiceRequest(serviceRequest);
		serviceRequestStatusRepository.save(status);

		serviceRequest.setStatus(ServiceRequestStatusType.DONE);
		serviceRequestRepository.save(serviceRequest);

		return JsonResponseUtils.createStatusResponse("Service Request Done Successfully");
	}

	@Override
	public Page<ServiceRequestResource> getWaitingEngineerServiceRequest(String username, Pageable pageable) {
		ServiceEngineerUser user = loginService.checkServiceEngineer(username);
		Page<ServiceRequest> page = serviceRequestRepository
				.findByAssignServiceEngineerServiceEngineerAndEngineerStatus(user,
						AssignServiceEngineerStatusType.WAITING, pageable);
		List<ServiceRequestResource> resources = new LinkedList<>();
		for (ServiceRequest request : page.getContent()) {
			resources.add(new ServiceRequestResource(request));
		}
		return new PageImpl<>(resources, pageable, page.getTotalElements());
	}

	@Override
	public Page<ServiceRequestResource> getAcceptedEngineerServiceRequest(String username, Pageable pageable) {
		ServiceEngineerUser user = loginService.checkServiceEngineer(username);
//		Page<ServiceRequest> page = serviceRequestRepository.getServiceRequestNotStatus(
//				AssignServiceEngineerStatusType.ACCEPTED, user, ServiceRequestStatusType.COMPLETED, pageable);

		Page<ServiceRequest> page = serviceRequestRepository
				.getServiceRequest(AssignServiceEngineerStatusType.ACCEPTED, user,
						Arrays.asList(ServiceRequestStatusType.ACCEPTED, ServiceRequestStatusType.ON_ROUTE,
								ServiceRequestStatusType.ON_PROGRESS, ServiceRequestStatusType.WAITING_FOR_PARTS),
						pageable);

		List<ServiceRequestResource> resources = new LinkedList<>();
		for (ServiceRequest request : page.getContent()) {
			resources.add(new ServiceRequestResource(request));
		}
		return new PageImpl<>(resources, pageable, page.getTotalElements());
	}

	@Override
	public Page<ServiceRequestResource> getCompletedEngineerServiceRequest(String username, Pageable pageable) {
		ServiceEngineerUser user = loginService.checkServiceEngineer(username);
		Page<ServiceRequest> page = serviceRequestRepository.getServiceRequest(AssignServiceEngineerStatusType.ACCEPTED,
				user, Arrays.asList(ServiceRequestStatusType.COMPLETED, ServiceRequestStatusType.DONE), pageable);
		List<ServiceRequestResource> resources = new LinkedList<>();
		for (ServiceRequest request : page.getContent()) {
			resources.add(new ServiceRequestResource(request));
		}
		return new PageImpl<>(resources, pageable, page.getTotalElements());
	}

	@Override
	public Resources<ServiceRequestResource> getPendingServiceRequest(String username) {
		User user = loginService.checkLogin(username);
		List<ServiceRequestResource> resources = new LinkedList<>();
		if (user instanceof ServiceCenterAdminUser) {
			ServiceCenterAdminUser admin = (ServiceCenterAdminUser) user;
			ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
			if (serviceCenter == null) {
				throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
						Thread.currentThread().getStackTrace()[1].getClassName(),
						Thread.currentThread().getStackTrace()[1].getMethodName(),
						Thread.currentThread().getStackTrace()[1].getLineNumber());
			}
			List<ServiceRequest> list;
			list = serviceRequestRepository.findByServiceServiceCenterAndStatusIn(serviceCenter,
					Arrays.asList(ServiceRequestStatusType.WAITING));
			for (ServiceRequest request : list) {
				resources.add(new ServiceRequestResource(request));
			}
		} else if (user instanceof AdminUser) {
			List<ServiceRequest> list;
			list = serviceRequestRepository.findByStatusIn(Arrays.asList(ServiceRequestStatusType.WAITING));
			for (ServiceRequest request : list) {
				resources.add(new ServiceRequestResource(request));
			}
		}

		return new Resources<>(resources);
	}

	@Override
	public Resources<ServiceRequestResource> getOnProgressServiceRequest(String username) {
		User user = loginService.checkLogin(username);
		List<ServiceRequestResource> resources = new LinkedList<>();
		if (user instanceof ServiceCenterAdminUser) {
			ServiceCenterAdminUser admin = (ServiceCenterAdminUser) user;
			ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
			if (serviceCenter == null) {
				throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
						Thread.currentThread().getStackTrace()[1].getClassName(),
						Thread.currentThread().getStackTrace()[1].getMethodName(),
						Thread.currentThread().getStackTrace()[1].getLineNumber());
			}
			List<ServiceRequest> list;
			list = serviceRequestRepository.findByServiceServiceCenterAndStatusIn(serviceCenter,
					Arrays.asList(ServiceRequestStatusType.ACCEPTED, ServiceRequestStatusType.ON_ROUTE,
							ServiceRequestStatusType.ON_PROGRESS));
			for (ServiceRequest request : list) {
				resources.add(new ServiceRequestResource(request));
			}
		} else if (user instanceof AdminUser) {
			List<ServiceRequest> list;
			list = serviceRequestRepository.findByStatusIn(Arrays.asList(ServiceRequestStatusType.ACCEPTED,
					ServiceRequestStatusType.ON_ROUTE, ServiceRequestStatusType.ON_PROGRESS));
			for (ServiceRequest request : list) {
				resources.add(new ServiceRequestResource(request));
			}
		}

		return new Resources<>(resources);
	}

	@Override
	public Resources<ServiceRequestResource> getCompletedServiceRequest(String username) {
		User user = loginService.checkLogin(username);
		List<ServiceRequestResource> resources = new LinkedList<>();
		if (user instanceof ServiceCenterAdminUser) {
			ServiceCenterAdminUser admin = (ServiceCenterAdminUser) user;
			ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
			if (serviceCenter == null) {
				throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
						Thread.currentThread().getStackTrace()[1].getClassName(),
						Thread.currentThread().getStackTrace()[1].getMethodName(),
						Thread.currentThread().getStackTrace()[1].getLineNumber());
			}
			List<ServiceRequest> list;
			list = serviceRequestRepository.findByServiceServiceCenterAndStatusIn(serviceCenter,
					Arrays.asList(ServiceRequestStatusType.COMPLETED, ServiceRequestStatusType.DONE));
			for (ServiceRequest request : list) {
				resources.add(new ServiceRequestResource(request));
			}
		} else if (user instanceof AdminUser) {
			List<ServiceRequest> list = serviceRequestRepository
					.findByStatusIn(Arrays.asList(ServiceRequestStatusType.COMPLETED, ServiceRequestStatusType.DONE));
			for (ServiceRequest request : list) {
				resources.add(new ServiceRequestResource(request));
			}
		}
		return new Resources<>(resources);
	}

	@Override
	public ReviewDashboardResource getReviewDashboard(String username) {
		ServiceCenterAdminUser admin = loginService.checkServiceCenterAdmin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		List<Double> overAllRatingList = reviewRepository.getOverAllRating(serviceCenter);
		Double overAllRating = -1d;
		for (Double r : overAllRatingList) {
			if (r != null)
				overAllRating = r;
		}
		long totalRating = reviewRepository.countByServiceRequestServiceServiceCenter(serviceCenter);

		ReviewDashboardResource dashboard = new ReviewDashboardResource();

		dashboard.setOverAllRating(overAllRating);
		dashboard.setTotalRating(totalRating);

		for (double i = 1; i <= 5; i++) {
			long tr = reviewRepository.countByServiceRequestServiceServiceCenterAndRating(serviceCenter, i);

			dashboard.addReviewPerRating(i, tr);
		}

		return dashboard;

	}

	@Override
	public Resources<ReviewResource> getAllReview(String username) {
		ServiceCenterAdminUser admin = loginService.checkServiceCenterAdmin(username);
		ServiceCenter serviceCenter = serviceCenterRepository.findByAdmin(admin);
		if (serviceCenter == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Service Center Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		List<Review> list = reviewRepository.findByServiceRequestServiceServiceCenter(serviceCenter);
		List<ReviewResource> resources = new LinkedList<>();
		for (Review review : list) {
			resources.add(new ReviewResource(review));
		}
		return new Resources<>(resources);
	}

	@Override
	public ReviewResource getReview(Long rId, String username) {
		loginService.checkLogin(username);
		Review review = reviewRepository.findOne(rId);
		if (review == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Review Not Found",
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		return new ReviewResource(review);
	}

	@Override
	public Resources<ServiceRequestResource> getPaymentHistory(Long date, String username) {
		loginService.checkAdmin(username);
		Calendar fCalendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
		fCalendar.setTime(new Date(date));
		Date fromDate = fCalendar.getTime();
		
		System.out.println(date);
		System.out.println(new Date(date) );

		fCalendar.add(Calendar.DATE, 1);
		Date toDate = fCalendar.getTime();
		System.out.println(fromDate + " " + toDate);
		List<ServiceRequest> list;
		list = serviceRequestRepository.getPaymentHistory( fromDate, toDate);
		List<ServiceRequestResource> resources = new LinkedList<>();
		for (ServiceRequest request : list) {
			resources.add(new ServiceRequestResource(request));
		}
		return new Resources<>(resources);
	}

}
