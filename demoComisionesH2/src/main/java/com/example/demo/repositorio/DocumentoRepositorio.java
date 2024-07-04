package com.example.demo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.data.Comision;
import com.example.demo.data.Documento;

public interface DocumentoRepositorio extends JpaRepository<Documento, Integer>  {

	List<Documento> findByComision(Comision comision);
}
