package com.justclick.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.justclick.authentication.*")
public class JustclickAuthenticationMultitenantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JustclickAuthenticationMultitenantServiceApplication.class, args);
	}

}
