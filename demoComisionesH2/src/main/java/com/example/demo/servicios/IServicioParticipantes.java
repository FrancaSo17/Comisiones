package com.example.demo.servicios;

import java.util.List;
import java.util.Optional;

import com.example.demo.common.exceptions.ServiceException;
import com.example.demo.data.Participante;

public interface IServicioParticipantes {

	public List<Participante> getAllParticipantes() throws ServiceException;

	public Optional<Participante> getParticipanteById(Integer id) throws ServiceException;

	public Participante saveParticipante(Participante participante) throws ServiceException;

	public void deleteParticipante(Integer id) throws ServiceException;

	public List<Participante> getParticipanteByComisionId(Integer comisionId) throws ServiceException;
	
	public List<Participante> getParticipantesByIds(List<Integer> ids) throws ServiceException;

}
