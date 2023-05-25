package com.onlineservice.user.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.onlineservice.user.model.CustomerAddress;
import com.onlineservice.user.model.CustomerUser;

@Repository
public interface CustomerAddressRepository extends PagingAndSortingRepository<CustomerAddress, Long> {
	Page<CustomerAddress> findByCustomerAndIsDeletedIsFalse(CustomerUser customer, Pageable pageable);

	List<CustomerAddress> findByCustomerAndIsDeletedIsFalse(CustomerUser customer);

	CustomerAddress findByIdAndCustomer(Long id, CustomerUser customer);
}
