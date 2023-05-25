package com.onlineservice.service;

import java.io.IOException;

import javax.mail.MessagingException;

import com.onlineservice.user.model.ResetPasswordOTPTable;

public interface EmailService {

	public void sendResetPasswordLink(ResetPasswordOTPTable otpTable) throws MessagingException, IOException;

	public void sendEmail(String email, String password) throws MessagingException, IOException;
	
}
