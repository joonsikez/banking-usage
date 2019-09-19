package com.pjs.bankingusage.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Device.java version 2019, 09. 17
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User {
	@Id
	private String userId;
	private String password;
	private String token;

	@Builder
	public User(String userId, String password, String token) {
		this.userId = userId;
		this.password = password;
		this.token = token;
	}
}
