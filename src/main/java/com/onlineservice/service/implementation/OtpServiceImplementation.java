package com.onlineservice.service.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.onlineservice.exception.ApiException;
import com.onlineservice.request.model.ServiceRequest;
import com.onlineservice.service.OtpService;

@Service
public class OtpServiceImplementation implements OtpService {

	private static final String API_KEY = "k25kDooIZke6870qL0FL5A";
	private static final String SENDER_ID = "Udbawa";
	private static final String CHANNEL = "2";
	private static final String DCS = "0";
	private static final String FLASH_SMS = "0";
	private static final String OTP_MESSAGE = "{{otp}} is your One Time Password for Verifying the Service Engineer.\n\nRegards,\nUdibawa Pvt. LTD ";
	private static final String ROUTE = "31";
	private static final String ENTITY_ID = "1201162013272335515";
	private static final String DLT_TEMPLATE_ID = "1207162755763028467";

	public static void main(String[] args) throws IOException, JSONException {
		OtpServiceImplementation a = new OtpServiceImplementation();
		a.sendOtp(null);
	}
	@Override
	public String sendOtp(ServiceRequest request) throws IOException, JSONException {
		String number = request.getCustomer().getPhoneNumber();
		
		String url = "https://www.smsgatewayhub.com/api/mt/SendSMS" + "?APIKey=" + API_KEY + "&senderid=" + SENDER_ID
				+ "&channel=" + CHANNEL + "&DCS=" + DCS + "&flashsms=" + FLASH_SMS + "&number=" + number + "&text="
				+ URLEncoder.encode(OTP_MESSAGE.replace("{{otp}}", request.getVerifyOtp() + ""), "UTF-8") + "&route="
				+ ROUTE + "&EntityId=" + ENTITY_ID + "&dlttemplateid=" + DLT_TEMPLATE_ID;

		URL obj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

		int status = conn.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		StringBuilder html = new StringBuilder();
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			html.append(inputLine);
		}
		in.close();
		conn.disconnect();
		JSONObject object = new JSONObject(html.toString());
		if (status == 200) {
			return "OTP Sent Successfully";
		}
		throw new ApiException(HttpStatus.UNAUTHORIZED, "Failed To Send OTP",
				Thread.currentThread().getStackTrace()[1].getClassName(),
				Thread.currentThread().getStackTrace()[1].getMethodName(),
				Thread.currentThread().getStackTrace()[1].getLineNumber());
	}

}
