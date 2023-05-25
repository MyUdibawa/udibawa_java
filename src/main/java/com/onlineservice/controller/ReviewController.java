package com.onlineservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineservice.request.resource.ReviewDashboardResource;
import com.onlineservice.request.resource.ReviewResource;
import com.onlineservice.request.service.ReviewService;

@RestController
@RequestMapping("review")
@CrossOrigin
public class ReviewController {

	private ReviewService reviewService;

	@Autowired
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@GetMapping("dashboard")
	public ResponseEntity<ReviewDashboardResource> getReviewDashboard(Authentication authentication) {
		return ResponseEntity.ok(reviewService.getReviewDashboard(authentication.getName()));
	}

	@GetMapping("all")
	public ResponseEntity<Resources<ReviewResource>> getAllReview(Authentication authentication) {
		return ResponseEntity.ok(reviewService.getAllReview(authentication.getName()));
	}

	@GetMapping("{rId}")
	public ResponseEntity<ReviewResource> getReview(@PathVariable("rId") Long rId, Authentication authentication) {
		return ResponseEntity.ok(reviewService.getReview(rId, authentication.getName()));
	}

}
