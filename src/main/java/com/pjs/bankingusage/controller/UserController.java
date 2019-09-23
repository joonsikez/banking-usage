package com.pjs.bankingusage.controller;

import com.pjs.bankingusage.common.exception.ApiException;
import com.pjs.bankingusage.model.dto.UserDto;
import com.pjs.bankingusage.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController.java version 2019, 09. 18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/banking/user")
@Slf4j
public class UserController {
	private final UserService userService;

	private static final String HTTP_AUTHORIZATION_HEADER = "Authorization";
	private static final String REFRESH_HEADER_VALUE = "Bearer Token";

	@PostMapping("/signup")
	public String signUp(@RequestBody UserDto userDto) {
		return userService.signUp(userDto);
	}

	@PostMapping("/signin")
	public String signIn(@RequestBody UserDto userDto) {
		return userService.signIn(userDto);
	}

	@PutMapping("/refresh")
	public String refresh(@RequestHeader(HTTP_AUTHORIZATION_HEADER) String token) {
		if (token.startsWith(REFRESH_HEADER_VALUE)) {
			String tokenValue = token.substring(REFRESH_HEADER_VALUE.length()).trim();
			return userService.refresh(tokenValue);
		} else {
			throw new ApiException("REFRESH 조건의 Header 값이 아닙니다.");
		}
	}
}
