package com.stackroute.analyticsservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnalyticsServiceApplication {
	private static final Logger logger = LoggerFactory.getLogger(AnalyticsServiceApplication.class);

	public static void main(String[] args) {
		logger.info("Analytics Service Application Started");
		SpringApplication.run(AnalyticsServiceApplication.class, args);
	}

}
