package co.uk.anddigital.customer.telecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"co.uk.anddigital.customer.telecom.controller",
		"co.uk.anddigital.customer.telecom.service",
		"co.uk.anddigital.customer.telecom.repository",
		"co.uk.anddigital.customer.telecom.model"})
public class TelecomApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelecomApplication.class, args);
	}

}
