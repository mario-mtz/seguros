package com.seguros.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.seguros.demo.model.SessionData;
import com.seguros.demo.model.UserAuth;

@Service	
public class ApiServiceImpl implements ApiService {
	
	Logger logger = LoggerFactory.getLogger(ApiServiceImpl.class);
	
	final static String CMD_ACCEPTED = "1";
	final static String OK = "OK";
	final static String NOT_NULL_RESPONSE = "null";
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
    
    public SessionData authenticate(String accesCode) {
    	SessionData session = new SessionData();
    	try {
			String message = String.format("<GETCFGACTR;%s>", accesCode);
			String response = sendMessage(message);
			if(!StringUtils.isEmpty(response) && !response.contains(NOT_NULL_RESPONSE)) {				
				String [] values = response.split(":");
				session.setAplicacion(values[0]);
				session.setUsuario(values[1]);
				session.setPassword(values[2]);
				session.setAplicacionDetalle(values[5]);
				session.setNuc(values[6]);
				session.setNuu(values[7]);
				
				session.setResponse(Boolean.TRUE);
				return session;
			}    		
		} catch (Exception e) {
			logger.error("Error en authenticate -> ", e);
		}
    	session.setResponse(Boolean.FALSE);
    	return session;
    }        
    
    public Boolean executeCommand(String command, UserAuth user) {
    	try {	    	
	    	String message = String.format("<VALCOMXEXP;%s;%s;False;False;%s;%s>", user.getNuc(), user.getNuu(), command, user.getAplicacion());    		
			String response = sendMessage(message);
			String []values = response.split(",");
			if(values[3].equals(CMD_ACCEPTED)) {
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
