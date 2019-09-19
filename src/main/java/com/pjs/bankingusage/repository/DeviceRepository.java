package com.pjs.bankingusage.repository;

import com.pjs.bankingusage.model.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DeviceRepository.java version 2019, 09. 17
 */
public interface DeviceRepository extends JpaRepository<Device, String> {
	Device findOneByDeviceId(String deviceId);
}
