package com.onlineservice.service.implementation;

import java.io.IOException;
import java.util.Base64;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.onlineservice.service.EmailService;
import com.onlineservice.user.model.ResetPasswordOTPTable;

/*
 * @Service - stereotype for service layer
 */
@Service
public class EmailServiceImplementation implements EmailService {
	private JavaMailSender emailSender;
	private static final String EMAIL_FROM_USERNAME = "Udibawa";

	private static final String MAIL_CONTENT_TYPE = "text/html";
	private static final String FROM_MAIL = "customer.udibawa@gmail.com";

	@Autowired
	public EmailServiceImplementation(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}

	@Override
	public void sendResetPasswordLink(ResetPasswordOTPTable otpTable) throws MessagingException, IOException {
		MimeMessage message = emailSender.createMimeMessage();
		message.setFrom(new InternetAddress(FROM_MAIL, EMAIL_FROM_USERNAME));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(otpTable.getUser().getEmail()));
		message.setSubject("Reset Password");
		String baseURL = "http://admin.d2hservice.com/change-password.html";
		String data = otpTable.getOtp() + ":" + otpTable.getUser().getEmail() + ":"
				+ otpTable.getExpiryDate().getTime();
		String content = baseURL + "?data=" + Base64.getEncoder().encodeToString(data.getBytes());
		message.setContent(content, MAIL_CONTENT_TYPE);
		emailSender.send(message);
	}

	@Override
	public void sendEmail(String email, String password) throws MessagingException, IOException {
		MimeMessage message = emailSender.createMimeMessage();
		message.setFrom(new InternetAddress(FROM_MAIL, EMAIL_FROM_USERNAME));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
		message.setSubject("Engineer Password");
		message.setContent(password, MAIL_CONTENT_TYPE);
		emailSender.send(message);
	}

}
