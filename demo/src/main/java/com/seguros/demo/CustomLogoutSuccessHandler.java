package com.seguros.demo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.seguros.demo.service.ApiService;

public class CustomLogoutSuccessHandler extends 
SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
	
	@Autowired private ApiService apiService;
	
	@Autowired
	@Override
    public void onLogoutSuccess(
      HttpServletRequest request, 
      HttpServletResponse response, 
      Authentication authentication) 
      throws IOException, ServletException {
		apiService.closeSession();
        super.onLogoutSuccess(request, response, authentication);
    }

}