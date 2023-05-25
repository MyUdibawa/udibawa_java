package com.onlineservice.center.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.onlineservice.center.model.OfferedService;
import com.onlineservice.center.model.ServiceCenter;
import com.onlineservice.center.model.ServiceType;

@Repository
public interface OfferedServiceRepository extends PagingAndSortingRepository<OfferedService, Long> {
	Page<OfferedService> findByServiceCenterAndDeletedIsFalse(ServiceCenter serviceCenter, Pageable pageable);

	List<OfferedService> findByServiceCenterAndDeletedIsFalse(ServiceCenter serviceCenter);

	List<OfferedService> findByServiceTypeAndPincodesContaining(ServiceType serviceType, String pincode);

	Page<OfferedService> findByServiceTypeAndPincodesContainingAndDeletedIsFalse(ServiceType serviceType,
			String pincode, Pageable pageable);

	long countByServiceCenterAndDeletedIsFalse(ServiceCenter serviceCenter);
}
