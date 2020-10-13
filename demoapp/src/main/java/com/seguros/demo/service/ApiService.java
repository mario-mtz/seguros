package com.seguros.demo.service;

import com.seguros.demo.model.SessionData;
import com.seguros.demo.model.UserAuth;

public interface ApiService {
	SessionData authenticate(String accesCode);
	Boolean executeCommand(String command, UserAuth user);
	
}
