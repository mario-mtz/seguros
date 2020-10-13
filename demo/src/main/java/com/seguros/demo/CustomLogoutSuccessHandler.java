package com.seguros.demo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.seguros.demo.model.UserAuth;
import com.seguros.demo.service.ApiService;

public class CustomLogoutSuccessHandler extends 
SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
	
	@Autowired private ApiService apiService;
		
	@Override
    public void onLogoutSuccess(
      HttpServletRequest request, 
      HttpServletResponse response, 
      Authentication authentication) 
      throws IOException, ServletException {
		UserAuth user = (UserAuth) authentication.getPrincipal();
		apiService.closeSession(user);
        //super.onLogoutSuccess(request, response, authentication);
		String basePath = String.format("%s://%s:%s", request.getScheme(), request.getServerName(), request.getServerPort());  
		String otherContext = "Login.xhtml";

		response.sendRedirect(basePath + "/" + otherContext);
    }

}
