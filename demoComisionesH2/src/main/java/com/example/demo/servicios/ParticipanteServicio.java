package com.example.demo.servicios;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.exceptions.CodeError;
import com.example.demo.common.exceptions.ServiceException;
import com.example.demo.data.Comision;
import com.example.demo.data.Participante;
import com.example.demo.repositorio.ParticipanteRepositorio;

@Service
public class ParticipanteServicio implements IServicioParticipantes {

	Logger log = LoggerFactory.getLogger(ComisionServicio.class);

	@Autowired
	private ParticipanteRepositorio participanteRepositorio;
	
	@Autowired
	private IServicioComision comisionServicio;

	public List<Participante> getAllParticipantes() throws ServiceException {
	    log.info("getAllParticipantes");
	    try {
	        return participanteRepositorio.findAll();
	    } catch (Exception e) {
	        log.error("Error al obtener todos los participantes", e);
	        throw new ServiceException(CodeError.COMISION_NOT_FOUND+" Error al obtener todos los participantes", e);
	    }
	}


	public Optional<Participante> getParticipanteById(Integer id) throws ServiceException {
	    log.info("getParticipanteById");
	    try {
	        return participanteRepositorio.findById(id);
	    } catch (Exception e) {
	        log.error("Error al obtener el participante con ID: " + id, e);
	        throw new ServiceException(CodeError.COMISION_NOT_FOUND+" Error al obtener el participante con ID: " + id, e);
	    }
	}


	public Participante saveParticipante(Participante participante) throws ServiceException {
	    log.info("saveParticipante");
	    try {
	        return participanteRepositorio.save(participante);
	    } catch (Exception e) {
	        log.error("Error al guardar el participante", e);
	        throw new ServiceException(CodeError.PARTICIPANTE_NOT_FOUND+" Error al guardar el participante", e);
	    }
	}


	public void deleteParticipante(Integer id) throws ServiceException {
	    log.info("deleteParticipante");
	    try {
	        participanteRepositorio.deleteById(id);
	    } catch (Exception e) {
	        log.error("Error al eliminar el participante con ID: " + id, e);
	        throw new ServiceException(CodeError.PARTICIPANTE_NOT_FOUND+ " Error al eliminar el participante con ID: " + id, e);
	    }
	}


	public List<Participante> getParticipanteByComisionId(Integer comisionId) throws ServiceException {
	    log.info("getParticipanteByComisionId");
	    
	    try {
	        Comision comision = comisionServicio.getComisionById(comisionId)
	            .orElseThrow(() -> new RuntimeException("Comisión no encontrada"));
	        return participanteRepositorio.findByComision(comision);
	    } catch (Exception e) {
	        log.error("Error obteniendo participantes por ID de comisión", e);
	        throw new ServiceException(CodeError.PARTICIPANTE_NOT_FOUND+" Error obteniendo participantes por ID de comisión", e);
	    }
	}


	@Override
	public List<Participante> getParticipantesByIds(List<Integer> ids) throws ServiceException {
	    log.info("[getParticipantesByIds]");
	    try {
	        // Implementa la lógica para obtener participantes por IDs
	        // Por ejemplo:
	        return participanteRepositorio.findAllByIdIn(ids);
	    } catch (Exception e) {
	        log.error("Error obteniendo participantes por IDs", e);
	        throw new ServiceException(CodeError.PARTICIPANTE_NOT_FOUND+" Error obteniendo participantes por IDs", e);
	    }
	}




}
