package com.onlineservice.center.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.onlineservice.center.model.ServiceCenter;
import com.onlineservice.user.model.ServiceCenterAdminUser;

@Repository
public interface ServiceCenterRepository extends PagingAndSortingRepository<ServiceCenter, Long> {
	Page<ServiceCenter> findByIsVerified(Boolean isVerified, Pageable pageable);

	Page<ServiceCenter> findByIsDeletedIsFalse(Pageable pageable);

	List<ServiceCenter> findByIsDeletedIsFalse();

	Page<ServiceCenter> findByIsVerifiedAndIsDeletedIsFalse(Boolean isVerified, Pageable pageable);

	List<ServiceCenter> findByIsVerifiedAndIsDeletedIsFalse(Boolean isVerified);

	ServiceCenter findByIdAndIsDeletedIsFalse(Long id);

	ServiceCenter findByAdmin(ServiceCenterAdminUser admin);
}
