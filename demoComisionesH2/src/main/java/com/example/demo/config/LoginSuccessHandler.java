package com.example.demo.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.data.Usuario;
import com.example.demo.servicios.IServicioUsuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	Logger log = LoggerFactory.getLogger(LoginSuccessHandler.class);
	
	@Autowired
	IServicioUsuario servicio;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		log.info("onAuthenticationSuccess");
		
		HttpSession session = request.getSession();
		Usuario usuario = null;
		try {
			log.info("NOMBRE DEL PRICIPAL:"+authentication.getName());
			usuario = servicio.getUsuario(authentication.getName());
			log.info("usuario" + usuario.toString());
			session.setAttribute("usuario", usuario);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Redirigir al usuario a una página de inicio personalizada
        response.sendRedirect("/listar"); // Puedes ajustar la URL según tu configuración
	}

}
