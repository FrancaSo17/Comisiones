package com.example.demo.servicios;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.data.Comision;
import com.example.demo.data.Usuario;
import com.example.demo.repositorio.ComisionRepositorio;

@Service
public class ComisionServicio implements IServicioComision {

	private final static Logger log = LoggerFactory.getLogger(ComisionServicio.class);
	@Autowired
	private ComisionRepositorio comisionRepositorio;

	public List<Comision> getAllComsiones() {
		log.info("[getAllComsiones]");
		List<Comision> comisiones = comisionRepositorio.findAll();
		log.debug("Comisiones sin ordenar");
		comisiones.forEach(c -> log.debug(c.toString()));

		List<Comision> comisionesOrdenadas = comisionRepositorio.findAllByOrderByFechaComisionAscPrioridadAsc();
		log.debug("Comisiones ordenadas");
		comisionesOrdenadas.forEach(c -> log.debug(c.toString()));

		Collections.sort(comisionesOrdenadas, Comparator.comparingInt(Comision::getPrioridad));
		return comisionesOrdenadas;
	}

	public List<Comision> getAllcomisionesSinValidar(Usuario usuario) {

		log.info("[getAllComsionesSinValidar]");
		log.info("[Nivel usuario en Servicio:" + usuario.getNivelUsuario());

		List<Comision> comisiones = comisionRepositorio.findAll();

		List<Comision> comisionesSinValidar = comisiones.stream()
				.filter(c -> c.isValidada() == false && c.isCancelada() == false)
				.filter(c -> c.getUsuarioCreador().getNivelUsuario()< usuario.getNivelUsuario() || usuario.getNivelUsuario()==9)
				.collect(Collectors.toList());

		Collections.sort(comisionesSinValidar, Comparator.comparingInt(Comision::getPrioridad));

		return comisionesSinValidar;

	}

	public List<Comision> getAllcomisionesSinVerificar(Usuario usuario) {

		log.info("[getAllComsionesSinVerificar]");

		List<Comision> comisiones = comisionRepositorio.findAll();

		List<Comision> comisionesSinV = comisiones.stream().filter(c -> c.isValidada() && !c.isVerificada())
				.filter(c -> c.getUsuarioValidador().getNivelUsuario()< usuario.getNivelUsuario() || usuario.getNivelUsuario()==9)
				.collect(Collectors.toList());

		Collections.sort(comisionesSinV, Comparator.comparingInt(Comision::getPrioridad));

		return comisionesSinV;

	}

	public List<Comision> getAllcomisionesCanceladas() {

		log.info("[getAllComsionesCanceladas]");

		List<Comision> comisiones = comisionRepositorio.findAll();

		List<Comision> comisionesCanceladas = comisiones.stream().filter(c -> c.isCancelada())
				.collect(Collectors.toList());

		Collections.sort(comisionesCanceladas, Comparator.comparingInt(Comision::getPrioridad));

		return comisionesCanceladas;

	}

	public List<Comision> getAllcomisionesAprobadas() {

		log.info("[getAllComsionesAprobadas]");

		List<Comision> comisiones = comisionRepositorio.findAll();

		List<Comision> comisionesAprobadas = comisiones.stream()
				.filter(c -> c.isCancelada() == false && c.isValidada() && c.isVerificada() && c.isAprobada())
				.collect(Collectors.toList());

		Collections.sort(comisionesAprobadas, Comparator.comparingInt(Comision::getPrioridad));

		return comisionesAprobadas;

	}

	public Optional<Comision> getComisionById(Integer id) {
		log.info("[getComisionById]");
		return comisionRepositorio.findById(id);
	}

	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Importante
	public Comision saveComision(Comision comision, Usuario usuarioCreador) {
	    log.info("[saveComision]");
	    System.out.println("Usuario autenticado: " + usuarioCreador.getUsername());

	   
	    comision.setEstado("pendiente");

	    // Obtén el usuario autenticado actualmente
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    log.info("------------"+ authentication.isAuthenticated());
	  
	   
	        comision.setUsuarioCreador(usuarioCreador);
	  

	    return comisionRepositorio.save(comision);
	}
	
	
	
//	///////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	

	public void deleteComision(Integer id) {
		log.info("[EliminarComision]");
		comisionRepositorio.deleteById(id);
	}

	@Override
	public List<Comision> listComision() {
		log.info("[listComision]");
		// TODO Auto-generated method stub
		return null;
	}


	////
	
	@Override
	public void validarComision(Integer id, Usuario usuario) {
		log.info("[validarComision]");

		Optional<Comision> comision = comisionRepositorio.findById(id);
		log.info(comision.toString());
		if (comision.isPresent()) {
			Comision comisionObj = comision.get();
			comisionObj.setEstado("Verificando");
			comisionObj.setValidada(true);
			comisionObj.setFechaValidacion(LocalDate.now());
			comisionObj.setUsuarioValidador(usuario);
			comisionRepositorio.save(comisionObj); // Guarda la comisión actualizada en la base de datos
		} else {
			log.error("Comisión con ID " + id + " no encontrada.");
		}
	}

	@Override
	public void verificarComision(Integer id, Usuario usuario) {
		log.info("[verificarComision]");

		Optional<Comision> comision = comisionRepositorio.findById(id);

		if (comision.isPresent()) {
			Comision comisionObj = comision.get();
			if (comisionObj.isValidada()) {
				comisionObj.setEstado("Aprobada");
				comisionObj.setAprobada(true);
				comisionObj.setVerificada(true);
				comisionObj.setUsuarioVerificador(usuario);
				comisionObj.setFechaVerificacion(LocalDate.now());
				comisionRepositorio.save(comisionObj); // Guarda la comisión actualizada en la base de datos
			} else {
				log.error("Inetentando verificar Comisión con ID " + id + " sin validar");
			}
		} else {
			log.error("Comisión con ID " + id + " no encontrada.");
		}
	}

	public void noValidarComision(Integer id, Usuario usuario) {
		log.info("[NOvalidarComision]");

		Optional<Comision> comision = comisionRepositorio.findById(id);

		if (comision.isPresent()) {
			Comision comisionObj = comision.get();
			if (comisionObj.isValidada() == false) {
				comisionObj.setValidada(false);
				comisionObj.setCancelada(true);
				comisionObj.setUsuarioCancelador(usuario);
				comisionObj.setEstado("Validación_cancelada");
				comisionRepositorio.save(comisionObj); // Guarda la comisión actualizada en la base de datos
			} else {
				log.error("Inetentando verificar Comisión con ID " + id + " sin validar");
			}
		} else {
			log.error("Comisión con ID " + id + " no encontrada.");
		}
	}

	public void noVerificarComision(Integer id, Usuario usuario) {
		log.info("[NOverificarComision]");

		Optional<Comision> comision = comisionRepositorio.findById(id);

		if (comision.isPresent()) {
			Comision comisionObj = comision.get();
			if (comisionObj.isValidada()) {
				comisionObj.setValidada(false);
				comisionObj.setVerificada(false);
				comisionObj.setCancelada(true);
				comisionObj.setUsuarioCancelador(usuario);
				comisionObj.setEstado("Verificación_cancelada");
				comisionRepositorio.save(comisionObj); // Guarda la comisión actualizada en la base de datos
			} else {
				log.error("Inetentando verificar Comisión con ID " + id + " sin validar");
			}
		} else {
			log.error("Comisión con ID " + id + " no encontrada.");
		}
	}

	public void guardarComentario(Integer id, String motivo, Usuario usuario) {

		log.info("[GuardarComentarioCancelacion]");

		Optional<Comision> comision = comisionRepositorio.findById(id);
		if (comision.isPresent()) {
			Comision comisionObj = comision.get();
			log.info("[Objeto Comision a Cancelar] "+ comisionObj.getUsuarioCancelador());
			if(comisionObj.getUsuarioCancelador().getId()==usuario.getId()) {
				comisionObj.setMotivoCancelacion(motivo);
				log.info("[MOTIVO CANCELACION:] "+motivo );
				comisionRepositorio.save(comisionObj);
			}
			
		}
	}

}
