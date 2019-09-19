package com.pjs.bankingusage.controller;

import com.pjs.bankingusage.common.exception.ApiException;
import com.pjs.bankingusage.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController.java version 2019, 09. 18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {
	private final UserService userService;

	private static final String HTTP_TOKEN_HEADER = "x-access-token";
	private static final String HTTP_AUTHORIZATION_HEADER = "Authorization";
	private static final String REFRESH_HEADER_VALUE = "Bearer Token";

	@PostMapping("/signup")
	public String signUp(@RequestParam String id, @RequestParam String password) {
		return userService.signUp(id, password);
	}

	@PostMapping("/signin")
	public String signIn(@RequestParam String id, @RequestParam String password) {
		return userService.signIn(id, password);
	}

	@PostMapping("/refresh")
	public String refresh(@RequestHeader(HTTP_AUTHORIZATION_HEADER) String authorization, @RequestHeader(HTTP_TOKEN_HEADER) String token) {
		if (REFRESH_HEADER_VALUE.equals(authorization) && token != null) {
			return userService.refresh(token);
		} else {
			throw new ApiException("REFRESH 조건의 Header Token이 아닙니다.");
		}
	}
}
