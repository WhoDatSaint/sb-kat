package com.acme.myapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.katharsis.spring.jpa.SpringTransactionRunner;

@Configuration
public class JpaConfig {

	@Bean
	public SpringTransactionRunner transactionRunner() {
		return new SpringTransactionRunner();
	}
}
