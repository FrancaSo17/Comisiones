package com.example.demo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.data.Comision;
import com.example.demo.data.Participante;

public interface ParticipanteRepositorio extends JpaRepository<Participante, Integer> {

	List<Participante> findByComision(Comision comision);
	
	 List<Participante> findAllByIdIn(List<Integer> ids);
}
