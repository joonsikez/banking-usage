package com.pjs.bankingusage.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pjs.bankingusage.model.entity.User;
import lombok.Getter;
import lombok.Setter;

/**
 * UserDto.java version 2019, 09. 19
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
	private String userId;
	private String password;
	private String token;

	public User toEntity() {
		return User.builder()
				.userId(userId)
				.password(password)
				.token(token)
				.build();
	}
}
