package com.seguros.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.seguros.demo.model.Role;
import com.seguros.demo.service.ApiService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
		
	@Autowired
	private ApiService apiService;
	

	@Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
 
        String usr = authentication.getName();
        String psw = authentication.getCredentials().toString();
        
        if (this.apiService.authenticate(usr, psw)) {
        	List<Role> roles = new ArrayList<Role>();
        	roles.add(new Role("admin"));
            return new UsernamePasswordAuthenticationToken(usr, psw, roles);
        } else {
            return null;
        }
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
