/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguros.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.seguros.demo.model.Role;

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
    
    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() {    	 
        userName = SecurityContextHolder.getContext().getAuthentication().getName();                
		List<Role> roles = (List<Role>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        rol = roles.stream().map(Role::getAuthority).collect(Collectors.joining(","));
    }
   
}
