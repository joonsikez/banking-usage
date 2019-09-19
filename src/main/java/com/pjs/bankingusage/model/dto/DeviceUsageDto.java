package com.pjs.bankingusage.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pjs.bankingusage.model.entity.Device;
import com.pjs.bankingusage.model.entity.DeviceUsage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DeviceUsageDto.java version 2019, 09. 17
 */
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceUsageDto {
	private String deviceId;
	private String deviceName;
	private Integer year;
	private Double rate;

	public DeviceUsageDto(Device entity) {
		this.deviceId = entity.getDeviceId();
		this.deviceName = entity.getDeviceName();
	}

	public DeviceUsageDto(DeviceUsage entity) {
		this.deviceId = entity.getDevice().getDeviceId();
		this.deviceName = entity.getDevice().getDeviceName();
		this.year = entity.getYear();
		this.rate = entity.getRate();
	}

	@Builder
	public DeviceUsageDto(String deviceId, String deviceName, Integer year, Double rate) {
		this.deviceId = deviceId;
		this.deviceName = deviceName;
		this.year = year;
		this.rate = rate;
	}
}
