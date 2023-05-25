package com.onlineservice.request.service;

import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import com.onlineservice.request.resource.ReviewDashboardResource;
import com.onlineservice.request.resource.ReviewResource;

@Service
public interface ReviewService {

	public ReviewDashboardResource getReviewDashboard(String username);

	public Resources<ReviewResource> getAllReview(String username);

	public ReviewResource getReview(Long rId, String username);

}
