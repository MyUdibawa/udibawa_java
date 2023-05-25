package com.onlineservice.center.resource;

import java.util.Date;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.onlineservice.center.model.ServiceCenter;
import com.onlineservice.user.resource.UserResource;

public class ServiceCenterResource extends ResourceSupport {
	private Long scId;
	private String name;
	private String description;
	private String gstin;
	private String pan;

	private UserResource admin;

	private ServiceCenterAddressResource address;

	private Boolean isVerified = false;

	private Date creationDate;
	private Date updatedDate;

	public ServiceCenterResource(ServiceCenter serviceCenter) {
		this.scId = serviceCenter.getId();
		this.name = serviceCenter.getName();
		this.description = serviceCenter.getDescription();
		this.gstin = serviceCenter.getGstin();
		this.pan = serviceCenter.getPan();

		if (serviceCenter.getImage() != null && !serviceCenter.getImage().isEmpty())
			add(new Link(serviceCenter.getImage()).withRel("image"));

		this.admin = new UserResource(serviceCenter.getAdmin());

		this.address = new ServiceCenterAddressResource(serviceCenter.getAddress());

		this.isVerified = serviceCenter.getIsVerified();
		this.creationDate = serviceCenter.getCreationDate();
		this.updatedDate = serviceCenter.getUpdatedDate();
	}

	public Long getScId() {
		return scId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getGstin() {
		return gstin;
	}

	public String getPan() {
		return pan;
	}

	public UserResource getAdmin() {
		return admin;
	}

	public ServiceCenterAddressResource getAddress() {
		return address;
	}

	public Boolean getIsVerified() {
		return isVerified;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

}
