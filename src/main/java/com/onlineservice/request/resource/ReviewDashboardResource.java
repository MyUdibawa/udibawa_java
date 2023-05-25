package com.onlineservice.request.resource;

import java.util.LinkedList;
import java.util.List;

public class ReviewDashboardResource {

	private Double overAllRating;
	private long totalRating;
	private List<ReviewPerRating> reviewPerRating = new LinkedList<>();

	public ReviewDashboardResource() {
	}

	public Double getOverAllRating() {
		return overAllRating;
	}

	public void setOverAllRating(Double overAllRating) {
		this.overAllRating = overAllRating;
	}

	public long getTotalRating() {
		return totalRating;
	}

	public void setTotalRating(long totalRating) {
		this.totalRating = totalRating;
	}

	public void addReviewPerRating(double rating, long totalRating) {
		reviewPerRating.add(new ReviewPerRating(rating, totalRating));
	}

	public List<ReviewPerRating> getReviewPerRating() {
		return reviewPerRating;
	}

	class ReviewPerRating {
		double rating;
		long totalRating;

		public ReviewPerRating(double rating, long totalRating) {
			this.rating = rating;
			this.totalRating = totalRating;
		}

		public double getRating() {
			return rating;
		}

		public long getTotalRating() {
			return totalRating;
		}

	}
}
