package com.example.demo.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccesoDenegadoControlador {
	private final static Logger log = LoggerFactory.getLogger(ListarComisionesController.class);

	@GetMapping("/access-denied")
	public String accessDenied() {
		log.info("[GETaccessDenied]");
		return "accesoDenegado"; // Nombre de la plantilla de Thymeleaf o JSP
	}
}
