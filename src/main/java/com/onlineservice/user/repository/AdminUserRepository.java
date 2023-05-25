package com.onlineservice.user.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.onlineservice.user.model.AdminUser;

@Repository
public interface AdminUserRepository extends PagingAndSortingRepository<AdminUser, Long>{

}
