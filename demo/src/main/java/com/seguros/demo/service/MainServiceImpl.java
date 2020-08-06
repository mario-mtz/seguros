package com.seguros.demo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.seguros.demo.model.Aplicacion;

@Service
public class MainServiceImpl implements MainService {

	@Override
	public List<Aplicacion> getAplicaciones() {
		return Arrays.asList( 
				new Aplicacion("TELRC", "localhost:8081/auth/index.xhtml") 
				);
	}

}
