package com.seguros.demo.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.seguros.demo.model.Aplicacion;
import com.seguros.demo.service.ApiService;
import com.seguros.demo.service.MainService;

import lombok.Getter;
import lombok.Setter;

@Controller
@Scope("view")
public class MainController {		
	
	@Getter @Setter List<Aplicacion> aplicaciones;	
	
	private final MainService mainService;
	private final ApiService apiService;
	
	@Autowired
	public MainController(MainService mainService, ApiService apiService) { 
		this.mainService = mainService;
		this.apiService = apiService;
	}
	
	@PostConstruct
	public void init() {
		aplicaciones = mainService.getAplicaciones();
		this.apiService.getApplications();
	} 
	
}
