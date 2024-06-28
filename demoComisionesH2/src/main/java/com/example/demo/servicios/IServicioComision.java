package com.example.demo.servicios;

import java.util.List;
import java.util.Optional;

import com.example.demo.data.Comision;
import com.example.demo.data.Usuario;

public interface IServicioComision {

	public List<Comision> listComision();

	public List<Comision> getAllComsiones();

	public Optional<Comision> getComisionById(Integer id);

	public Comision saveComision(Comision comision,Usuario usuarioCreador);

	public void deleteComision(Integer id);
	
	public List<Comision> getAllcomisionesSinVerificar(Usuario usuario);
	
	public List<Comision> getAllcomisionesSinValidar(Usuario usuario);
	
	public List<Comision> getAllcomisionesCanceladas();
	
	public List<Comision> getAllcomisionesAprobadas();
	
	public void validarComision(Integer id, Usuario usuario);
	
	public void verificarComision(Integer id, Usuario usuario);
	
	public void noValidarComision(Integer id, Usuario usuario);
	
	public void noVerificarComision(Integer id, Usuario usuario);
	
	public void guardarComentario(Integer id, String motivo, Usuario usuario);
}
