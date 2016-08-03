package com.betmonkey.web.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:springBeans.xml")
public class BetfairDataServiceApplication {

	public static void main(String[] args)
    {

		SpringApplication.run(BetfairDataServiceApplication.class, args);
	}
}
