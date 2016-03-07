package com.projectb.starter;

import com.projectb.endpoint.UserService;
import com.projectb.entities.User;
import com.projectb.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;

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
