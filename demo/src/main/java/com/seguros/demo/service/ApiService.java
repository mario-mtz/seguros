package com.seguros.demo.service;

import java.util.List;

import com.seguros.demo.model.Aplicacion;
import com.seguros.demo.model.SessionData;
import com.seguros.demo.model.UserAuth;

public interface ApiService {
	SessionData authenticate(String usr, String psw);	
    List<Aplicacion> getAplicaciones(UserAuth user); 
    String getAlicacionAut(UserAuth user, String aplicacion, String aplicacionDetalle);
	Boolean closeSession(UserAuth user);
}
