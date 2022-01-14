package com.springboot.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringbootHelloWorldApplication {



	public static void main(String[] args) {
		SpringApplication.run(SpringbootHelloWorldApplication.class, args);
	}

}
