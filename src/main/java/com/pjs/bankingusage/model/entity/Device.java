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
public class Device {
	@Id
	private String deviceId;
	private String deviceName;

	@Builder
	public Device(String deviceId, String deviceName) {
		this.deviceId = deviceId;
		this.deviceName = deviceName;
	}
}
