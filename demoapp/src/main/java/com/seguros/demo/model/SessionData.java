package com.seguros.demo.model;

import lombok.Data;

@Data
public class SessionData {
	private String nuu;
	private String nuc;
	private String usuario;
	private String password;
	private String aplicacion;	
	private String aplicacionDetalle;
	private Boolean response;
}
