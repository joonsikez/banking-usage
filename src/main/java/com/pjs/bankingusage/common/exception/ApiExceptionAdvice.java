package com.pjs.bankingusage.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ApiExceptionAdvice.java version 2019, 09. 18
 * <p>
 * Copyright 2019 Tmon Corp. All rights Reserved.
 * Tmon PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
@RestControllerAdvice
public class ApiExceptionAdvice {
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ApiExceptionResponse apiNumberFormatExceptionHandler(Exception e) {
		return setExceptionResponse(e.getMessage());
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizationException.class)
	public ApiExceptionResponse apiUnauthorizationExceptionHandler(Exception e) {
		return setExceptionResponse(e.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ApiExceptionResponse apiRequestMethodExceptionHandler(Exception e) {
		return setExceptionResponse(e.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingRequestHeaderException.class)
	public ApiExceptionResponse apiMissingRequestHeaderExceptionHandler(Exception e) {
		return setExceptionResponse(e.getMessage());
	}

	private ApiExceptionResponse setExceptionResponse(String errorMessage) {
		ApiExceptionResponse response = new ApiExceptionResponse();
		response.setError(true);
		response.setMessage(errorMessage);
		return response;
	}
}
