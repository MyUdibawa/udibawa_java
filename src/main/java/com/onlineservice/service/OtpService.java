package com.onlineservice.service;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.stereotype.Service;

import com.onlineservice.request.model.ServiceRequest;

@Service
public interface OtpService {
	String sendOtp(ServiceRequest request) throws IOException, JSONException;
}
