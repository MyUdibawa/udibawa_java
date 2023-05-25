package com.onlineservice.utils;

import java.security.SecureRandom;
import java.util.Random;

public class IDGenerator {
	private static final String ALL_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	private final Random random;

	private final char[] symbols;

	private final char[] buf;

	public IDGenerator() {
		this.random = new SecureRandom();
		this.symbols = ALL_CHAR.toCharArray();
		this.buf = new char[11];
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++)
			System.out.println(new IDGenerator().nextString());
	}

	public String nextString() {
		for (int idx = 0; idx < buf.length; ++idx)
			buf[idx] = symbols[random.nextInt(symbols.length)];
		return new String(buf);
	}
}
