package com.example.demo.servicios;

import com.example.demo.data.Users;
import com.example.demo.data.Usuario;

public interface IServicioUsuario {

	void registerUser(Users user, Usuario usuario);

	Usuario getUsuario(String username);

}