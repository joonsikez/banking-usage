package com.pjs.bankingusage.common.interceptor;

import com.pjs.bankingusage.common.exception.UnauthorizationException;
import com.pjs.bankingusage.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
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
	private static final String HTTP_AUTHORIZATION_HEADER = "Authorization";
	private static final String TOKEN_HEADER_VALUE = "Bearer";
	private static final String TOKEN_SCOPE = "device/admin";

	private final UserService userService;

	/**
	 * 토큰 검증 인터셉터 preHandle
	 *
	 * @param request httpRequest
	 * @param response httpResponse
	 * @param handler handler
	 * @return 검증 여부
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		log.debug("interceptor start");
		String token = request.getHeader(HTTP_AUTHORIZATION_HEADER);

		if (token == null) {
			// token이 없을 경우 임시 return true
			return true;
		}
		String tokenValue = token.substring(TOKEN_HEADER_VALUE.length()).trim();
		if (tokenValue.equals(userService.getToken(tokenValue))) {
			return validateToken(tokenValue);
		} else {
			throw new UnauthorizationException("인증 권한 확인");
		}
	}

	/**
	 * JWT 토큰 값 검증
	 * claim의 scope이 device/admin 인지 확인
	 *
	 * @param token JWT 토큰
	 * @return 검증 여부
	 */
	private boolean validateToken(final String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey("banking_device_usage").parseClaimsJws(token);

			if (claims == null || claims.getBody() == null || claims.getBody().get("scope") == null) {
				return false;
			}

			return TOKEN_SCOPE.equals(claims.getBody().get("scope"));
		} catch (SignatureException e) {
			log.warn("서명 인증에 실패하였습니다.");
			return false;
		}
	}
}
