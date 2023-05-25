package com.onlineservice.center.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.onlineservice.center.model.ServiceCenterAddress;

@Repository
public interface ServiceCenterAddressRepository extends PagingAndSortingRepository<ServiceCenterAddress, Long> {

}
