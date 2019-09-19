package com.pjs.bankingusage.common;

import lombok.Getter;

import java.util.stream.Stream;

/**
 * DeviceInfo.java version 2019, 09. 17
 * <p>
 * Copyright 2019 Tmon Corp. All rights Reserved.
 * Tmon PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
@Getter
public enum DeviceInfo {
	SMART_PHONE("SMART_PHONE", "스마트폰"),
	DESKTOP_COMPUTER("DESKTOP_COMPUTER", "데스크탑 컴퓨터"),
	NOTEBOOK("NOTEBOOK", "노트북 컴퓨터"),
	OTHER("OTHER", "기타"),
	SMART_PAD("SMART_PAD", "스마트패드");

	private String id;
	private String name;

	DeviceInfo(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public static String getDeviceId(String name) {
		return Stream.of(DeviceInfo.values())
				.filter(v -> v.getName().equals(name))
				.map(DeviceInfo::getId)
				.findFirst().orElse(null);
	}
}
