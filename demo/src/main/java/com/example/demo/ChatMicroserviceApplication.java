package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "config")
public class ChatMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatMicroserviceApplication.class, args);
	}

}
