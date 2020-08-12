package com.seguros.demo.service;

import java.util.List;

import com.seguros.demo.model.Usuario;

public interface UsuarioService {
	void saveUsuario (Usuario usuario);
	List<Usuario> getUsuarios ();
	
}
