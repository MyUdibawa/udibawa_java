package com.onlineservice.user.repository;

import com.onlineservice.user.model.CustomerUser;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerUserRepository extends PagingAndSortingRepository<CustomerUser, Long> {

}
