package com.seguros.demo.service;

import com.seguros.demo.model.SessionData;

public interface ApiService {
	SessionData authenticate(String accesCode);
	Boolean executeCommand(String command);
	
}
