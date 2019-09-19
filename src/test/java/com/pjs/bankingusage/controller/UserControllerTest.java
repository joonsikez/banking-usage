package com.pjs.bankingusage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjs.bankingusage.model.dto.UserDto;
import com.pjs.bankingusage.service.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserControllerTest.java version 2019, 09. 18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
	private static final String END_POINT = "/api/user";

	@Autowired
	private UserService userService;
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void 회원_가입() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setUserId("junsik");
		userDto.setPassword("123");
		mockMvc.perform(post(END_POINT + "/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectToJsonString(userDto))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(handler().handlerType(UserController.class))
				.andExpect(handler().methodName("signUp"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void 회원_로그인() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setUserId("junsik");
		userDto.setPassword("123");
		userService.signUp(userDto);

		mockMvc.perform(get(END_POINT + "/signin")
				.param("id", "junsik")
				.param("password", "123")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(handler().handlerType(UserController.class))
				.andExpect(handler().methodName("signIn"))
				.andDo(MockMvcResultHandlers.print());
	}

	private String objectToJsonString(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
