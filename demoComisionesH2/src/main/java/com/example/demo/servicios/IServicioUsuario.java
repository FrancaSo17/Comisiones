package com.example.demo.servicios;

import com.example.demo.common.exceptions.ServiceException;
import com.example.demo.data.Users;
import com.example.demo.data.Usuario;

public interface IServicioUsuario {

	public void registerUser(Users user, Usuario usuario) throws ServiceException;

	public Usuario getUsuario(String username) throws ServiceException;

}