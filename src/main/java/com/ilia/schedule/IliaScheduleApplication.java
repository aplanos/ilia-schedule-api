package com.ilia.schedule;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class IliaScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(IliaScheduleApplication.class, args);
	}

}
