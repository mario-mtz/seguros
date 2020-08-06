/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguros.demo.controller;

import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

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
    public void init() {
    	SecurityContextHolder.getContext().getAuthentication().getPrincipal();   
        userName = SecurityContextHolder.getContext().getAuthentication().getName();        
        rol = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(Object::toString).collect(Collectors.joining(","));
    }
   
}
