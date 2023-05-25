package com.onlineservice.user.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.onlineservice.center.model.ServiceCenter;
import com.onlineservice.user.model.ServiceEngineerUser;

@Repository
public interface ServiceEngineerUserRepository extends PagingAndSortingRepository<ServiceEngineerUser, Long> {
	Page<ServiceEngineerUser> findByServiceCenter(ServiceCenter serviceCenter, Pageable pageable);

	List<ServiceEngineerUser> findByServiceCenterAndIsDeletedIsFalse(ServiceCenter serviceCenter);

	long countByServiceCenterAndIsDeletedIsFalse(ServiceCenter serviceCenter);
}
