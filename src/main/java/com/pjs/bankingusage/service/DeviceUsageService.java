package com.pjs.bankingusage.service;

import com.pjs.bankingusage.common.exception.ApiException;
import com.pjs.bankingusage.model.dto.DeviceResponseDto;
import com.pjs.bankingusage.model.dto.DeviceUsageDto;
import com.pjs.bankingusage.model.entity.Device;
import com.pjs.bankingusage.model.entity.DeviceUsage;
import com.pjs.bankingusage.repository.DeviceRepository;
import com.pjs.bankingusage.repository.DeviceUsageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DeviceUsageService.java version 2019, 09. 17
 */
@Service
@AllArgsConstructor
@Slf4j
public class DeviceUsageService {
	private DeviceUsageRepository deviceUsageRepository;
	private DeviceRepository deviceRepository;

	@Cacheable(value = "system", key = "{#root.methodName}")
	@Transactional(readOnly = true)
	public DeviceResponseDto getAllDevices() {
		DeviceResponseDto deviceResponseDto = new DeviceResponseDto();
		List<DeviceUsageDto> deviceUsageDtoList = deviceRepository.findAll()
				.stream()
				.map(DeviceUsageDto::new)
				.collect(Collectors.toList());

		if (!deviceUsageDtoList.isEmpty()) {
			deviceResponseDto.setDevices(deviceUsageDtoList);
		}

		return deviceResponseDto;
	}

	@Cacheable(value = "system", key = "{#root.methodName}")
	@Transactional(readOnly = true)
	public DeviceResponseDto getMaxDevicesGroupByYear() {
		DeviceResponseDto deviceResponseDto = new DeviceResponseDto();
		deviceResponseDto.setDevices(new ArrayList<>());

		List<DeviceUsageDto> deviceUsageDtoList = deviceUsageRepository.findAll()
				.stream()
				.map(DeviceUsageDto::new)
				.collect(Collectors.toList());

		Map<Integer, List<DeviceUsageDto>> group = deviceUsageDtoList
				.stream()
				.collect(Collectors.groupingBy(deviceUsage -> (deviceUsage.getYear())));

		group.forEach((key, groupValues) -> {
			DeviceUsageDto deviceUsageDto = groupValues.stream()
					.sorted(Comparator.comparing(DeviceUsageDto::getRate).reversed())
					.limit(1)
					.findFirst().orElse(new DeviceUsageDto());
			deviceResponseDto.getDevices().add(deviceUsageDto);
		});

		return deviceResponseDto;
	}

	@Cacheable(value = "system", key = "{#root.methodName, #year}")
	@Transactional(readOnly = true)
	public DeviceResponseDto getMaxDeviceByYear(final int year) {
		DeviceResponseDto deviceResponseDto = new DeviceResponseDto();

		DeviceUsageDto deviceUsageDto = deviceUsageRepository.findAllByYear(year)
				.stream()
				.map(DeviceUsageDto::new)
				.sorted(Comparator.comparing(DeviceUsageDto::getRate).reversed())
				.limit(1)
				.findFirst().orElseThrow(() -> new ApiException("해당 년도의 데이터가 없습니다."));

		deviceResponseDto.setResult(deviceUsageDto);

		return deviceResponseDto;
	}

	@Cacheable(value = "system", key = "{#root.methodName, #deviceId}")
	@Transactional(readOnly = true)
	public DeviceResponseDto getMaxYearByDevice(final String deviceId) {
		DeviceResponseDto deviceResponseDto = new DeviceResponseDto();

		DeviceUsage deviceUsage = deviceUsageRepository.findOneByDeviceId(deviceId);
		if (deviceUsage == null) {
			throw new ApiException("해당 디바이스에 대한 데이터가 없습니다.");
		}
		DeviceUsageDto deviceUsageDto = new DeviceUsageDto(deviceUsage);
		deviceResponseDto.setResult(deviceUsageDto);

		return deviceResponseDto;
	}

	@Cacheable(value = "system", key = "{#root.methodName, #deviceId}")
	@Transactional(readOnly = true)
	public DeviceResponseDto getExpectedRate(final String deviceId) {
		DeviceResponseDto deviceResponseDto = new DeviceResponseDto();

		List<DeviceUsageDto> deviceUsageDtoList = deviceUsageRepository.findAllByDevice(getDeviceByDeviceId(deviceId))
				.stream()
				.sorted(Comparator.comparingInt(DeviceUsage::getYear))
				.map(DeviceUsageDto::new)
				.collect(Collectors.toList());

		if (deviceUsageDtoList.isEmpty()) {
			throw new ApiException("해당 디바이스에 대한 데이터가 없습니다.");
		}

		double minusRate = 0;
		for (int i = 1; i < deviceUsageDtoList.size(); i++) {
			minusRate += deviceUsageDtoList.get(i).getRate() - deviceUsageDtoList.get(i-1).getRate();
		}
		double averageMinus = minusRate / (deviceUsageDtoList.size() - 1);
		double result = Math.round((deviceUsageDtoList.get(deviceUsageDtoList.size()-1).getRate() + averageMinus) * 10) / 10.0;

		if (result < 0) result = 0;
		if (result > 100) result = 100;

		deviceResponseDto.setResult(DeviceUsageDto.builder()
				.deviceId(deviceId)
				.rate(result)
				.year(2019)
				.build());

		return deviceResponseDto;
	}

	@Transactional(readOnly = true)
	public Device getDeviceByDeviceId(String deviceId) {
		return deviceRepository.findOneByDeviceId(deviceId);
	}

	@CacheEvict(value = "system", allEntries = true)
	public void cacheEvict() {
	}
}
