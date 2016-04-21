package com.projectb.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@EnableJpaRepositories({"com.projectb.repositories", "com.projectb.endpoint"})
@EntityScan("com.projectb.entities")
@ComponentScan("com.projectb")
@SpringBootApplication
@SuppressWarnings("squid:S1118")
public class VolontairSpringApplication extends RepositoryRestConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(VolontairSpringApplication.class, args);
	}

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.setRepositoryDetectionStrategy(RepositoryDetectionStrategy.RepositoryDetectionStrategies.ANNOTATED);
	}
}
