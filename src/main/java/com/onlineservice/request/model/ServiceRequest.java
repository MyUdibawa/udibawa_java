package com.onlineservice.request.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.onlineservice.center.model.OfferedService;
import com.onlineservice.user.model.CustomerAddress;
import com.onlineservice.user.model.CustomerUser;

@Entity
public class ServiceRequest {
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	@Column(columnDefinition = "TEXT")
	private String description;
	private String image;

	@ManyToOne
	private CustomerUser customer;

	@ManyToOne
	private CustomerAddress address;

	@ManyToOne
	private OfferedService service;

	@OneToMany(mappedBy = "serviceRequest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ReplacementPart> parts = new LinkedList<ReplacementPart>();

	@OneToMany(mappedBy = "serviceRequest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AssignServiceEngineer> assignServiceEngineers = new LinkedList<>();

	@OneToMany(mappedBy = "serviceRequest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ServiceRequestStatus> statuses = new LinkedList<>();

	@OneToOne(mappedBy = "serviceRequest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Review review;

	private Float serviceCharge;

	private Float totalCharge;

	private String orderId;
	private Boolean isPaid = false;

	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;

	@Enumerated(EnumType.STRING)
	private ServiceRequestStatusType status;

	@ManyToOne
	private AssignServiceEngineer assignServiceEngineer;

	@Enumerated(EnumType.STRING)
	private AssignServiceEngineerStatusType engineerStatus;

	private Date requestedDate;
	private String requestedTime;

	private Integer verifyOtp;

	@CreationTimestamp
	private Date creationDate;
	@UpdateTimestamp
	private Date updatedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public CustomerUser getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerUser customer) {
		this.customer = customer;
	}

	public CustomerAddress getAddress() {
		return address;
	}

	public void setAddress(CustomerAddress address) {
		this.address = address;
	}

	public OfferedService getService() {
		return service;
	}

	public void setService(OfferedService service) {
		this.service = service;
	}

	public List<ReplacementPart> getParts() {
		return parts;
	}

	public void setParts(List<ReplacementPart> parts) {
		this.parts = parts;
	}

	public List<AssignServiceEngineer> getAssignServiceEngineers() {
		return assignServiceEngineers;
	}

	public void setAssignServiceEngineers(List<AssignServiceEngineer> assignServiceEngineers) {
		this.assignServiceEngineers = assignServiceEngineers;
	}

	public List<ServiceRequestStatus> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<ServiceRequestStatus> statuses) {
		this.statuses = statuses;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public Float getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(Float serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public Float getTotalCharge() {
		return totalCharge;
	}

	public void setTotalCharge(Float totalCharge) {
		this.totalCharge = totalCharge;
	}

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public ServiceRequestStatusType getStatus() {
		return status;
	}

	public void setStatus(ServiceRequestStatusType status) {
		this.status = status;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getRequestedTime() {
		return requestedTime;
	}

	public void setRequestedTime(String requestedTime) {
		this.requestedTime = requestedTime;
	}

	public AssignServiceEngineerStatusType getEngineerStatus() {
		return engineerStatus;
	}

	public void setEngineerStatus(AssignServiceEngineerStatusType engineerStatus) {
		this.engineerStatus = engineerStatus;
	}

	public AssignServiceEngineer getAssignServiceEngineer() {
		return assignServiceEngineer;
	}

	public void setAssignServiceEngineer(AssignServiceEngineer assignServiceEngineer) {
		this.assignServiceEngineer = assignServiceEngineer;
	}

	public Integer getVerifyOtp() {
		return verifyOtp;
	}

	public void setVerifyOtp(Integer verifyOtp) {
		this.verifyOtp = verifyOtp;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

}
