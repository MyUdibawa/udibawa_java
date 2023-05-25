package com.onlineservice.center.resource;

import java.util.Date;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.onlineservice.center.model.OfferedService;
import com.onlineservice.center.model.ServiceCenter;
import com.onlineservice.center.model.ServiceType;

public class DetailedOfferedServiceResource extends ResourceSupport {
	private Long osId;
	private ServiceType serviceType;
	private Float serviceCharge;
	private String pincodes;

	private Long scId;
	private String scName;

	private Double rating;

	private Date creationDate;
	private Date updatedDate;

	public DetailedOfferedServiceResource(Double rating, OfferedService offeredService) {
		this.osId = offeredService.getId();
		this.serviceType = offeredService.getServiceType();
		this.serviceCharge = offeredService.getServiceCharge();
		this.pincodes = offeredService.getPincodes();

		ServiceCenter center = offeredService.getServiceCenter();
		this.scId = center.getId();
		this.scName = center.getName();

		if (center.getImage() != null && !center.getImage().isEmpty())
			add(new Link(center.getImage()).withRel("scImage"));

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

	public Long getScId() {
		return scId;
	}

	public String getScName() {
		return scName;
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
