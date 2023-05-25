package com.onlineservice.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;

import com.onlineservice.exception.ApiException;

public class GeneralUtils {

	private static final String EMAIL_NOT_FOUND = "Email Not Found";
	private static final String INVALID_EMAIL = "Invalid Email";
	private static final String PASSWORD_LENGTH_EXCEPTION = "Password Length Should Be more than 8 and less than 15 Characters";
//	private static final String PASSWORD_USERNAME_EXCEPTION = "Password Should not be same as User Name";
//	private static final String PASSWORD_UPPER_EXCEPTION = "Password should contain atleast one Uppercase Alphabet";
//	private static final String PASSWORD_LOWER_EXCEPTION = "Password should contain atleast one Lowercase alphabet";
//	private static final String PASSWORD_NUMBER_EXCEPTION = "Password should contain atleast one number";
//	private static final String PASSWORD_SPECIAL_CHAR_EXCEPTION = "Password should contain atleast one special character";
	private static final String PHONE_NUMBER_NOT_FOUND = "Phone Number Not Found";
	private static final String INVALID_PHONE_NUMBER = "Invalid Phone Number";

	private static final Random RANDOM = new Random();

	private GeneralUtils() {
	}

	public static int createToken() {
		return 100000 + RANDOM.nextInt(900000);
	}

	public static void checkEmail(String email) {
		if (email == null || email.isEmpty())
			throw new ApiException(HttpStatus.FAILED_DEPENDENCY, EMAIL_NOT_FOUND,
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(emailPattern);
		Matcher matcher = pattern.matcher(email);
		if (!matcher.matches())
			throw new ApiException(HttpStatus.FAILED_DEPENDENCY, INVALID_EMAIL,
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
	}

	public static boolean checkPassword(String userName, String password) {
		if (password == null || password.isEmpty())
			return false;
		boolean valid = true;
		if (password.length() > 15 || password.length() < 6)
			throw new ApiException(HttpStatus.FAILED_DEPENDENCY, PASSWORD_LENGTH_EXCEPTION,
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		/*
		 * if (!userName.isEmpty() && password.indexOf(userName) > -1) throw new
		 * ApiException(HttpStatus.FAILED_DEPENDENCY, PASSWORD_USERNAME_EXCEPTION,
		 * Thread.currentThread().getStackTrace()[1].getClassName(),
		 * Thread.currentThread().getStackTrace()[1].getMethodName(),
		 * Thread.currentThread().getStackTrace()[1].getLineNumber()); String
		 * upperCaseChars = "(.*[A-Z].*)"; if (!password.matches(upperCaseChars)) throw
		 * new ApiException(HttpStatus.FAILED_DEPENDENCY, PASSWORD_UPPER_EXCEPTION,
		 * Thread.currentThread().getStackTrace()[1].getClassName(),
		 * Thread.currentThread().getStackTrace()[1].getMethodName(),
		 * Thread.currentThread().getStackTrace()[1].getLineNumber()); String
		 * lowerCaseChars = "(.*[a-z].*)"; if (!password.matches(lowerCaseChars)) throw
		 * new ApiException(HttpStatus.FAILED_DEPENDENCY, PASSWORD_LOWER_EXCEPTION,
		 * Thread.currentThread().getStackTrace()[1].getClassName(),
		 * Thread.currentThread().getStackTrace()[1].getMethodName(),
		 * Thread.currentThread().getStackTrace()[1].getLineNumber()); String numbers =
		 * "(.*[0-9].*)"; if (!password.matches(numbers)) throw new
		 * ApiException(HttpStatus.FAILED_DEPENDENCY, PASSWORD_NUMBER_EXCEPTION,
		 * Thread.currentThread().getStackTrace()[1].getClassName(),
		 * Thread.currentThread().getStackTrace()[1].getMethodName(),
		 * Thread.currentThread().getStackTrace()[1].getLineNumber()); String
		 * specialChars =
		 * "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)"; if
		 * (!password.matches(specialChars)) throw new
		 * ApiException(HttpStatus.FAILED_DEPENDENCY, PASSWORD_SPECIAL_CHAR_EXCEPTION,
		 * Thread.currentThread().getStackTrace()[1].getClassName(),
		 * Thread.currentThread().getStackTrace()[1].getMethodName(),
		 * Thread.currentThread().getStackTrace()[1].getLineNumber());
		 */
		return valid;
	}

	public static String getFileExtension(String fileName) {
		String extension = "";
		try {
			if (fileName != null)
				extension = fileName.substring(fileName.lastIndexOf('.'));
		} catch (Exception e) {
			extension = "";
		}
		return extension;
	}

	public static void checkPhoneNumber(String phoneNumber) {
		if (phoneNumber == null || phoneNumber.isEmpty())
			throw new ApiException(HttpStatus.FAILED_DEPENDENCY, PHONE_NUMBER_NOT_FOUND,
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
		String regExpression = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[6789]\\d{9}$";
		if (!phoneNumber.matches(regExpression))
			throw new ApiException(HttpStatus.FAILED_DEPENDENCY, INVALID_PHONE_NUMBER,
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
	}
}
