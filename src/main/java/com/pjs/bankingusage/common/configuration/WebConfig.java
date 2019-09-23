package com.pjs.bankingusage.common.configuration;

import com.pjs.bankingusage.common.interceptor.TokenInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * InterceptorConfig.java version 2019, 09. 18
 */
@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final TokenInterceptor tokenInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tokenInterceptor).addPathPatterns("/api/banking/**").excludePathPatterns("/api/banking/user/**");
	}

}
