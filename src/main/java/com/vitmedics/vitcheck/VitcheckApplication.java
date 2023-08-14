package com.vitmedics.vitcheck;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableFeignClients

public class VitcheckApplication {

	private static final Logger log = LogManager.getLogger(VitcheckApplication.class);

	/* Init async logging */
	static {
		System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
	}

	public static void main(String[] args) {

		SpringApplication.run(VitcheckApplication.class, args);
	}

}
