package com.onlineservice.user.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.onlineservice.user.model.ServiceCenterAdminUser;

@Repository
public interface ServiceCenterAdminUserRepository extends PagingAndSortingRepository<ServiceCenterAdminUser, Long> {

}
