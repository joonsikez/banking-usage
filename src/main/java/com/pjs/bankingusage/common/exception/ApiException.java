package com.pjs.bankingusage.common.exception;

/**
 * UnauthorizationException.java version 2019, 09. 18
 */
public class ApiException extends RuntimeException {
	public ApiException(String message) {
		super(message);
	}
}
