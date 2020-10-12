package com.seguros.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seguros.demo.model.Role;
import com.seguros.demo.model.SessionData;
import com.seguros.demo.model.UserAuth;
import com.seguros.demo.service.ApiService;

@Controller
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
    
    private final ApiService apiService;
	    
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, ApiService apiService) {
		this.authenticationManager = authenticationManager;
		this.apiService = apiService;
	} 

	@GetMapping("/auth/test")
	public Map<String, String> test() {
		return Collections.singletonMap("res", "ok");
	}
	
	@GetMapping("/auth")
	public void auth(@RequestParam("ac")String accessCode, HttpServletRequest req) {		
    	SessionData sessionData = apiService.authenticate(accessCode);
    	if(sessionData.getResponse()){
    		List<Role> roles = new ArrayList<Role>();
        	roles.add(new Role("admin"));
    		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(buildUserForAuthentication("admin", "admin", roles, sessionData), "admin", roles);
    	    //authToken.setDetails(new WebAuthenticationDetails((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()));
    	    
    	    Authentication authentication = authenticationManager.authenticate(authToken);
    	    
    	    SecurityContextHolder.getContext().setAuthentication(authentication);    	        	        	    
    	}		  
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
