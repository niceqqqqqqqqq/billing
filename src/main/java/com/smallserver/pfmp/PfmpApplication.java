package com.smallserver.pfmp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = {"classpath:/app.xml"})
public class PfmpApplication extends SpringBootServletInitializer{

	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PfmpApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(PfmpApplication.class, args);
	}
}
