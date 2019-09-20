package com.pjs.bankingusage.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * ApiExceptionResponse.java version 2018, 09. 18
 */
@Getter
@Setter
public class ApiExceptionResponse {
	private boolean error;
	private String message;
}
