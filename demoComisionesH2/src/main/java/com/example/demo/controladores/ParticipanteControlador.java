package com.example.demo.controladores;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.exceptions.ServiceException;
import com.example.demo.data.Participante;
import com.example.demo.servicios.ParticipanteServicio;

@RestController
@RequestMapping("/participantes")
public class ParticipanteControlador {

	private final static Logger log = LoggerFactory.getLogger(LoginControlador.class);
	
	  @Autowired
	    private ParticipanteServicio participanteServicio;

	    @GetMapping
	    public List<Participante> getAllParticipantes() throws ServiceException{
	    	log.info("GETgetAllParticipantes]");
	        return participanteServicio.getAllParticipantes();
	    }

	    @GetMapping("/{id}")
	    public Optional<Participante> getParticipanteById(@PathVariable Integer id)throws ServiceException {
	    	log.info("GETgetParticipanteById]");
	        return participanteServicio.getParticipanteById(id);
	    }

	    @PostMapping
	    public Participante createParticipante(@RequestBody Participante participante)throws ServiceException {
	    	log.info("POSTcreateParticipante]");
	        return participanteServicio.saveParticipante(participante);
	    }

	    @DeleteMapping("/{id}")
	    public void deleteParticipante(@PathVariable Integer id) throws ServiceException{
	    	log.info("DELETEdeleteParticipante]");
	    	participanteServicio.deleteParticipante(id);
	    }

		
	}

