package com.seguros.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seguros.demo.model.Usuario;

public interface UsuarioDao extends JpaRepository<Usuario, Long>{

}
