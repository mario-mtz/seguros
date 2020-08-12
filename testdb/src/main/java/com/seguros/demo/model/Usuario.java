package com.seguros.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Entity
@Data
public class Usuario {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_id_seq")
    @SequenceGenerator(name="usuario_id_seq", sequenceName = "USUARIO_ID_SEQ", allocationSize = 1)
    private Long id;
	
	private String username;
	private String password;
	private String nombre;
	private String direccion;
	private String email;
	
}
