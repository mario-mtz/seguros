package com.seguros.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seguros.demo.model.Usuario;
import com.seguros.demo.service.UsuarioService;

@RestController
public class TestController {
	
	@Autowired UsuarioService usuarioService;
	
	@GetMapping("/users")
	public List<Usuario> getUsers() { return usuarioService.getUsuarios();}
}
