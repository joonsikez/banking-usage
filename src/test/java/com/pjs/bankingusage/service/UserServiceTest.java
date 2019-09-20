package com.pjs.bankingusage.service;

import com.pjs.bankingusage.model.dto.UserDto;
import com.pjs.bankingusage.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * UserServiceTest.java version 2019, 09. 20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserServiceTest {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;

	@Before
	public void setup() {
		UserDto userDto = new UserDto();
		userDto.setUserId("abc");
		userDto.setPassword("123");
		userService.signUp(userDto);
	}

	@Test
	public void JWT_토큰_인증() {
		UserService userService = new UserService(userRepository);
		String expectedUserId = "abc";
		String token = ReflectionTestUtils.invokeMethod(userService, "getJwtToken", expectedUserId);

		Jws<Claims> claims = Jwts.parser().setSigningKey("banking_device_usage").parseClaimsJws(token);

		Assert.assertEquals(expectedUserId, claims.getBody().get("userId"));
	}
}
