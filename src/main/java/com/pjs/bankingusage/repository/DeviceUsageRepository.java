package com.pjs.bankingusage.repository;

import com.pjs.bankingusage.model.entity.DeviceUsage;
import com.pjs.bankingusage.model.entity.DeviceUsageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * DeviceUsageRepository.java version 2019, 09. 17
 */
public interface DeviceUsageRepository extends JpaRepository<DeviceUsage, DeviceUsageId> {
	List<DeviceUsage> findAllByYear(int year);
	List<DeviceUsage> findAllByDeviceId(String deviceId);
	@Query(nativeQuery = true, value = "select * from device_usage d WHERE d.device_id = :deviceId order by d.rate desc limit 1")
	DeviceUsage findOneByDeviceId(@Param("deviceId") String deviceId);
}
