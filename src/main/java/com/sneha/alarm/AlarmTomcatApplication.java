package com.sneha.alarm;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class AlarmTomcatApplication extends SpringBootServletInitializer {
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AlarmTomcatApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AlarmTomcatApplication.class, args);
    }
}

