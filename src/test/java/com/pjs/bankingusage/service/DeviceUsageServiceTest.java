package com.pjs.bankingusage.service;

import com.pjs.bankingusage.model.dto.DeviceResponseDto;
import com.pjs.bankingusage.model.dto.DeviceUsageDto;
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

	@Test
	public void 년도별_최대이용률_디바이스_조회() {
		DeviceResponseDto deviceResponseDto = deviceUsageService.getMaxDevicesGroupByYear();
		Assert.assertNotNull(deviceResponseDto.getDevices());
	}

	@Test
	public void 특정년도_최대이용률_디바이스_조회() {
		Double exptectedValue = 90.5;
		DeviceResponseDto deviceResponseDto = deviceUsageService.getMaxDeviceByYear(2018);
		Assert.assertEquals(exptectedValue, deviceResponseDto.getResult().getRate());
	}

	@Test
	public void 특정디바이스_최대이용률_년도_조회() {
		Integer exptectedValue = 2017;
		DeviceResponseDto deviceResponseDto = deviceUsageService.getMaxYearByDevice("SMART_PHONE");
		Assert.assertEquals(exptectedValue, deviceResponseDto.getResult().getYear());
	}
}
