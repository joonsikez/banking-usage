package com.pjs.bankingusage.service;

import com.pjs.bankingusage.common.exception.ApiException;
import com.pjs.bankingusage.common.exception.UnauthorizationException;
import com.pjs.bankingusage.model.dto.UserDto;
import com.pjs.bankingusage.model.entity.User;
import com.pjs.bankingusage.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * UserService.java version 2019, 09. 18
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserService {
	private static final String PASSWORD_ENCRYPT_TYPE = "SHA-256";
	private static final String SECRET_KEY = "banking_device_usage";

	private final UserRepository userRepository;

	@Transactional
	public String signUp(UserDto userDto) {
		if (userRepository.findOneByUserId(userDto.getUserId()) != null) {
			throw new UnauthorizationException("이미 있는 ID 입니다.");
		}
		String token = getJwtToken(userDto.getUserId());
		try {
			userRepository.save(User.builder()
					.userId(userDto.getUserId())
					.password(encryptPassword(userDto.getPassword()))
					.token(token)
					.build());
		} catch (NoSuchAlgorithmException e) {
			log.warn("password encryption fail");
		}

		return token;
	}

	@Transactional(readOnly = true)
	public String signIn(final UserDto userDto) {
		String token = "";
		try {
			User user = userRepository.findOneByUserIdAndPassword(userDto.getUserId(), encryptPassword(userDto.getPassword()));
			if (user == null) {
				log.warn("login fail");
				throw new UnauthorizationException("계정 정보가 없습니다.");
			}
			token = user.getToken();
		} catch (NoSuchAlgorithmException e) {
			log.warn("password encryption fail");
		}

		return token;
	}

	@Transactional
	public String refresh(String token) {
		User user = userRepository.findOneByToken(token);

		if (user == null) {
			log.debug("token : {}", token);
			throw new ApiException("토큰에 해당하는 정보가 없습니다.");
		}

		String refreshToken = getJwtToken(user.getUserId());
		userRepository.save(User.builder()
				.userId(user.getUserId())
				.password(user.getPassword())
				.token(refreshToken)
				.build());

		return refreshToken;
	}

	private String getJwtToken(final String userId) {
		return Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setHeaderParam("regDate", System.currentTimeMillis())
				.claim("userId", userId)
				.claim("scope", "device/admin")
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}

	private String encryptPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(PASSWORD_ENCRYPT_TYPE);
		md.reset();
		return new String(Hex.encodeHex(md.digest(password.getBytes())));
	}

	@Transactional(readOnly = true)
	public String getToken(final String token) {
		User user = userRepository.findOneByToken(token);

		if (user == null) {
			log.warn("There is no token in DB : {}", token);
			return null;
		}

		return user.getToken();
	}

}
