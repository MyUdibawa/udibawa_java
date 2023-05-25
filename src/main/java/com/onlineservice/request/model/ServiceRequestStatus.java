package com.onlineservice.request.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class ServiceRequestStatus {

	@Id
	@GeneratedValue
	private Long id;

	@Enumerated(EnumType.STRING)
	private ServiceRequestStatusType status;

	@ManyToOne
	private ServiceRequest serviceRequest;

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

	public ServiceRequestStatusType getStatus() {
		return status;
	}

	public void setStatus(ServiceRequestStatusType status) {
		this.status = status;
	}

	public ServiceRequest getServiceRequest() {
		return serviceRequest;
	}

	public void setServiceRequest(ServiceRequest serviceRequest) {
		this.serviceRequest = serviceRequest;
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

}
