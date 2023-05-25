package com.onlineservice.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 737882073877639083L;
	private final HttpStatus httpStatus;
	private final String message;
	private final String className;
	private final String methodName;
	private final int lineNumber;

	public ApiException(HttpStatus httpStatus, String message, String className, String methodName, int lineNumber) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
		this.className = className;
		this.methodName = methodName;
		this.lineNumber = lineNumber;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	@Override
	public String toString() {
		return "ApiException [httpStatus=" + httpStatus + ", message=" + message + ", className=" + className
				+ ", methodName=" + methodName + ", lineNumber=" + lineNumber + "]";
	}

}
