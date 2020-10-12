package com.seguros.demo.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import com.seguros.demo.model.Aplicacion;
import com.seguros.demo.model.UserAuth;
import com.seguros.demo.service.ApiService;

import lombok.Getter;
import lombok.Setter;

@Controller
@Scope("view")
public class MainController {		
	
	@Getter @Setter List<Aplicacion> aplicaciones;		
	@Autowired private ApiService apiService;
	
	
	public MainController() { }
	
	@PostConstruct
	public void init(Authentication authentication) {
		UserAuth user = (UserAuth)authentication.getPrincipal();
		aplicaciones = apiService.getAplicaciones(user);
	} 
	
}
