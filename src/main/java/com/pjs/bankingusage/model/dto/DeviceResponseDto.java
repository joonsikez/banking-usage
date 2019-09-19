package com.pjs.bankingusage.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DeviceResponseDto.java version 2019, 09. 17
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceResponseDto {
	private List<DeviceUsageDto> devices;
	private DeviceUsageDto result;
}
