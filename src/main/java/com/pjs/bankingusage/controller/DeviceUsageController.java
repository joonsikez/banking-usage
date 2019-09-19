package com.pjs.bankingusage.controller;

import com.pjs.bankingusage.model.dto.DeviceResponseDto;
import com.pjs.bankingusage.service.DeviceUsageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DeviceUsageController.java version 2019, 09. 17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/banking")
public class DeviceUsageController {

	private DeviceUsageService deviceUsageService;

	@GetMapping("/devices")
	public DeviceResponseDto getAllDevices() {
		return deviceUsageService.getAllDevices();
	}

	@GetMapping("/devices/year")
	public DeviceResponseDto getMaxDevicesGroupByYear() {
		return deviceUsageService.getMaxDevicesGroupByYear();
	}

	@GetMapping("/devices/year/{year}")
	public DeviceResponseDto getMaxDeviceByYear(@PathVariable final int year) {
		return deviceUsageService.getMaxDeviceByYear(year);
	}

	@GetMapping("/year/{deviceId}")
	public DeviceResponseDto getMaxYearByDevice(@PathVariable final String deviceId) {
		return deviceUsageService.getMaxYearByDevice(deviceId);
	}

	@GetMapping("/devices/expected/{deviceId}")
	public DeviceResponseDto getExpectedRate(@PathVariable final String deviceId) {
		return deviceUsageService.getExpectedRate(deviceId);
	}
}
