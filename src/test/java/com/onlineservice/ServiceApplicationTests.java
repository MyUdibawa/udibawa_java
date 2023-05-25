package com.onlineservice;

import java.io.IOException;

import javax.mail.MessagingException;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.onlineservice.service.EmailService;
import com.onlineservice.service.RazorPayService;
import com.razorpay.Order;
import com.razorpay.RazorpayException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceApplicationTests {

	@Autowired
	private EmailService emailService;

	@Test
	public void test() throws JSONException, RazorpayException {
//		try {
////			emailService.sendEmail("karthikkbdevadiga@gmail.com", "Test Email");
//		} catch (MessagingException | IOException e) {
//			e.printStackTrace();
//		}
	}

}
