package com.pjs.bankingusage.common.interceptor;

import com.pjs.bankingusage.common.exception.UnauthorizationException;
import com.pjs.bankingusage.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TokenInterceptor.java version 2019, 09. 18
 */
@AllArgsConstructor
@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
	private static final String HTTP_TOKEN_HEADER = "x-access-token";

	private final UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		log.debug("interceptor start");
		String token = request.getHeader(HTTP_TOKEN_HEADER);

		if (token == null || token.equals(userService.getToken(token))) {
			// token이 없을 경우 임시 return true
			return true;
		} else {
			throw new UnauthorizationException("인증 권한 확인");
		}
	}
}
