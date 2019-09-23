package com.pjs.bankingusage;

import com.pjs.bankingusage.service.DataSaveService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * DeviceUsageCommandLineRunner.java version 2019, 09. 17
 */
@Component
@AllArgsConstructor
public class DeviceUsageCommandLineRunner implements CommandLineRunner {
	private final DataSaveService dataSaveService;

	@Override
	public void run(String... args) {
		dataSaveService.saveDeviceUsage();
	}
}
