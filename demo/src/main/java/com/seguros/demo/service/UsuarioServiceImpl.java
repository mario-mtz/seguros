package com.seguros.demo.service;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.seguros.demo.dao.UsuarioDao;
import com.seguros.demo.model.Usuario;
import com.seguros.demo.util.MessageType;

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
	public Usuario findByUsername(String username) {
		return this.usuarioDao.findByUsername(username); 
	}		
	
	@Override
	public void saveUsuario (Usuario usuario) {
		try {			
			this.usuarioDao.save(usuario);
			sendUIMessage("Datos guardados!", MessageType.MESSAGE_INFO);
		} catch (DataAccessException e) {
			logger.error("Error en UsuarioServiceImpl.saveUsuario --> ", e);
			sendUIMessage("Hubo un error al guardar los datos! Favor de contactar al área de sistemas", MessageType.MESSAGE_ERROR);
		}
	}
	
	@Override
	public List<Usuario> getUsuarios () {		
		return this.usuarioDao.findAll();		
	}
	
	protected void sendUIMessage(String message, int severity){
        FacesMessage messageContext = new FacesMessage(message, "");
        switch(severity){
            case MessageType.MESSAGE_INFO:
                messageContext.setSeverity(FacesMessage.SEVERITY_INFO);
                break;
            case MessageType.MESSAGE_WARN:
                messageContext.setSeverity(FacesMessage.SEVERITY_WARN);
                break;
            case MessageType.MESSAGE_ERROR:
                messageContext.setSeverity(FacesMessage.SEVERITY_ERROR);
                break;    
        }        
        FacesContext.getCurrentInstance().addMessage("", messageContext);
    }
}
