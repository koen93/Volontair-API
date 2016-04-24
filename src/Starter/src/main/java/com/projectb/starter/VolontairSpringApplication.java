package com.projectb.starter;

import org.hibernate.engine.internal.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import java.util.Arrays;
import java.util.TimeZone;

@EnableJpaRepositories({"com.projectb.repositories", "com.projectb.endpoint"})
@EntityScan("com.projectb.entities")
@ComponentScan("com.projectb")
@SpringBootApplication
@SuppressWarnings("squid:S1118")
public class VolontairSpringApplication extends RepositoryRestConfigurerAdapter {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
		SpringApplication.run(VolontairSpringApplication.class, args);
	}

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.setRepositoryDetectionStrategy(RepositoryDetectionStrategy.RepositoryDetectionStrategies.ANNOTATED);
	}

	@Bean(name = "multipartResolver")
	public MultipartResolver multipartResolver() {
		StandardServletMultipartResolver standardServletMultipartResolver = new StandardServletMultipartResolver();
		return standardServletMultipartResolver;
	}
}
