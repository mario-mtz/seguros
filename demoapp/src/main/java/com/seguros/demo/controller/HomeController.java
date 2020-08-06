/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguros.demo.controller;

import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;

import com.seguros.demo.service.ApiService;
import com.seguros.demo.service.MainService;

import lombok.Getter;

/**
 *
 * @author mariomartinezaguilar
 */
@Controller
@Scope("view")
public class HomeController {
    
    @Getter private String userName;
    @Getter private String rol;
    
    private final AuthenticationManager authenticationManager;
    
    private final ApiService apiService;
	    
    @Autowired
    public HomeController(AuthenticationManager authenticationManager, ApiService apiService) {
		this.authenticationManager = authenticationManager;
		this.apiService = apiService;
	} 
    
    @PostConstruct
    public void init() {
//    	if(!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
    		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken("admin", "admin");
    	    authToken.setDetails(new WebAuthenticationDetails((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()));
    	    
    	    Authentication authentication = authenticationManager.authenticate(authToken);
    	    
    	    SecurityContextHolder.getContext().setAuthentication(authentication);    	    
    	//}    	
    	SecurityContextHolder.getContext().getAuthentication().getPrincipal();   
        userName = SecurityContextHolder.getContext().getAuthentication().getName();        
        rol = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(Object::toString).collect(Collectors.joining(","));
        this.apiService.getApplicationsCredentials();
    }
    
    public void forward()  throws Exception {
    	 String uri = "/private/index.xhtml";
         FacesContext.getCurrentInstance().getExternalContext().dispatch(uri);
    }
   
}
