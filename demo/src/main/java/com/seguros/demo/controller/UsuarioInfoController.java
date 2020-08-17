package com.seguros.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.seguros.demo.model.Usuario;
import com.seguros.demo.service.UsuarioService;

import lombok.Getter;
import lombok.Setter;

@Controller
@Scope("view")
public class UsuarioInfoController {
	
	Logger logger = LoggerFactory.getLogger(UsuarioInfoController.class);
	
	@Autowired private UsuarioService usuarioService;
	@Autowired PasswordEncoder passwordEncoder;
	@Getter @Setter private Usuario usuario;
	@Getter @Setter private Boolean registered = Boolean.FALSE;
	@Getter @Setter private Boolean isNew = Boolean.TRUE;
	private String oldPasword = "";
	
	public UsuarioInfoController() {
		usuario = new Usuario();
	}
	
	@PostConstruct
	public void init() {
		String action = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("action");
		if(action.equals("update")) {			
			List<Usuario> list = this.usuarioService.getUsuarios();
			this.usuario = this.usuarioService.findByUsername("Test01");
			this.isNew = Boolean.FALSE;
			if(this.usuario == null) {
				cancelar();
			} else {
				this.oldPasword = this.usuario.getPassword();
			}
						
		}
	}
	
	public void saveUsuario() {
		if(!isNew && StringUtils.isEmpty(this.usuario.getPassword())) {
			this.usuario.setPassword(oldPasword);
		}
		if((!isNew && !StringUtils.isEmpty(this.usuario.getPassword())) || this.usuario.getId() == null) {
			this.usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		}
		this.usuarioService.saveUsuario(usuario);
		this.registered = Boolean.TRUE;

	}	
	
	public void cancelar() {
		try {
			String url = isNew ? "/Login.xhtml" : "/private/index.xhtml";
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);	
		} catch (Exception e) {
			logger.error("Error en UsuarioInfoController -> cancelar ", e);
		}
		
	}

}
