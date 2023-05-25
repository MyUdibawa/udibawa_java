package com.onlineservice.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonResponseUtils {

	private static Logger logger = LoggerFactory.getLogger("JsonResponseUtils");

	private JsonResponseUtils() {
	}

	public static String createStatusResponse(String status) {
		JSONObject response = new JSONObject();
		try {
			response.put("status", status);
		} catch (JSONException e) {
			logger.error(e.getMessage());
		}
		return response.toString();
	}

	public static String createURLResponse(String url) {
		JSONObject response = new JSONObject();
		try {
			response.put("url", url);
		} catch (JSONException e) {
			logger.error(e.getMessage());
		}
		return response.toString();
	}

}
