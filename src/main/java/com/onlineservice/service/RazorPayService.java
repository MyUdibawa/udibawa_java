package com.onlineservice.service;

import org.json.JSONException;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayException;

@Service
public interface RazorPayService {
	String razorPayKeyId = "rzp_test_IM8zdLwFmRpe5k";

	String razorPayKeySecret = "kvCGBsxmBBxPjTTEFgvbjTvs";

	public Order createOrder(Float amount) throws JSONException, RazorpayException;

	public Order getOrder(String orderId) throws RazorpayException;
}
