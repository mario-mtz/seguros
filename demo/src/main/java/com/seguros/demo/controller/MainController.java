package com.seguros.demo.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
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
	public void init() {		  
		UserAuth user = (UserAuth)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		aplicaciones = apiService.getAplicaciones(user);
	} 
	
	public void refirectAplicacion( ) {
		UserAuth user = (UserAuth)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String aplicacion = ec.getRequestParameterMap().get("aplicacion");
		String aplicacionDetalle = ec.getRequestParameterMap().get("aplicacionDetalle");
		String accesCode = apiService.getAlicacionAut(user, aplicacion, aplicacionDetalle);
		PrimeFaces.current().executeScript("window.open('http://localhost:8081/auth/index.xhtml?ac=" + accesCode + "'), '_newtab'");
	} 
	
}
