package com.egoncalves.product.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@SpringBootApplication
public class ProductApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApiApplication.class, args);
	}
	
	@Bean
	public Jackson2ObjectMapperBuilder configureObjectMapper() {
	    return new Jackson2ObjectMapperBuilder()
	        .modulesToInstall(Hibernate5Module.class);
	}
}
