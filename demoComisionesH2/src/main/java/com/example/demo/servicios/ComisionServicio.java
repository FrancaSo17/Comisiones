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

import com.example.demo.common.exceptions.CodeError;
import com.example.demo.common.exceptions.ServiceException;
import com.example.demo.data.Comision;
import com.example.demo.data.Usuario;
import com.example.demo.repositorio.ComisionRepositorio;

@Service
public class ComisionServicio implements IServicioComision {

	private final static Logger log = LoggerFactory.getLogger(ComisionServicio.class);

	@Autowired
	private ComisionRepositorio comisionRepositorio;

	public List<Comision> getAllComsiones() throws ServiceException {
	    log.info("[getAllComsiones]");
	    try {
	        List<Comision> comisiones = comisionRepositorio.findAll();
	        log.debug("Comisiones sin ordenar");
	        comisiones.forEach(c -> log.debug(c.toString()));

	        List<Comision> comisionesOrdenadas = comisionRepositorio.findAllByOrderByFechaComisionAscPrioridadAsc();
	        log.debug("Comisiones ordenadas");
	        comisionesOrdenadas.forEach(c -> log.debug(c.toString()));

	        Collections.sort(comisionesOrdenadas, Comparator.comparingInt(Comision::getPrioridad));
	        return comisionesOrdenadas;
	    } catch (Exception e) {
	        log.error("Error obteniendo comisiones", e);
	        throw new ServiceException(CodeError.COMISION_NOT_FOUND, e);
	    }
	}


	public List<Comision> getAllcomisionesSinValidar(Usuario usuario) throws ServiceException {
	    log.info("[getAllComsionesSinValidar]");
	    log.info("[Nivel usuario en Servicio:" + usuario.getNivelUsuario() + "]");

	    try {
	        List<Comision> comisiones = comisionRepositorio.findAll();

	        List<Comision> comisionesSinValidar = comisiones.stream()
	            .filter(c -> !c.isValidada() && !c.isCancelada())
	            .filter(c -> c.getUsuarioCreador().getNivelUsuario() < usuario.getNivelUsuario()
	                    || usuario.getNivelUsuario() == 9)
	            .collect(Collectors.toList());

	        Collections.sort(comisionesSinValidar, Comparator.comparingInt(Comision::getPrioridad));

	        return comisionesSinValidar;
	    } catch (Exception e) {
	        log.error("Error obteniendo comisiones sin validar", e);
	        throw new ServiceException(CodeError.COMISION_NOT_FOUND, e);
	    }
	}


	public List<Comision> getAllcomisionesSinVerificar(Usuario usuario) throws ServiceException {
	    log.info("[getAllComsionesSinVerificar]");

	    try {
	        List<Comision> comisiones = comisionRepositorio.findAll();

	        List<Comision> comisionesSinV = comisiones.stream()
	            .filter(c -> c.isValidada() && !c.isVerificada())
	            .filter(c -> c.getUsuarioValidador().getNivelUsuario() < usuario.getNivelUsuario()
	                    || usuario.getNivelUsuario() == 9)
	            .collect(Collectors.toList());

	        Collections.sort(comisionesSinV, Comparator.comparingInt(Comision::getPrioridad));

	        return comisionesSinV;
	    } catch (Exception e) {
	        log.error("Error obteniendo comisiones sin verificar", e);
	        throw new ServiceException(CodeError.COMISION_NOT_FOUND, e);
	    }
	}


	public List<Comision> getAllcomisionesCanceladas() throws ServiceException {
	    log.info("[getAllComsionesCanceladas]");
	    
	    try {
	        List<Comision> comisiones = comisionRepositorio.findAll();

	        List<Comision> comisionesCanceladas = comisiones.stream()
	            .filter(Comision::isCancelada)
	            .collect(Collectors.toList());

	        Collections.sort(comisionesCanceladas, Comparator.comparingInt(Comision::getPrioridad));

	        return comisionesCanceladas;
	    } catch (Exception e) {
	        log.error("Error obteniendo comisiones canceladas", e);
	        throw new ServiceException(CodeError.COMISION_NOT_FOUND, e);
	    }
	}


	public List<Comision> getAllcomisionesAprobadas() throws ServiceException {
	    log.info("[getAllComsionesAprobadas]");
	    
	    try {
	        List<Comision> comisiones = comisionRepositorio.findAll();

	        List<Comision> comisionesAprobadas = comisiones.stream()
	            .filter(c -> !c.isCancelada() && c.isValidada() && c.isVerificada() && c.isAprobada())
	            .collect(Collectors.toList());

	        Collections.sort(comisionesAprobadas, Comparator.comparingInt(Comision::getPrioridad));

	        return comisionesAprobadas;
	    } catch (Exception e) {
	        log.error("Error obteniendo comisiones aprobadas", e);
	        throw new ServiceException(CodeError.COMISION_NOT_FOUND, e);
	    }
	}


	public Optional<Comision> getComisionById(Integer id) throws ServiceException  {
		log.info("[getComisionById]");
		try {
			Optional<Comision> comision= comisionRepositorio.findById(id);
			return comision;
		}catch (Exception e) {
			log.error("Error obteniendo comision by Id",e);
			throw new ServiceException(CodeError.COMISION_NOT_FOUND,e);	
		}
		
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Importante
	public Comision saveComision(Comision comision, Usuario usuarioCreador) throws ServiceException {
	    log.info("[saveComision]");
	    System.out.println("Usuario autenticado: " + usuarioCreador.getUsername());

	    try {
	        comision.setEstado("pendiente");

	        // Obtén el usuario autenticado actualmente
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        log.info("[Autenticada(?)] " + authentication.isAuthenticated());

	        comision.setUsuarioCreador(usuarioCreador);

	        return comisionRepositorio.save(comision);
	    } catch (Exception e) {
	        log.error("Error guardando la comisión", e);
	        throw new ServiceException(CodeError.ERROR_GENERAL, e);
	    }
	}


//	///////////////////////////////////////////////////////////////////////////////////////////////

	public void deleteComision(Integer id) throws ServiceException {
	    log.info("[EliminarComision]");
	    
	    try {
	        comisionRepositorio.deleteById(id);
	    } catch (Exception e) {
	        log.error("Error eliminando la comisión con ID: " + id, e);
	        throw new ServiceException(CodeError.COMISION_NOT_FOUND +" Error eliminando la comisión con ID: " + id, e);
	    }
	}



	@Override
	public List<Comision> listComision() throws ServiceException {
	    log.info("[listComision]");
	    
	    try {
	        // Suponiendo que se debería retornar una lista de comisiones
	        return comisionRepositorio.findAll();
	    } catch (Exception e) {
	        log.error("Error listando las comisiones", e);
	        throw new ServiceException(CodeError.COMISION_NOT_FOUND, e);
	    }
	}


	////

	@Override
	public void validarComision(Integer id, Usuario usuario) throws ServiceException {
	    log.info("[validarComision]");

	    try {
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
	            throw new ServiceException("Comisión con ID " + id + " no encontrada.");
	        }
	    } catch (Exception e) {
	        log.error("Error validando la comisión con ID: " + id, e);
	        throw new ServiceException(CodeError.COMISION_NOT_FOUND+ " Error validando la comisión con ID: " + id, e);
	    }
	}


	@Override
	public void verificarComision(Integer id, Usuario usuario) throws ServiceException {
	    log.info("[verificarComision]");

	    try {
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
	                log.error("Intentando verificar Comisión con ID " + id + " sin validar");
	                throw new ServiceException("Intentando verificar Comisión sin validar con ID: " + id);
	            }
	        } else {
	            log.error("Comisión con ID " + id + " no encontrada.");
	            throw new ServiceException("Comisión con ID " + id + " no encontrada.");
	        }
	    } catch (Exception e) {
	        log.error("Error verificando la comisión con ID: " + id, e);
	        throw new ServiceException(CodeError.COMISION_NOT_FOUND+ " Error verificando la comisión con ID: " + id, e);
	    }
	}


	public void noValidarComision(Integer id, Usuario usuario) throws ServiceException {
	    log.info("[NOvalidarComision]");

	    try {
	        Optional<Comision> comision = comisionRepositorio.findById(id);

	        if (comision.isPresent()) {
	            Comision comisionObj = comision.get();
	            if (!comisionObj.isValidada()) {
	                comisionObj.setValidada(false);
	                comisionObj.setCancelada(true);
	                comisionObj.setUsuarioCancelador(usuario);
	                comisionObj.setEstado("Validación_cancelada");
	                comisionRepositorio.save(comisionObj); // Guarda la comisión actualizada en la base de datos
	            } else {
	                log.error("Intentando no validar Comisión con ID " + id + " ya validada");
	                throw new ServiceException("Intentando no validar Comisión con ID " + id + " ya validada");
	            }
	        } else {
	            log.error("Comisión con ID " + id + " no encontrada.");
	            throw new ServiceException("Comisión con ID " + id + " no encontrada.");
	        }
	    } catch (Exception e) {
	        log.error("Error no validando la comisión con ID: " + id, e);
	        throw new ServiceException(CodeError.COMISION_NOT_FOUND+" Error no validando la comisión con ID: " + id, e);
	    }
	}


	public void noVerificarComision(Integer id, Usuario usuario) throws ServiceException {
	    log.info("[NOverificarComision]");

	    try {
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
	                log.error("Intentando no verificar Comisión con ID " + id + " sin validar");
	                throw new ServiceException("Intentando no verificar Comisión con ID " + id + " sin validar");
	            }
	        } else {
	            log.error("Comisión con ID " + id + " no encontrada.");
	            throw new ServiceException("Comisión con ID " + id + " no encontrada.");
	        }
	    } catch (Exception e) {
	        log.error("Error no verificando la comisión con ID: " + id, e);
	        throw new ServiceException(CodeError.COMISION_NOT_FOUND+" Error no verificando la comisión con ID: " + id, e);
	    }
	}


	public void guardarComentario(Integer id, String motivo, Usuario usuario) throws ServiceException {
	    log.info("[GuardarComentarioCancelacion]");

	    try {
	        Optional<Comision> comision = comisionRepositorio.findById(id);
	        
	        if (comision.isPresent()) {
	            Comision comisionObj = comision.get();

	            // Verificar si el usuario actual es el que canceló la comisión
	            if (comisionObj.getUsuarioCancelador() != null && comisionObj.getUsuarioCancelador().getId() == usuario.getId()) {
	                comisionObj.setMotivoCancelacion(motivo);
	                log.info("[MOTIVO CANCELACION:] " + motivo);
	                comisionRepositorio.save(comisionObj);
	            } else {
	                log.error("Usuario no autorizado para guardar comentario de cancelación.");
	                throw new ServiceException("Usuario no autorizado para guardar comentario de cancelación.");
	            }
	        } else {
	            log.error("Comisión con ID " + id + " no encontrada.");
	            throw new ServiceException("Comisión con ID " + id + " no encontrada.");
	        }
	    } catch (Exception e) {
	        log.error("Error al guardar comentario de cancelación para la comisión con ID: " + id, e);
	        throw new ServiceException(CodeError.COMISION_NOT_FOUND+ " Error al guardar comentario de cancelación para la comisión con ID: " + id, e);
	    }
	}


}
