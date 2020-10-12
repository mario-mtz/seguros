package com.seguros.demo.model;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {
	
	public Role() { }
	public Role(String name) { this.name = name;}
	
	
	private static final long serialVersionUID = 1L;
    
    private String name;
     
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    @Override
    public String getAuthority() {
        return this.name;
    }

}
