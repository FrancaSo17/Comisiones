package com.example.demo.servicios;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.exceptions.CodeError;
import com.example.demo.common.exceptions.ServiceException;
import com.example.demo.data.Comision;
import com.example.demo.data.Documento;
import com.example.demo.repositorio.DocumentoRepositorio;

@Service
public class DocumentoServicio implements IServicioDocumento{

	Logger log = LoggerFactory.getLogger(ComisionServicio.class);
	
	@Autowired
	private DocumentoRepositorio documentoRepositorio;
	
	@Autowired
	private IServicioComision comisionServicio;
	
	
	public List<Documento> getDocumentosByComisionId(Integer comisionId) throws ServiceException{
		log.info("[getDocumentosByComision ]");
		
		try {
			 Comision comision = comisionServicio.getComisionById(comisionId)
					 .orElseThrow(() -> new RuntimeException("Comisión no encontrada"));
			return documentoRepositorio.findByComision(comision);
			
		}catch(Exception e){
			
			log.error("[Error al obtener getDocumentosByComision ]");
			throw new ServiceException(CodeError.ERROR_GENERAL);
		}
		
		
	}
	
	public Documento saveDocumento(Documento documento) throws ServiceException{
		log.info("[saveDocumento ]");
	try {
			documentoRepositorio.save(documento);
			return documento;
		}catch(Exception e){
			log.error("[Error al guardar documento ]");
			throw new ServiceException(CodeError.ERROR_GENERAL);
			
		}
		
	
		
	}
}
