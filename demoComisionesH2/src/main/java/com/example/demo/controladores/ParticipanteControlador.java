package com.example.demo.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.Participante;
import com.example.demo.servicios.ParticipanteServicio;

@RestController
@RequestMapping("/participantes")
public class ParticipanteControlador {

	  @Autowired
	    private ParticipanteServicio participanteServicio;

	    @GetMapping
	    public List<Participante> getAllParticipantes() {
	        return participanteServicio.getAllParticipantes();
	    }

	    @GetMapping("/{id}")
	    public Optional<Participante> getParticipanteById(@PathVariable Integer id) {
	        return participanteServicio.getParticipanteById(id);
	    }

	    @PostMapping
	    public Participante createParticipante(@RequestBody Participante participante) {
	        return participanteServicio.saveParticipante(participante);
	    }

	    @DeleteMapping("/{id}")
	    public void deleteParticipante(@PathVariable Integer id) {
	    	participanteServicio.deleteParticipante(id);
	    }

		
	}

