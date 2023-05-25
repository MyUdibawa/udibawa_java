package com.onlineservice.exception;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;

public class ApiExceptionResource extends ResourceSupport {
	private HttpStatus httpStatus;
	private String message;

	public ApiExceptionResource(ApiException apiException) {
		this.httpStatus = apiException.getHttpStatus();
		this.message = apiException.getMessage();

	}

	public int getStatusCode() {
		return httpStatus.value();
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

}
