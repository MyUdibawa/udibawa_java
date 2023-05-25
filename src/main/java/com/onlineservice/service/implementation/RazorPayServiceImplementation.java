package com.onlineservice.service.implementation;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineservice.service.RazorPayService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class RazorPayServiceImplementation implements RazorPayService {

	private RazorpayClient razorpayClient;

	@Autowired
	public RazorPayServiceImplementation() throws RazorpayException {
		razorpayClient = new RazorpayClient(razorPayKeyId, razorPayKeySecret);
	}

	@Override
	public Order createOrder(Float amount) throws JSONException, RazorpayException {
		JSONObject options = new JSONObject();
		options.put("amount", amount*100);
		options.put("currency", "INR");
		
//		JSONObject customer = new JSONObject();
//		customer.put("name","Karthik" );
//		customer.put("contact", "9964121704");
//		customer.put("email", "karthikkbdevadiga@gmail.com");
//		
////		options.put("prefill", prefill);
////		'prefill': {
////		    'contact': '8888888888',
////		    'email': 'test@razorpay.com'
////		  }
//		options.put("customer", customer);
		Order order = razorpayClient.Orders.create(options);
//		System.out.println(order.toString());
		return order;
	}

	@Override
	public Order getOrder(String orderId) throws RazorpayException {
		Order order = razorpayClient.Orders.fetch(orderId);
		System.out.println(order.toString());
		return order;
	}

}
