package com.example.demo.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.data.Authorities;
import com.example.demo.data.Users;
import com.example.demo.data.Usuario;
import com.example.demo.repositorio.AuthoritiesRepository;
import com.example.demo.repositorio.UsersRepositorio;
import com.example.demo.repositorio.UsuarioRepositorio;

import jakarta.transaction.Transactional;

@Service
public class UsuarioServicio implements IServicioUsuario{

	Logger log = LoggerFactory.getLogger(ComisionServicio.class);
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private UsersRepositorio usersRepositorio;
	
	@Autowired
	private AuthoritiesRepository authoritiesRepository;
	
	
	
	@Override
	@Transactional
	public void registerUser(Users user, Usuario usuario) {
	log.info("registerUser");
	log.debug("user:", user.getUsername());
	log.debug("usuario:", usuario);
	
	try {
	user.setPassword(passwordEncoder().encode(user.getPassword()));
	usersRepositorio.save(user);
	
	// Asignar rol (autoridad) al usuario
				Authorities authorities = new Authorities();
				authorities.setUsername(user.getUsername());
				authorities.setAuthority("ROLE_USER");
				
				// Guardar la autoridad en la base de datos
				authoritiesRepository.save(authorities);

				log.debug("authorities:", authorities);

				// datosUsuario.setUsername(user.getUsername());

				usuarioRepositorio.save(usuario);

				log.info("Usuario {} registrado con rol ROLE_USER", user.getUsername()+ " "+ user.getPassword()+"  nivelUsuario"+ usuario.getNivelUsuario());
			} catch (Exception e) {
				log.error("Error registro", e);
			}
	}
	
	@Override
	public Usuario getUsuario(String username) {
		
		return usuarioRepositorio.findUsuarioByUsername(username);
	}

	
	private PasswordEncoder passwordEncoder() { return new
			  BCryptPasswordEncoder(); }

	public Usuario authenticate(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	

}
