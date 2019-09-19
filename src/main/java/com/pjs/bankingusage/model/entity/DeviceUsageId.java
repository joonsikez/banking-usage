package com.pjs.bankingusage.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * DeviceUsageId.java version 2019, 09. 17
 */
@Data
public class DeviceUsageId implements Serializable {
	private int year;
	private String deviceId;
}
