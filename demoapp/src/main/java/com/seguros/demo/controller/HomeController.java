/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguros.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
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

import com.seguros.demo.model.Role;
import com.seguros.demo.model.SessionData;
import com.seguros.demo.model.UserAuth;
import com.seguros.demo.service.ApiService;

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
    public void init() throws IOException {    	    	    	    	
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String accesCode = ec.getRequestParameterMap().get("ac");
    	SessionData sessionData = apiService.authenticate(accesCode);
    	if(sessionData.getResponse()){
    		List<Role> roles = new ArrayList<Role>();
        	roles.add(new Role("admin"));
    		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(buildUserForAuthentication("admin", "admin", roles, sessionData), "admin", roles);
    	    //authToken.setDetails(new WebAuthenticationDetails((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()));
    	    
    	    Authentication authentication = authenticationManager.authenticate(authToken);    	    
    	    SecurityContextHolder.getContext().setAuthentication(authentication);    	    
        	    	
//            userName = sessionData.getUsuario();        
//            rol = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(Object::toString).collect(Collectors.joining(","));          
    	}		        
    }
    
    public void forward()  throws IOException {
    	 String uri = "/private/index.xhtml";
         FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
    }
    
    private UserAuth buildUserForAuthentication(String username, String password, 
    		List<Role> authorities,  SessionData sessionData) {
		    boolean enabled = true;
		    boolean accountNonExpired = true;
		    boolean credentialsNonExpired = true;
		    boolean accountNonLocked = true;

		    UserAuth userAuth = new UserAuth(username, password, enabled, accountNonExpired, credentialsNonExpired,
		            accountNonLocked, authorities);		    
		    userAuth.setUsuario(sessionData.getUsuario());
		    userAuth.setPass(sessionData.getPassword());
		    userAuth.setNuc(sessionData.getNuc());
		    userAuth.setNuu(sessionData.getNuu());
		    userAuth.setAplicacion(sessionData.getAplicacion());
		    userAuth.setAplicacionDetalle(sessionData.getAplicacionDetalle());		    
		  return userAuth;
	}
   
}
