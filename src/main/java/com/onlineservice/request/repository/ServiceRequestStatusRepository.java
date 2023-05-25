package com.onlineservice.request.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.onlineservice.request.model.ServiceRequestStatus;

@Repository
public interface ServiceRequestStatusRepository extends PagingAndSortingRepository<ServiceRequestStatus, Long> {

}
