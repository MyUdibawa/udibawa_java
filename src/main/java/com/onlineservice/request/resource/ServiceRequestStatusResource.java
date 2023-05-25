package com.onlineservice.request.resource;

import java.util.Date;

import com.onlineservice.request.model.ServiceRequestStatus;
import com.onlineservice.request.model.ServiceRequestStatusType;

public class ServiceRequestStatusResource {
	private Long srsId;
	private ServiceRequestStatusType status;
	private Date creationDate;
	private Date updatedDate;

	public ServiceRequestStatusResource(ServiceRequestStatus serviceRequestStatus) {
		this.srsId = serviceRequestStatus.getId();
		this.status = serviceRequestStatus.getStatus();
		this.creationDate = serviceRequestStatus.getCreationDate();
		this.updatedDate = serviceRequestStatus.getUpdatedDate();
	}

	public Long getSrsId() {
		return srsId;
	}

	public ServiceRequestStatusType getStatus() {
		return status;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}
	
	
}
