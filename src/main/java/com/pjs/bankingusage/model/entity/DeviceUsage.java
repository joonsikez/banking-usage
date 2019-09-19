package com.pjs.bankingusage.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * DeviceUsage.java version 2019, 09. 17
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@IdClass(DeviceUsageId.class)
public class DeviceUsage {
	@Id
	private int year;
	@Id
	private String deviceId;
	private double rate;
	private String deviceName;

	@Builder
	public DeviceUsage(int year, double rate, String deviceId, String deviceName) {
		this.year = year;
		this.rate = rate;
		this.deviceId = deviceId;
		this.deviceName = deviceName;
	}
}
