package com.onlineservice.request.resource;

import java.util.Date;

import com.onlineservice.center.resource.OfferedServiceResource;
import com.onlineservice.request.model.Review;
import com.onlineservice.request.model.ServiceRequest;
import com.onlineservice.user.resource.UserResource;

public class ReviewResource {
	private Long rId;
	private Long srId;
	private Double rating;
	private String description;
	private UserResource customer;
	private OfferedServiceResource service;
	private Date creationDate;
	private Date updatedDate;

	public ReviewResource(Review review) {
		this.rId = review.getId();
		this.rating = review.getRating();
		this.description = review.getDescription();
		ServiceRequest request = review.getServiceRequest();
		this.srId = request.getId();
		this.customer = new UserResource(request.getCustomer());
		this.service = new OfferedServiceResource(request.getService(), 0d);
		this.creationDate = review.getCreationDate();
		this.updatedDate = review.getUpdatedDate();
	}

	public Long getrId() {
		return rId;
	}

	public Long getSrId() {
		return srId;
	}

	public Double getRating() {
		return rating;
	}

	public String getDescription() {
		return description;
	}

	public UserResource getCustomer() {
		return customer;
	}

	public OfferedServiceResource getService() {
		return service;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

}