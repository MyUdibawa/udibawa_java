package com.onlineservice.request.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onlineservice.center.model.ServiceCenter;
import com.onlineservice.request.model.AssignServiceEngineerStatusType;
import com.onlineservice.request.model.ServiceRequest;
import com.onlineservice.request.model.ServiceRequestStatusType;
import com.onlineservice.user.model.CustomerUser;
import com.onlineservice.user.model.ServiceEngineerUser;

@Repository
public interface ServiceRequestRepository extends PagingAndSortingRepository<ServiceRequest, Long> {

	Page<ServiceRequest> findByCustomer(CustomerUser customer, Pageable pageable);

	List<ServiceRequest> findByCustomer(CustomerUser customer);

	Page<ServiceRequest> findByServiceServiceCenter(ServiceCenter serviceCenter, Pageable pageable);

	Page<ServiceRequest> findByServiceServiceCenterAndStatus(ServiceCenter serviceCenter,
			ServiceRequestStatusType status, Pageable pageable);

	List<ServiceRequest> findByServiceServiceCenter(ServiceCenter serviceCenter);

	List<ServiceRequest> findByStatusIn(List<ServiceRequestStatusType> statuses);
	
	List<ServiceRequest> findByServiceServiceCenterAndStatusIn(ServiceCenter serviceCenter,
			List<ServiceRequestStatusType> statuses);

	List<ServiceRequest> findByServiceServiceCenterAndStatus(ServiceCenter serviceCenter,
			ServiceRequestStatusType status);

	List<ServiceRequest> findByAssignServiceEngineerServiceEngineerAndEngineerStatus(
			ServiceEngineerUser serviceEngineerUser, AssignServiceEngineerStatusType engineerStatus);

	Page<ServiceRequest> findByAssignServiceEngineerServiceEngineerAndEngineerStatus(
			ServiceEngineerUser serviceEngineerUser, AssignServiceEngineerStatusType engineerStatus, Pageable pageable);

	Page<ServiceRequest> findByAssignServiceEngineerServiceEngineerAndEngineerStatusAndStatusIsNot(
			ServiceEngineerUser serviceEngineerUser, AssignServiceEngineerStatusType engineerStatus, Pageable pageable);

	@Query("SELECT sr FROM ServiceRequest sr WHERE sr.engineerStatus = :engineerStatus AND sr.assignServiceEngineer.serviceEngineer=:serviceEngineer AND sr.status=:status")
	Page<ServiceRequest> getServiceRequest(@Param("engineerStatus") AssignServiceEngineerStatusType engineerStatus,
			@Param("serviceEngineer") ServiceEngineerUser serviceEngineerUser,
			@Param("status") ServiceRequestStatusType status, Pageable pageable);

	@Query("SELECT sr FROM ServiceRequest sr WHERE sr.engineerStatus = :engineerStatus AND sr.assignServiceEngineer.serviceEngineer=:serviceEngineer AND sr.status in :statuses")
	Page<ServiceRequest> getServiceRequest(@Param("engineerStatus") AssignServiceEngineerStatusType engineerStatus,
			@Param("serviceEngineer") ServiceEngineerUser serviceEngineerUser,
			@Param("statuses") List<ServiceRequestStatusType> statuses, Pageable pageable);

	@Query("SELECT sr FROM ServiceRequest sr WHERE sr.engineerStatus = :engineerStatus AND sr.assignServiceEngineer.serviceEngineer=:serviceEngineer AND sr.status!=:status")
	Page<ServiceRequest> getServiceRequestNotStatus(
			@Param("engineerStatus") AssignServiceEngineerStatusType engineerStatus,
			@Param("serviceEngineer") ServiceEngineerUser serviceEngineerUser,
			@Param("status") ServiceRequestStatusType status, Pageable pageable);
	
	long countByServiceServiceCenter(ServiceCenter serviceCenter);
	
	@Query("SELECT sr FROM ServiceRequest sr WHERE sr.creationDate BETWEEN :fromDate AND :toDate AND sr.isPaid=true")
	List<ServiceRequest> getPaymentHistory(@Param("fromDate") Date fromDate,@Param("toDate") Date toDate);
}
