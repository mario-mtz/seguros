package com.seguros.demo;

import java.util.Collections;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.seguros.demo.dao"} )
@RestController
public class DemoApplication {

	public static void main(String[] args) {	
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@GetMapping("/test")
	public Map test() {
		return Collections.singletonMap("value", "ok up!");
	}

}
