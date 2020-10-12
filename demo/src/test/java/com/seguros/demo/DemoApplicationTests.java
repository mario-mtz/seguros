package com.seguros.demo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.seguros.demo.model.Usuario;
import com.seguros.demo.service.UsuarioService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DemoApplication.class})
@Transactional
class DemoApplicationTests {
	
	Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);

	@Test
	@Autowired
	void contextLoads(UsuarioService usuarioService) {
		Usuario usuario = new Usuario();
		usuario.setUsername("admin");
		usuario.setPassword("password");
		usuario.setNombre("Nombre de usuario");
		usuario.setDireccion("Mi direccion");
		usuario.setEmail("email@gmail.com");
		usuarioService.saveUsuario(usuario);
		logger.info("usuario guardado ok!");
		
		List<Usuario> usuarios = usuarioService.getUsuarios();
		logger.info("usuarios size {}", usuarios.size());
		
		usuarios.stream().forEach( u -> logger.info(u.getNombre()) );
	}

}
