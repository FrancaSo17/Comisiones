package com.example.demo.servicios;

import java.util.List;
import java.util.Optional;

import com.example.demo.data.Participante;

public interface IServicioParticipantes {

	public List<Participante> getAllParticipantes();

	public Optional<Participante> getParticipanteById(Integer id);

	public Participante saveParticipante(Participante participante);

	public void deleteParticipante(Integer id);

	public List<Participante> getParticipanteByComisionId(Integer comisionId);
	
	List<Participante> getParticipantesByIds(List<Integer> ids);

}
