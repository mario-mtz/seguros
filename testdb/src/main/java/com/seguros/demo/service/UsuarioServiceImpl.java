package com.seguros.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.seguros.demo.dao.UsuarioDao;
import com.seguros.demo.model.Usuario;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
	
	Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);
	
	
	private final UsuarioDao usuarioDao;
	
	@Autowired
	public UsuarioServiceImpl(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	
	@Override
	public void saveUsuario (Usuario usuario) {
		try {
			this.usuarioDao.save(usuario);
		} catch (DataAccessException e) {
			logger.error("Error en UsuarioServiceImpl.saveUsuario --> ", e);
		}
	}
	
	@Override
	public List<Usuario> getUsuarios () {		
		return this.usuarioDao.findAll();		
	}
}
