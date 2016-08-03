package com.betmonkey.web.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:springBeans.xml")
public class BetmonkeyDataServiceApplication {

	public static void main(String[] args)
    {

		SpringApplication.run(BetmonkeyDataServiceApplication.class, args);
	}
}
