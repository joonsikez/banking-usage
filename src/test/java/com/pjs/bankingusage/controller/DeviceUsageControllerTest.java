package com.pjs.bankingusage.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * DeviceUsageControllerTest.java version 2019, 09. 18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceUsageControllerTest {
	private static final String END_POINT = "/api/banking";

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void 전체_디바이스_목록_조회() throws Exception {
		mockMvc.perform(get(END_POINT + "/devices").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(handler().handlerType(DeviceUsageController.class))
				.andExpect(handler().methodName("getAllDevices"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void 년도별_최대이용_디바이스_목록_조회() throws Exception {
		mockMvc.perform(get(END_POINT + "/devices/year").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(handler().handlerType(DeviceUsageController.class))
				.andExpect(handler().methodName("getMaxDevicesGroupByYear"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void 특정년도_최대이용_디바이스_목록_조회() throws Exception {
		mockMvc.perform(get(END_POINT + "/devices/year/2018").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(handler().handlerType(DeviceUsageController.class))
				.andExpect(handler().methodName("getMaxDeviceByYear"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void 디바이스_이용률_최대_년도_조회() throws Exception {
		mockMvc.perform(get(END_POINT + "/year/SMART_PHONE").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(handler().handlerType(DeviceUsageController.class))
				.andExpect(handler().methodName("getMaxYearByDevice"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void 특정디바이스_2019년_이용률_예측_조회() throws Exception {
		mockMvc.perform(get(END_POINT + "/devices/expected/DESKTOP_COMPUTER").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(handler().handlerType(DeviceUsageController.class))
				.andExpect(handler().methodName("getExpectedRate"))
				.andDo(MockMvcResultHandlers.print());
	}

}
