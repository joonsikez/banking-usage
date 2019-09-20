package com.pjs.bankingusage.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ApiExceptionAdvice.java version 2019, 09. 18
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

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ApiException.class)
	public ApiExceptionResponse apiExceptionHandler(Exception e) {
		return setExceptionResponse(e.getMessage());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ApiExceptionResponse exceptionHandler(Exception e) {
		return setExceptionResponse(e.getMessage());
	}

	private ApiExceptionResponse setExceptionResponse(String errorMessage) {
		ApiExceptionResponse response = new ApiExceptionResponse();
		response.setError(true);
		response.setMessage(errorMessage);
		return response;
	}
}
