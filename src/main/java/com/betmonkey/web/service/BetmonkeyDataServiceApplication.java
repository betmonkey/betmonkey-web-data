package com.betmonkey.web.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;

@EnableDiscoveryClient
@SpringBootApplication
@ImportResource("classpath:springBeans.xml")
public class BetmonkeyDataServiceApplication {

	public static void main(String[] args)
    {

		SpringApplication.run(BetmonkeyDataServiceApplication.class, args);
	}
}
