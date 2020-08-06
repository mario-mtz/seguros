package com.seguros.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.seguros.demo.model.ApplicationData;
import com.seguros.demo.model.SessionData;

@Service	
public class ApiServiceImpl implements ApiService {
	
	Logger logger = LoggerFactory.getLogger(ApiServiceImpl.class);
	
	final static String CMD_ACCEPTED = "2";
	final static String OK = "OK";
	@Value("${conf.socket.server}")
	private String server;
	@Value("${conf.socket.port}")
	private String port;
	@Value("${conf.socket.charset}")
	private String charsetProp;
	
	private Charset charset = Charset.forName("ASCII");
	
	private Socket clientSocket;
    private OutputStream out;
    private BufferedReader in;
    
    private SessionData session = new SessionData();
    
    public Boolean authenticate(String usr, String psw) {
    	try {
			String message = String.format("<VALLOGAGEN;%s;%s>", usr, psw);
			String response = sendMessage(message);
			if(response.contains(OK)) {
				String [] values = response.split(";");
				session.setNuu(values[1]);
				session.setNuc(values[2]);
				return Boolean.TRUE;
			}    		
		} catch (Exception e) {
			logger.error("Error en authenticate -> ", e);
		}
    	return Boolean.FALSE;
    } 
    
    public List<String> getApplications() {
    	try {	    	
	    	String message = String.format("<CFGUSRXSSO;%s;%s;TELRC;Servidor Finanzas Telnet;192.168.1.10>", session.getNuc(), session.getNuu());    		
			String response = sendMessage(message);
			if(response.contains(OK)) {
				Arrays.asList("TELRC");
			}
	    } catch (Exception e) {
			logger.error("Error en getApplications -> ", e);
		}
    	return new ArrayList<String>();
    }
    
    public Boolean getApplicationsCredentials() {
    	try {	    	
	    	String message = String.format("<GETCFGACTU;%s;%s;TELRC>", session.getNuc(), session.getNuu());    		
			String response = sendMessage(message);
			if(response.contains("TELRC")) {
				String []values = response.split(":");
				ApplicationData ad = new ApplicationData();
				ad.setAppName(values[0]);
				ad.setAppName(values[1]);
				ad.setAppName(values[2]);
				session.getAppData().add(ad);
				return Boolean.TRUE;
			}
	    } catch (Exception e) {
			logger.error("Error en getApplications -> ", e);
		}
    	return Boolean.FALSE;
    }
    
    public Boolean executeCommand() {
    	try {	    	
	    	String message = String.format("<VALCOMXEXP;126;1387;False;False;dir;TELRC>");    		
			String response = sendMessage(message);
			String []values = response.split(":");
			if(values[3].equals(CMD_ACCEPTED)) {
				return Boolean.TRUE;
			}
	    } catch (Exception e) {
			logger.error("Error en getApplications -> ", e);
		}
    	return Boolean.FALSE;
    }        
        
    public Boolean closeSession() {
    	try {	    	
	    	String message = String.format("<DELALLSESS;126;1387>");    		
			String response = sendMessage(message);
			if(response.contains(OK)) {
				return Boolean.TRUE;
			}
	    } catch (Exception e) {
			logger.error("Error en getApplications -> ", e);
		}
    	return Boolean.FALSE;
    }       
	
	private void startConnection() throws IOException {
        clientSocket = new Socket(server, Integer.parseInt(port));
        out = clientSocket.getOutputStream();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }
 
	private String sendMessage(String message) throws IOException {
		startConnection();
		logger.info("getApplications -> {}", message);
		byte [] msg = message.getBytes(charset);
        out.write(msg);
        String response = in.readLine();
        logger.info("getApplications Response -> {}", response);        
        stopConnection();
        return response;        
    }
 
	private void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
	
}
