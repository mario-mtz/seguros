package com.seguros.demo.service;

import java.util.List;

public interface ApiService {
	Boolean authenticate(String usr, String psw);
	List<String> getApplications();
	Boolean getApplicationsCredentials();
	Boolean executeCommand(String command);
	Boolean closeSession();
}
