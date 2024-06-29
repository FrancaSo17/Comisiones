package com.example.demo.servicios;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public List<Participante> getAllParticipantes() {
		log.info("getAllParticipantes");
		return participanteRepositorio.findAll();
	}

	public Optional<Participante> getParticipanteById(Integer id) {
		log.info("getParticipanteById");
		return participanteRepositorio.findById(id);
	}

	public Participante saveParticipante(Participante participante) {
		log.info("saveParticipante");
		return participanteRepositorio.save(participante);
	}

	public void deleteParticipante(Integer id) {
		log.info("deleteParticipante");
		participanteRepositorio.deleteById(id);
	}

	@Override
	public List<Participante> getParticipanteByComisionId(Integer comisionId) {
		log.info("getParticipanteByComisionId");
		Comision comision= comisionServicio.getComisionById(comisionId).orElseThrow(()-> new RuntimeException("Comisión no encontrada"));
		return participanteRepositorio.findByComision(comision);
	}
	 @Override
	    public List<Participante> getParticipantesByIds(List<Integer> ids) { 
		 log.info("[getParticipantesByIds]");
	        // Implementa la lógica para obtener participantes por IDs
	        // Por ejemplo:
	         return participanteRepositorio.findAllByIdIn(ids);
	    }


}
