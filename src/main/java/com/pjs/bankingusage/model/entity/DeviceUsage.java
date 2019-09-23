package com.pjs.bankingusage.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * DeviceUsage.java version 2019, 09. 17
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class DeviceUsage {
	@Id
	@GeneratedValue
	private Long id;
	private int year;
	private double rate;
	@OneToOne
	@JoinColumn(name = "deviceId")
	private Device device;

	@Builder
	public DeviceUsage(int year, double rate, Device device, Long id) {
		this.year = year;
		this.rate = rate;
		this.device = device;
		this.id = id;
	}
}
