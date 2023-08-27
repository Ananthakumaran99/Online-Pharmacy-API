package com.jsp.onlinepharmacy;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlinepharmacyApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlinepharmacyApplication.class, args);
	}
	@Bean //it is a third party class so in order to access it we need to use annotate with @bean
	public ModelMapper getModelMapper() {
		return  new ModelMapper();
	}
}
