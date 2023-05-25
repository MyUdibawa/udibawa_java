package com.onlineservice.user.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.onlineservice.user.model.ResetPasswordOTPTable;
import com.onlineservice.user.model.User;

@Repository
public interface ResetPasswordOTPTableRepository extends PagingAndSortingRepository<ResetPasswordOTPTable, Long> {
	ResetPasswordOTPTable findByOtp(int otp);

	ResetPasswordOTPTable findByUser(User user);
}
