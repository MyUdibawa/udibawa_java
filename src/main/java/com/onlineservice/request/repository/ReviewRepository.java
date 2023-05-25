package com.onlineservice.request.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onlineservice.center.model.OfferedService;
import com.onlineservice.center.model.ServiceCenter;
import com.onlineservice.request.model.Review;
import com.onlineservice.request.model.ServiceRequest;

@Repository
public interface ReviewRepository extends PagingAndSortingRepository<Review, Long> {
	Review findByServiceRequest(ServiceRequest serviceRequest);

	@Query("SELECT SUM(r.rating)/COUNT(r) FROM Review r")
	List<Double> rating();

	@Query("SELECT SUM(r.rating)/COUNT(r) FROM Review r WHERE r.serviceRequest.service=:service")
	List<Double> ratingOfService(@Param("service") OfferedService service);

	@Query("SELECT SUM(r.rating)/COUNT(r) FROM Review r WHERE r.serviceRequest.service.serviceCenter=:serviceCenter")
	List<Double> getOverAllRating(@Param("serviceCenter") ServiceCenter serviceCenter);


	List<Review> findByServiceRequestServiceServiceCenter(ServiceCenter serviceCenter);

	long countByServiceRequestServiceServiceCenter(ServiceCenter serviceCenter);

	long countByServiceRequestServiceServiceCenterAndRating(ServiceCenter serviceCenter, Double rating);
}
