package com.seguros.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.seguros.demo.model.Aplicacion;
import com.seguros.demo.model.SessionData;
import com.seguros.demo.model.UserAuth;

@Service	
public class ApiServiceImpl implements ApiService {
	
	Logger logger = LoggerFactory.getLogger(ApiServiceImpl.class);
	
	final static String CMD_ACCEPTED = "1";
	final static String OK = "OK";
	final static String NOT_NULL_RESPONSE = "null";
	final static String SESSION_OBJ = "sessionData";
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
    
    public SessionData authenticate(String usr, String psw) {
    	SessionData sessionData = new SessionData();
    	try {
			String message = String.format("<VALLOGAGEN;%s;%s>", usr, psw);
			String response = sendMessage(message);
			if(response.contains(OK)) {
				String [] values = response.split(";");
				sessionData.setNuu(values[1]);
				sessionData.setNuc(values[2]);
				sessionData.setResultadoAut(Boolean.TRUE);
				return sessionData;
			}    		
		} catch (Exception e) {
			logger.error("Error en authenticate -> ", e);
		}
    	sessionData.setResultadoAut(Boolean.FALSE);
    	return sessionData;
    }
    
    public List<Aplicacion> getAplicaciones(UserAuth user) {
    	List<Aplicacion> aplicaciones = new ArrayList<>(); 
    	try {	    	
	    	String message = String.format("<GETTECXCSG;%s;%s>", user.getNuc(), user.getNuu());    		
			String response = sendMessage(message);
			if(!response.contains(NOT_NULL_RESPONSE)) {
				String apps[] = response.split(",");
				for(String a : apps) {
					List<Aplicacion> aplicacionesDetalle = getAplicacionesDetalle(a, user);
					aplicaciones.add(new Aplicacion(a, null, aplicacionesDetalle));
				}
			}
	    } catch (Exception e) {
			logger.error("Error en getApplications -> ", e);
		}
    	return aplicaciones;
    }
    
    public List<Aplicacion> getAplicacionesDetalle(String aplicacion, UserAuth user) {
    	List<Aplicacion> aplicaciones = new ArrayList<>();
    	try {	    	
	    	String message = String.format("<GETSERXTEC;%s;%s;%s>", user.getNuc(), user.getNuu(), aplicacion);    		
			String response = sendMessage(message);
			if(!response.contains(NOT_NULL_RESPONSE)) {
				String apps[] = response.split(",");
				for(String a : apps) {				
					aplicaciones.add(new Aplicacion(a, "localhost:8081/auth/index.xhtml", null));
				}
			}
	    } catch (Exception e) {
			logger.error("Error en getApplications -> ", e);
		}
    	return aplicaciones;
    }
    
    public String getAlicacionAut(UserAuth user, String aplicacion, String aplicacionDetalle) {
    	try {	    	
	    	String message = String.format("<CFGUSRSSOR;%s;%s;%s;%s;255.255.255.255>", user.getNuc(), user.getNuu(), aplicacion, aplicacionDetalle);    		
			String response = sendMessage(message);
			if(response.contains(OK)) {
				String []values = response.split(",");				
				return values[1];
			}
	    } catch (Exception e) {
			logger.error("Error en getApplications -> ", e);
		}
    	return null;
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
