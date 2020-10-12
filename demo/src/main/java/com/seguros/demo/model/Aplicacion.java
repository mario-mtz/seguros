package com.seguros.demo.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Aplicacion {
	
	private String nombre; 
	private String enlace;
	private List<Aplicacion> detailApplication;
}
