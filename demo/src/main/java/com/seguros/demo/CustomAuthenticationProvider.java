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
import com.seguros.demo.model.SessionData;
import com.seguros.demo.model.UserAuth;
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
        
        SessionData sessionData = this.apiService.authenticate(usr, psw); 
        if (sessionData.getResultadoAut()) {
        //if (true) {
        	List<Role> roles = new ArrayList<Role>();
        	roles.add(new Role("admin"));
            return new UsernamePasswordAuthenticationToken(buildUserForAuthentication(usr, psw,roles, sessionData), psw, roles);
        } else {
            return null;
        }
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
    private UserAuth buildUserForAuthentication(String username, String password, 
    		List<Role> authorities,  SessionData sessionData) {
		    boolean enabled = true;
		    boolean accountNonExpired = true;
		    boolean credentialsNonExpired = true;
		    boolean accountNonLocked = true;

		    UserAuth userAuth = new UserAuth(username, password, enabled, accountNonExpired, credentialsNonExpired,
		            accountNonLocked, authorities);
		    userAuth.setNuc(sessionData.getNuc());
		    userAuth.setNuu(sessionData.getNuu());
		  return userAuth;
	}
}
