package com.example.demo.servicios;

import java.util.List;

import com.example.demo.common.exceptions.ServiceException;
import com.example.demo.data.Documento;

public interface IServicioDocumento {

	public List<Documento> getDocumentosByComisionId(Integer comisionId) throws ServiceException;
	
	public Documento saveDocumento(Documento documento) throws ServiceException;
}
