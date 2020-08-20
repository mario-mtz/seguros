package com.seguros.demo;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JSFConfig {
	
	@Bean
	public static CustomScopeConfigurer viewScope() {
	       CustomScopeConfigurer configurer = new CustomScopeConfigurer();
	       configurer.addScope("view",  new ViewScope());
	       return configurer;
	}

    
}
