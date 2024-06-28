package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.data.Usuario;


public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>{

	//Usuario findByNombre(String nombre);
	
	@Query("select u from Usuario u where u.username=:username")
	public Usuario findUsuarioByUsername(@Param("username") String username);
}
