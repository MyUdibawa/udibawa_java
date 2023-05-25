package com.onlineservice.request.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.stereotype.Repository;

import com.onlineservice.request.model.AssignServiceEngineer;

@Repository
public interface AssignServiceEngineerRepository extends PagingAndSortingRepository<AssignServiceEngineer, Long> {

}
