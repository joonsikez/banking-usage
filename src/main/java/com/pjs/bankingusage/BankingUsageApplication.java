package com.pjs.bankingusage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class BankingUsageApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingUsageApplication.class, args);
	}

}
