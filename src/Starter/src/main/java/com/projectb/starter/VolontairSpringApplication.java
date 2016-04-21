package com.projectb.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration(exclude = {
		org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class})
@EnableJpaRepositories("com.projectb.repositories")
@EntityScan("com.projectb.entities")
@ComponentScan("com.projectb")
@SpringBootApplication
@SuppressWarnings("squid:S1118")
public class VolontairSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(VolontairSpringApplication.class, args);
	}
}
