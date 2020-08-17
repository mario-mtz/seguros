package com.seguros.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity
@Data
@DynamicUpdate
public class Usuario {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_id_seq")
    @SequenceGenerator(name="usuario_id_seq", sequenceName = "USUARIO_ID_SEQ", allocationSize = 1)
    private Long id;
	@NotBlank
	private String username;
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*])(?=\\S+$).{8,}$")
	private String password;
	@NotBlank
	private String nombre;
	@NotBlank
	private String direccion;
	@NotBlank @Email
	private String email;
	
}
