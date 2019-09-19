package com.pjs.bankingusage.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * ApiExceptionResponse.java version 2018, 09. 18
 * <p>
 * Copyright 2018 Ticketmonster Corp. All rights Reserved.
 * Ticketmonster PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
@Getter
@Setter
public class ApiExceptionResponse {
	private boolean error;
	private String message;
}
