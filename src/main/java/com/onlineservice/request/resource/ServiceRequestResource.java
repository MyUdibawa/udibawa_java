package com.onlineservice.request.resource;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.onlineservice.center.resource.OfferedServiceResource;
import com.onlineservice.center.resource.ServiceCenterResource;
import com.onlineservice.request.model.AssignServiceEngineer;
import com.onlineservice.request.model.AssignServiceEngineerStatusType;
import com.onlineservice.request.model.PaymentMode;
import com.onlineservice.request.model.ReplacementPart;
import com.onlineservice.request.model.ServiceRequest;
import com.onlineservice.request.model.ServiceRequestStatus;
import com.onlineservice.request.model.ServiceRequestStatusType;
import com.onlineservice.user.resource.CustomerAddressResource;
import com.onlineservice.user.resource.UserResource;

public class ServiceRequestResource extends ResourceSupport {
	private Long srId;
	private String description;
	private CustomerAddressResource address;
	private OfferedServiceResource offeredService;
	private ServiceCenterResource serviceCenter;
	private List<ServiceRequestStatusResource> statuses = new LinkedList<>();
	private List<AssignServiceEngineerResource> assignServiceEngineers = new LinkedList<>();
	private List<ReplacementPartResource> parts = new LinkedList<>();
	private UserResource customer;
	private Float serviceCharge;
	private Float totalCharge;
	private String orderId;
	private Boolean isPaid = false;
	private PaymentMode paymentMode;
	private Date creationDate;
	private Date updatedDate;
	private ServiceRequestStatusType status;
	private AssignServiceEngineerResource assignServiceEngineer;
	private AssignServiceEngineerStatusType engineerStatus;
	private ReviewResource review;
	private Date requestedDate;
	private String requestedTime;

	private Integer verifyOtp;

	public ServiceRequestResource(ServiceRequest serviceRequest) {
		this.srId = serviceRequest.getId();
		this.description = serviceRequest.getDescription();
		this.address = new CustomerAddressResource(serviceRequest.getAddress());
		this.offeredService = new OfferedServiceResource(serviceRequest.getService(), 0d);
		this.serviceCenter = new ServiceCenterResource(serviceRequest.getService().getServiceCenter());
		for (ServiceRequestStatus status : serviceRequest.getStatuses()) {
			this.statuses.add(new ServiceRequestStatusResource(status));
		}
		for (AssignServiceEngineer engineer : serviceRequest.getAssignServiceEngineers()) {
			this.assignServiceEngineers.add(new AssignServiceEngineerResource(engineer));
		}
		for (ReplacementPart part : serviceRequest.getParts()) {
			this.parts.add(new ReplacementPartResource(part));
		}
		this.customer = new UserResource(serviceRequest.getCustomer());
		this.serviceCharge = serviceRequest.getServiceCharge();
		this.totalCharge = serviceRequest.getTotalCharge();
		this.isPaid = serviceRequest.getIsPaid();
		this.paymentMode = serviceRequest.getPaymentMode();
		this.orderId = serviceRequest.getOrderId();
		this.creationDate = serviceRequest.getCreationDate();
		this.updatedDate = serviceRequest.getUpdatedDate();
		this.status = serviceRequest.getStatus();
		if (serviceRequest.getAssignServiceEngineer() != null)
			this.assignServiceEngineer = new AssignServiceEngineerResource(serviceRequest.getAssignServiceEngineer());
		this.engineerStatus = serviceRequest.getEngineerStatus();
		if (serviceRequest.getReview() != null)
			this.review = new ReviewResource(serviceRequest.getReview());
		this.requestedDate = serviceRequest.getRequestedDate();
		this.requestedTime = serviceRequest.getRequestedTime();

		this.verifyOtp = serviceRequest.getVerifyOtp();
	}

	public Long getSrId() {
		return srId;
	}

	public String getDescription() {
		return description;
	}

	public CustomerAddressResource getAddress() {
		return address;
	}

	public OfferedServiceResource getOfferedService() {
		return offeredService;
	}

	public UserResource getCustomer() {
		return customer;
	}

	public Float getServiceCharge() {
		return serviceCharge;
	}

	public Float getTotalCharge() {
		return totalCharge;
	}

	public Boolean getIsPaid() {
		return isPaid;
	}

	public String getOrderId() {
		return orderId;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public ServiceCenterResource getServiceCenter() {
		return serviceCenter;
	}

	public List<ServiceRequestStatusResource> getStatuses() {
		return statuses;
	}

	public ServiceRequestStatusType getStatus() {
		return status;
	}

	public List<AssignServiceEngineerResource> getAssignServiceEngineers() {
		return assignServiceEngineers;
	}

	public ReviewResource getReview() {
		return review;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public String getRequestedTime() {
		return requestedTime;
	}

	public AssignServiceEngineerResource getAssignServiceEngineer() {
		return assignServiceEngineer;
	}

	public AssignServiceEngineerStatusType getEngineerStatus() {
		return engineerStatus;
	}

	public Integer getVerifyOtp() {
		return verifyOtp;
	}

	public List<ReplacementPartResource> getParts() {
		return parts;
	}

}
