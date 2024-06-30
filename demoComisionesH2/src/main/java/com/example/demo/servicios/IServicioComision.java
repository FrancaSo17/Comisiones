package com.example.demo.servicios;

import java.util.List;
import java.util.Optional;

import com.example.demo.common.exceptions.ServiceException;
import com.example.demo.data.Comision;
import com.example.demo.data.Usuario;

public interface IServicioComision {

	public List<Comision> listComision() throws ServiceException;

	public List<Comision> getAllComsiones() throws ServiceException;

	public Optional<Comision> getComisionById(Integer id) throws ServiceException;

	public Comision saveComision(Comision comision,Usuario usuarioCreador) throws ServiceException;

	public void deleteComision(Integer id) throws ServiceException;
	
	public List<Comision> getAllcomisionesSinVerificar(Usuario usuario) throws ServiceException;
	
	public List<Comision> getAllcomisionesSinValidar(Usuario usuario) throws ServiceException;
	
	public List<Comision> getAllcomisionesCanceladas() throws ServiceException;
	
	public List<Comision> getAllcomisionesAprobadas() throws ServiceException;
	
	public void validarComision(Integer id, Usuario usuario) throws ServiceException;
	
	public void verificarComision(Integer id, Usuario usuario) throws ServiceException;
	
	public void noValidarComision(Integer id, Usuario usuario) throws ServiceException;
	
	public void noVerificarComision(Integer id, Usuario usuario) throws ServiceException;
	
	public void guardarComentario(Integer id, String motivo, Usuario usuario) throws ServiceException;
}
