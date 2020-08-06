package com.seguros.demo.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import lombok.Getter;
import lombok.Setter;

@Controller
@Scope("view")
public class TestController {
		
	@Setter @Getter private String nombre;    		      
	@Setter @Getter private String saludo;
	
	public void hola() {
		saludo = String.format("Hola! %s", nombre);
	}

}
