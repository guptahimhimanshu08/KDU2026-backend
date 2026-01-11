package com.kickdrum.prodLib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class ProdLibApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProdLibApplication.class, args);
	}

}
