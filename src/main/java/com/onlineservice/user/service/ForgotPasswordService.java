package com.onlineservice.user.service;

import org.springframework.stereotype.Service;

@Service
public interface ForgotPasswordService {
	public String requestForgetPassword(String email);

	public String changePassword(String email, int otp, String password);
}
