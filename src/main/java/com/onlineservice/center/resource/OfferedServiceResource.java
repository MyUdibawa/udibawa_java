package com.onlineservice.center.resource;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import com.onlineservice.center.model.OfferedService;
import com.onlineservice.center.model.ServiceType;

public class OfferedServiceResource extends ResourceSupport {
	private Long osId;
	private ServiceType serviceType;
	private Float serviceCharge;
	private String pincodes;

	private Double rating;
	
	private Date creationDate;
	private Date updatedDate;

	public OfferedServiceResource(OfferedService offeredService,Double rating) {
		this.osId = offeredService.getId();
		this.serviceType = offeredService.getServiceType();
		this.serviceCharge = offeredService.getServiceCharge();
		this.pincodes = offeredService.getPincodes();
		this.rating = rating;
		this.creationDate = offeredService.getCreationDate();
		this.updatedDate = offeredService.getUpdatedDate();
	}

	public Long getOsId() {
		return osId;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public Float getServiceCharge() {
		return serviceCharge;
	}

	public String getPincodes() {
		return pincodes;
	}

	public Double getRating() {
		return rating;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

}
