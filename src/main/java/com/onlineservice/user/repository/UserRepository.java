package com.onlineservice.user.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.onlineservice.user.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	User findByEmail(String username);
}
