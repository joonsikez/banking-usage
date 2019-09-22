package com.pjs.bankingusage;

import com.pjs.bankingusage.common.DeviceInfo;
import com.pjs.bankingusage.model.entity.Device;
import com.pjs.bankingusage.model.entity.DeviceUsage;
import com.pjs.bankingusage.service.DeviceUsageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DeviceUsageCommandLineRunner.java version 2019, 09. 17
 */
@Component
@AllArgsConstructor
@Slf4j
public class DeviceUsageCommandLineRunner implements CommandLineRunner {
	private static final String BANKING_USAGE_CSV_FILE = "default/device_usage.csv";
	private static final String CSV_SEPARATOR = ",";

	private final DeviceUsageService deviceUsageService;

	@Override
	public void run(String... args) {
		ClassPathResource resource = new ClassPathResource(BANKING_USAGE_CSV_FILE);

		try (InputStream is = resource.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			List<String> deviceNames = new ArrayList<>();
			reader.lines().forEach(line -> {
				List<String> usageData = Arrays.asList(line.split(CSV_SEPARATOR));
				if (deviceNames.isEmpty()) {
					saveDevice(usageData, deviceNames);
				} else {
					saveUsage(usageData, deviceNames);
				}
			});
		} catch (IOException e) {
			log.warn("Internet banking usage file read error");
		}
	}

	private void saveDevice(List<String> usageData, List<String> deviceNames) {
		usageData.forEach(str -> {
			deviceNames.add(str);
			if (DeviceInfo.getDeviceId(str) != null) {
				// device 정보 저장
				deviceUsageService.saveDevice(Device.builder()
						.deviceId(DeviceInfo.getDeviceId(str))
						.deviceName(str)
						.build());
			}
		});
	}

	private void saveUsage(List<String> usageData, List<String> deviceNames) {
		int year = -1;
		for (int i = 0; i < usageData.size(); i++) {
			if (year < 0) {
				year = getIntValue(usageData.get(i));
			}

			if (DeviceInfo.getDeviceId(deviceNames.get(i)) != null) {
				deviceUsageService.saveDeviceUsage(DeviceUsage.builder()
						.year(year)
						.rate(getDoubleValue(usageData.get(i)))
						.device(deviceUsageService.getDeviceByDeviceId(DeviceInfo.getDeviceId(deviceNames.get(i))))
						.build());
			}
		}
	}

	private int getIntValue(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	private double getDoubleValue(String str) {
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

}
