package com.pjs.bankingusage.service;

import com.pjs.bankingusage.model.dto.DeviceResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * DeviceUsageServiceTest.java version 2019, 09. 18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DeviceUsageServiceTest {

	@Autowired
	private DeviceUsageService deviceUsageService;

	@Test
	public void 전체_디바이스_조회() {
		DeviceResponseDto deviceResponseDto = deviceUsageService.getAllDevices();
		Assert.assertNotNull(deviceResponseDto.getDevices());
	}
}
