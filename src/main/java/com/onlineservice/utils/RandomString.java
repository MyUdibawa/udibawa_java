package com.onlineservice.utils;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;

public class RandomString {

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++)
			System.out.println(new RandomString(10).nextString());
	}

	public String nextString() {
		for (int idx = 0; idx < buf.length; ++idx)
			buf[idx] = symbols[random.nextInt(symbols.length)];
		return new String(buf);
	}

	private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";

	private static final String SPECIAL_CHAR = "~!@#$%^&*()-_=+{}|;:<>/?]";

	private static final String DIGITS = "0123456789";

	private static final String ALPHANUMBERIC = UPPER + LOWER + DIGITS + SPECIAL_CHAR;

	private final Random random;

	private final char[] symbols;

	private final char[] buf;

	public RandomString(int length, Random random, String symbols) {
		if (length < 1)
			throw new IllegalArgumentException();
		if (symbols.length() < 2)
			throw new IllegalArgumentException();
		this.random = Objects.requireNonNull(random);
		this.symbols = symbols.toCharArray();
		this.buf = new char[length];
	}

	public RandomString(int length, Random random) {
		this(length, random, ALPHANUMBERIC);
	}

	public RandomString(int length) {
		this(length, new SecureRandom());
	}

	public RandomString() {
		this(21);
	}

}