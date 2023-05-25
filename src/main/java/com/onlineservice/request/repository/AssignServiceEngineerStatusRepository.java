package com.onlineservice.request.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.onlineservice.request.model.AssignServiceEngineerStatus;

@Repository
public interface AssignServiceEngineerStatusRepository
		extends PagingAndSortingRepository<AssignServiceEngineerStatus, Long> {

}
